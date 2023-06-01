package com.zenyte.plugins.object;

import com.zenyte.game.world.entity.player.Player;
import com.zenyte.game.world.object.ObjectAction;
import com.zenyte.game.world.object.WorldObject;
import com.zenyte.plugins.dialogue.KylieMinnowD;

public class MinnowRowBoatAction implements ObjectAction {

    @Override
    public void handleObjectAction(Player player, WorldObject object, String name, int optionId, String option) {
        player.getDialogueManager().start(new KylieMinnowD(player, 7727, true));
    }

    @Override
    public Object[] getObjects() {
        return new Object[]{30376, 30377};
    }
}
