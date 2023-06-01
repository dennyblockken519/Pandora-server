package com.zenyte.game.packet.in.decoder;

import com.zenyte.game.packet.ClientProtDecoder;
import com.zenyte.game.packet.in.event.MoveGameClickEvent;
import com.zenyte.game.world.entity.player.Player;
import com.zenyte.network.io.RSBuffer;

/**
 * @author Tommeh | 28 jul. 2018 | 22:21:41
 * @see <a href="https://www.rune-server.ee/members/tommeh/">Rune-Server profile</a>}
 */
public class MoveGameClickDecoder implements ClientProtDecoder<MoveGameClickEvent> {
    @Override
    public MoveGameClickEvent decode(Player player, int opcode, RSBuffer buffer) {
        final short offsetY = buffer.readShort128();
        final short offsetX = buffer.readShort128();
        final byte type = buffer.readByte128();
        return new MoveGameClickEvent(offsetX, offsetY, type);
    }
}
