package com.dequeue.rsbot.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

public class MiniItem {

    private final String name;
    private final short id, note;
    private final byte flags;

    public MiniItem(String name, int id, int note, boolean noted, boolean stackable, boolean members) {
        this(name, (short) id, (short) note,
                (byte) ((noted ? NOTED : 0) | (stackable ? STACKED : 0) | (members ? MEMBERS : 0)));
    }

    private MiniItem(String name, short id, short note, byte flags) {
        this.name = name;
        this.id = id;
        this.note = note;
        this.flags = flags;
    }

    public String getName() {
        return name;
    }

    public int getUnnotedId() {
        return (isNoted() ? note : id);
    }

    public int getNotedId() {
        return (isNoted() ? id : note);
    }

    public int getId() {
        return id;
    }

    public boolean isNoted() {
        return (flags & NOTED) == NOTED;
    }

    public boolean isStackable() {
        return (flags & STACKED) == STACKED;
    }

    public boolean isMembers() {
        return (flags & MEMBERS) == MEMBERS;
    }

    @Override
    public String toString() {
        return String.format("MiniItemDef %d: %s isNoted %s isStackable %s isMembers %s notedId %d unnotedId %d",
                id, name, isNoted(), isStackable(), isMembers(), getNotedId(), getUnnotedId());
    }

    public static class ItemWriter {
        private final BufferedOutputStream output;
        private int count;
        private int index = 0;
        private ByteArrayOutputStream indices = new ByteArrayOutputStream(120000);

        private ByteBuffer tmp = ByteBuffer.allocate(100);

        public ItemWriter(OutputStream out) {
            output = new BufferedOutputStream(out);
        }

        public synchronized boolean write(MiniItem it) {
            try {
                tmp.clear();
                tmp.put(it.name.getBytes());
                tmp.putChar('\n');
                tmp.putShort(it.note);
                tmp.put(it.flags);
                int written = tmp.position();
                tmp.clear();
                output.write(tmp.array(), 0, written);

                tmp.putInt(index);
                tmp.clear();
                indices.write(tmp.array(), 0, 4);

                index += written;

                count++;
                return true;
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }

        public void finish() {
            try {
                output.write(indices.toByteArray());
                tmp.clear();
                tmp.putInt(count);
                tmp.clear();
                output.write(tmp.array(), 0, 4);

                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ItemReader {
        private final RandomAccessFile source;
        private final int count;
        private final long descriptionStart;
        private ByteBuffer buf = ByteBuffer.allocate(3);

        private ItemReader(RandomAccessFile source) throws IOException {
            this.source = source;
            source.seek(source.length() - 4);
            count = source.readInt();
            descriptionStart = source.length() - (count + 1) * 4;
        }

        public ItemReader(File source) throws FileNotFoundException, IOException {
            this(new RandomAccessFile(source, "r"));
        }

        public ItemReader(String source) throws FileNotFoundException, IOException {
            this(new File(source));
        }

        public synchronized MiniItem read(int id) {
            if (id < 0 || id >= count)
                return null;
            try {
                source.seek(descriptionStart + id * 4);
                int loc = source.readInt();
                source.seek(loc);
                String name = source.readLine();
                buf.clear();
                source.read(buf.array());
                return new MiniItem(name, (short) id, buf.getShort(), buf.get());
            } catch (IOException e) {
                return null;
            }
        }

        public int getCount() {
            return count;
        }

    }

    private static final int NOTED = 1 << 0, STACKED = 1 << 1, MEMBERS = 1 << 2;

}