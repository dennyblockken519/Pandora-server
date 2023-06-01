package com.zenyte.game.packet.in.decoder;

import com.zenyte.game.packet.ClientProtDecoder;
import com.zenyte.game.packet.in.event.ClickWorldMapEvent;
import com.zenyte.game.world.entity.player.Player;
import com.zenyte.network.io.RSBuffer;

/**
 * @author Tommeh | 31 mrt. 2018 : 15:07:05
 * @see <a href="https://www.rune-server.ee/members/tommeh/">Rune-Server profile</a>}
 */
public class ClickWorldMapDecoder implements ClientProtDecoder<ClickWorldMapEvent> {
    @Override
    public ClickWorldMapEvent decode(Player player, int opcode, RSBuffer buffer) {
        buffer.readIntV1();
        final short y = buffer.readShortLE();
        final byte z = buffer.readByteC();
        final short x = buffer.readShort();
        return new ClickWorldMapEvent(x, y, z);
    }
}
