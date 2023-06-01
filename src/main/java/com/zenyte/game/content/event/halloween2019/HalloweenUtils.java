package com.zenyte.game.content.event.halloween2019;

import com.zenyte.Constants;
import com.zenyte.game.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author Kris | 01/11/2019
 * @see <a href="https://www.rune-server.ee/members/kris/">Rune-Server profile</a>
 */
public final class HalloweenUtils {

    public static final int SPOKEN_TO_CAROL = 1;
    public static final int SPOKEN_TO_PEKSA = 2;
    public static final int SEARCHED_BLOODY_AXE = 3;
    public static final int FREED_GHOST = 4;
    public static final int COMPLETED_VARP = 3620;

    public static boolean isCompleted(@NotNull final Player player) {
        return player.getNumericAttribute("Halloween event 2019").intValue() == 1;
    }

    public static int getStage(@NotNull final Player player) {
        return player.getNumericAttribute("halloween event 2019 stage").intValue();
    }

    public static void setStage(@NotNull final Player player, final int stage) {
        if (getStage(player) >= stage || !Constants.HALLOWEEN) {
            return;
        }
        player.addAttribute("halloween event 2019 stage", stage);
    }

}
