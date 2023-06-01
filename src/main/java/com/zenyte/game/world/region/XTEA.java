package com.zenyte.game.world.region;

public class XTEA {
    private final int region;
    private final int[] keys;

    public XTEA(final int region, final int[] keys) {
        this.region = region;
        this.keys = keys;
    }

    public int getRegion() {
        return this.region;
    }

    public int[] getKeys() {
        return this.keys;
    }
}
