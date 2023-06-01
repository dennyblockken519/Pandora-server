package com.zenyte.game.world.region;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Kris | 7. jaan 2018 : 21:44.02
 * @see <a href="https://www.rune-server.ee/members/kris/">Rune-Server profile</a>}
 * @see <a href="https://rune-status.net/members/kris.354/">Rune-Status profile</a>}
 */
public final class XTEALoader {
    public static final Map<Integer, XTEA> DEFINITIONS = new HashMap<>();
    private static final Logger log = LoggerFactory.getLogger(XTEALoader.class);
    private static final Gson GSON = new Gson();
    private static final int[] DEFAULT_KEYS = new int[4];

    public static void load() throws FileNotFoundException {
        final BufferedReader br = new BufferedReader(new FileReader("data/objects/xteas.json"));
        final XTEA[] xteas = GSON.fromJson(br, XTEA[].class);
        for (final XTEA xtea : xteas) {
            if (xtea == null) {
                continue;
            }
            DEFINITIONS.put(xtea.getRegion(), xtea);
        }
    }

    /**
     * Gets the default xtea keys of {0, 0, 0, 0} or
     * the correct keys for the home area (egdeville)
     * as we repack the maps upon cache update.
     *
     * @param region
     * @return
     */
    public static int[] getXTEAs(final int region) {
        if (region == 12342) {
            return getXTEAKeys(region);
        }
        return DEFAULT_KEYS;
    }

    /**
     * Gets the actual xtea keys.
     *
     * @param regionId
     * @return
     */
    public static int[] getXTEAKeys(final int regionId) {
        final XTEA xtea = DEFINITIONS.get(regionId);
        if (xtea == null) {
            return DEFAULT_KEYS;
        }
        return xtea.getKeys();
    }

    public static void save() {
        final File saveFile = new File("filtered xteas.json");
        final Map<Integer, XTEA> xteas = new TreeMap<>();
        DEFINITIONS.forEach((k, v) -> {
            if (v.getKeys()[0] != 0 && v.getKeys()[1] != 0 && v.getKeys()[2] != 0 && v.getKeys()[3] != 0) {
                xteas.put(k, v);
            }
        });
        final String toJson = new GsonBuilder().setPrettyPrinting().create().toJson(xteas.values());
        PrintWriter writer;
        try {
            writer = new PrintWriter(saveFile, StandardCharsets.UTF_8);
            writer.println(toJson);
            writer.close();
        } catch (final Exception e) {
            log.error(Strings.EMPTY, e);
        }
    }
}
