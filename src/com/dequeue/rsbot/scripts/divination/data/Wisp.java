package com.dequeue.rsbot.scripts.divination.data;

/**
 * Created with IntelliJ IDEA.
 * User: Dequeue
 */

public enum Wisp {
    NONE(-1, -1, -1, -1, "not selected"),
    PALE(18150, -1, 18173, -1, "Pale"),
    FLICKERING(18151, 18152, 18174, 18175, "Flickering"),
    BRIGHT(18153, 18154, 18176, 18177, "Bright"),
    GLOWING(18155, 18156, 18178, 18179, "Glowing"),
    SPARKLING(18157, 18158, 18180, 18181, "Sparkling"),
    GLEAMING(18159, 18160, 18182, 18183, "Gleaming"),
    VIBRANT(18161, 18162, 18184, 18185, "Vibrant"),
    LUSTROUS(18163, 18164, 18186, 18187, "Lustrous"),
    BRILLIANT(18165, 18166, 18188, 18189, "Brilliant"),
    RADIANT(18167, 18168, 18190, 18191, "Radiant"),
    LUMINOUS(18169, 18170, 18192, 18193, "Luminous"),
    INCANDESCENT(18171, 18172, 18194, 18195, "Incandescent");

    private final int wispId, enrichedWispId, springId, enrichedSpringId;
    private final String name;

    Wisp(int wispId, int enrichedWispId, int springId, int enrichedSpringId, String name) {
        this.wispId = wispId;
        this.enrichedWispId = enrichedWispId;
        this.springId = springId;
        this.enrichedSpringId = enrichedSpringId;
        this.name = name;
    }

    public int getWispId() {
        return wispId;
    }

    public int getEnrichedWispId() {
        return enrichedWispId;
    }

    public int getSpringId() {
        return springId;
    }

    public int getEnrichedSpringId() {
        return enrichedSpringId;
    }

    @Override
    public String toString() {
        return name;
    }
}
