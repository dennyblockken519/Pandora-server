package com.zenyte.game.music;

import com.zenyte.game.parser.scheduled.ScheduledExternalizable;
import com.zenyte.game.world.World;
import com.zenyte.game.world.entity.player.MusicHandler;
import mgi.types.config.enums.EnumDefinitions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Kris | 27. juuli 2018 : 23:42:56
 * @see <a href="https://www.rune-server.ee/members/kris/">Rune-Server profile</a>
 */
public class MusicLoader implements ScheduledExternalizable {

    public static final List<Integer> UNLOCKED_BY_DEFAULT = new ArrayList<>(500);
    public static final int[] DEFAULT_VARP_VALUES = new int[MusicHandler.VARP_IDS.length];
    private static final Logger log = LoggerFactory.getLogger(MusicLoader.class);
    //Excluded varp values cus I accidentally scuffed it by placing default-unlocked tracks within unlocked map.
    private static final int[] excludedVarpValues = new int[]{51200, 536871936, 1210056705, 805306384, 1075840064, 201871616, -1073741543, -2035273693, 186794284, -703027092, 923736283, -1173808644, -1107383168, -193028019, 2130705371, -423099905, -678336849, 265464435, 3592};

    public static int getExcludedVarpValue(final int index) {
        if (index < 0 || index >= excludedVarpValues.length) {
            return 0;
        }
        return excludedVarpValues[index];
    }

    @Override
    public Logger getLog() {
        return log;
    }

    @Override
    public int writeInterval() {
        return 0;
    }

    @Override
    public void read(final BufferedReader reader) {
        final Music[] music = World.getGson().fromJson(reader, Music[].class);
        for (final Music track : music) {
            Music.map.put(track.getName(), track);
            Music.lowercaseMap.put(track.getName().toLowerCase(), track);
            final List<Integer> regions = track.getRegionIds();
            if (regions == null) {
                continue;
            }
            for (final int region : regions) {
                World.getRegion(region).getMusicTracks().add(track);
            }
        }
        final EnumDefinitions map = EnumDefinitions.get(819);
        final EnumDefinitions nameMap = EnumDefinitions.get(812);
        map.getValues().forEach((slot, grid) -> {
            if (!(grid instanceof Integer)) {
                return;
            }
            final String name = nameMap.getStringValue(slot);
            if (name == null) {
                System.err.println("Failure to load song: " + slot);
                return;
            }
            final Music track = Music.map.get(name);
            if (track == null) {
                System.err.println("Failure to locate track for name: " + name);
                return;
            }
            if (track.getRegionIds() != null || track.isDefaultLocked()) {
                return;
            }
            if ((int) grid == -1) {
                return;
            }
            final int index = ((int) grid >> 14 & 16383) - 1;
            if (index >= DEFAULT_VARP_VALUES.length) {
                System.err.println("Failure to set music track: " + name + " as a default track!");
                return;
            }
            DEFAULT_VARP_VALUES[index] |= 1 << ((int) grid & 16383);
        });
    }

    @Override
    public void write() {
    }

    @Override
    public String path() {
        return "data/music.json";
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return MusicLoader.class;
    }

}
