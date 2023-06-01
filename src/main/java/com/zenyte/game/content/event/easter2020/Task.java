package com.zenyte.game.content.event.easter2020;

import com.zenyte.game.world.entity.player.Player;

/**
 * @author Corey
 * @since 30/03/2020
 */
public enum Task {
    FEED_EASTER_BIRD("Feed Easter Bird", Stage.BIRD_FED_SPEAK_TO_BUNNY_JR),
    FIX_INCUBATOR("Fix incubator", Stage.INCUBATOR_FIXED_SPEAK_TO_BUNNY_JR),
    RETRAIN_SQUIRRELS("Retrain squirrels", Stage.SQUIRRELS_RETRAINED_SPEAK_T0_BUNNY_JR),
    ROUND_UP_IMPLINGS("Round-up implings", Stage.IMPLINGS_GATHERED_SPEAK_TO_BUNNY_JR);
    public static final Task[] allTasks = values();
    private final String name;
    private final Stage stage;

    Task(final String name, final Stage stage) {
        this.name = name;
        this.stage = stage;
    }

    public String interfaceString(final Player player) {
        return SplittingHeirs.progressedAtLeast(player, stage) ? "<str>" + name + "</str>" : name;
    }
}
