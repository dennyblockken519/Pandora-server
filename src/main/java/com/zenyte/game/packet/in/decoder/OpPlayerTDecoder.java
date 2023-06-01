package com.zenyte.game.packet.in.decoder;

import com.zenyte.game.packet.ClientProtDecoder;
import com.zenyte.game.packet.in.event.OpPlayerTEvent;
import com.zenyte.game.world.entity.player.Player;
import com.zenyte.network.io.RSBuffer;

/**
 * @author Tommeh | 28 jul. 2018 | 19:45:51
 * @see <a href="https://www.rune-server.ee/members/tommeh/">Rune-Server profile</a>}
 */
public class OpPlayerTDecoder implements ClientProtDecoder<OpPlayerTEvent> {
    @Override
    public OpPlayerTEvent decode(Player player, int opcode, RSBuffer buffer) {
        final int compressed = buffer.readIntV2();
        final boolean run = buffer.read128Byte() == 1;
        buffer.readShort128();
        final short index = buffer.readShort();
        final int interfaceId = compressed >> 16;
        final int componentId = compressed & 65535;
        return new OpPlayerTEvent(interfaceId, componentId, index, run);
    }
}
