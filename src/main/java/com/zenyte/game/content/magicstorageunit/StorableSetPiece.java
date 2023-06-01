package com.zenyte.game.content.magicstorageunit;

/**
 * @author Kris | 15/09/2020
 * @see <a href="https://www.rune-server.ee/members/kris/">Rune-Server profile</a>
 */
public class StorableSetPiece {
    private final int[] ids;

    public StorableSetPiece(final int... ids) {
        this.ids = ids;
    }

    public int[] getIds() {
        return this.ids;
    }
}
