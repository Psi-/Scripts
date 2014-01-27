package com.dequeue.rsbot.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */

public class MiniItemMain {
    private static int ITEM_ID = 2846;
    private static HashMap<Integer, MiniItem> defMap = new HashMap<Integer, MiniItem>();


    public static void main(String[] args) throws IOException {
        MiniItem.ItemReader read = new MiniItem.ItemReader(new File("minidef.in"));
        MiniItem def = read.read(ITEM_ID);
        defMap.put(ITEM_ID, def);
        System.out.println(def.getName());
    }
}
