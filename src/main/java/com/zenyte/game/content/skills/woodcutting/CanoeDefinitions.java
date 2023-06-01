package com.zenyte.game.content.skills.woodcutting;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tommeh | 7 feb. 2018 : 00:56:18
 * @see <a href="https://www.rune-server.ee/members/tommeh/">Rune-Server profile</a>}
 */
public enum CanoeDefinitions {
    LOG(12, 30, 1, 8),
    DUGOUT(27, 60, 2, 6),
    STABLE_DUGOUT(42, 90, 3, 3),
    WAKA(57, 150, 4, 13);
    public static final CanoeDefinitions[] ENTRIES = values();
    public static final Map<Integer, CanoeDefinitions> CANOES = new HashMap<>();
    private final int level;
    private final int bit;
    private final int componentId;
    private final double experience;

    CanoeDefinitions(final int level, final double experience, final int bit, final int componentId) {
        this.level = level;
        this.experience = experience;
        this.bit = bit;
        this.componentId = componentId;
    }

    public static CanoeDefinitions get(final int componentId) {
        return CANOES.get(componentId);
    }

    public static CanoeDefinitions getDefinitionByBitValue(int bit) {
        for (CanoeDefinitions canoe : ENTRIES) {
            if (bit == canoe.getBit() || bit == (canoe.getBit() + 10)) return canoe;
        }
        return null;
    }

    public int getLevel() {
        return this.level;
    }

    public int getBit() {
        return this.bit;
    }

    public int getComponentId() {
        return this.componentId;
    }

    public double getExperience() {
        return this.experience;
    }
}
