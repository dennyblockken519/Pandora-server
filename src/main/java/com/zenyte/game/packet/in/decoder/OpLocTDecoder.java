package com.zenyte.game.packet.in.decoder;

import com.zenyte.game.packet.ClientProtDecoder;
import com.zenyte.game.packet.in.event.OpLocTEvent;
import com.zenyte.game.world.entity.player.Player;
import com.zenyte.network.io.RSBuffer;

/**
 * @author Tommeh | 9 jan. 2018 : 20:07:16
 * @see <a href="https://www.rune-server.ee/members/tommeh/">Rune-Server profile</a>}
 */
public class OpLocTDecoder implements ClientProtDecoder<OpLocTEvent> {
    @Override
    public OpLocTEvent decode(Player player, int opcode, RSBuffer buffer) {
        final boolean run = buffer.readByte() == 1;
        final short x = buffer.readShort();
        final int objectId = buffer.readShort128() & 65535;
        final int compressed = buffer.readIntV2();
        final short slotId = buffer.readShort();
        final short y = buffer.readShortLE();
        final int interfaceId = compressed >> 16;
        final int componentId = compressed & 65535;
        return new OpLocTEvent(interfaceId, componentId, slotId, objectId, x, y, run);
    }
}
