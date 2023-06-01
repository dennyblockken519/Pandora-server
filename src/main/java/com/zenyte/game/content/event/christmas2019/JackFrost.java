package com.zenyte.game.content.event.christmas2019;

import com.zenyte.game.world.entity.npc.actions.NPCPlugin;
import com.zenyte.game.world.entity.player.dialogue.Dialogue;
import com.zenyte.game.world.entity.player.dialogue.Expression;

import java.util.Objects;

/**
 * @author Corey
 * @since 19/12/2019
 */
public class JackFrost extends NPCPlugin {
    @Override
    public void handle() {
        bind("Talk-to", (player, npc) -> {
            if (Objects.requireNonNull(AChristmasWarble.getProgress(player)) == AChristmasWarble.ChristmasWarbleProgress.FIND_OUT_ABOUT_SCOURGES_PAST) {
                player.getDialogueManager().start(new Dialogue(player, npc) {
                    @Override
                    public void buildDialogue() {
                        if (ChristmasUtils.wearingGhostCostume(player)) {
                            npc("I'm not talking to you while you're in those bedsheets.", Expression.HIGH_REV_NORMAL);
                            return;
                        }
                        player("Hello, Jack.");
                        npc("Oh, hello.", Expression.HIGH_REV_NORMAL);
                        player("Do you know much about Scourge?");
                        npc("No.", Expression.HIGH_REV_NORMAL);
                        player("But he's your neighbour?");
                        npc("So?", Expression.HIGH_REV_NORMAL);
                    }
                });
            } else {
                player.getDialogueManager().start(new Dialogue(player, npc) {
                    @Override
                    public void buildDialogue() {
                        player("Hello, Jack.");
                        plain("Jack doesn't seem to be paying attention to what you're saying.");
                    }
                });
            }
        });
    }

    @Override
    public int[] getNPCs() {
        return new int[]{ChristmasConstants.JACK_FROST_NPC_ID};
    }
}
