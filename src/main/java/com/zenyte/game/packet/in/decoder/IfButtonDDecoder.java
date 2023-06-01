package com.zenyte.game.packet.in.decoder;

import com.zenyte.game.packet.ClientProtDecoder;
import com.zenyte.game.packet.in.event.IfButtonDEvent;
import com.zenyte.game.world.entity.player.Player;
import com.zenyte.network.io.RSBuffer;

/**
 * @author Kris | 19. juuli 2018 : 23:19:47
 * @see <a href="https://www.rune-server.ee/members/kris/">Rune-Server profile</a>}
 * @see <a href="https://rune-status.net/members/kris.354/">Rune-Status profile</a>}
 */
public class IfButtonDDecoder implements ClientProtDecoder<IfButtonDEvent> {
    @Override
    public IfButtonDEvent decode(Player player, int opcode, RSBuffer buffer) {
        //16
        final short fromSlotId = buffer.readShort128();
        buffer.readShort128();
        final short toSlotId = buffer.readShort();
        final int fromCompressed = buffer.readIntLE();
        final int toCompressed = buffer.readIntV1();
        buffer.readShort();
        final int fromComponentId = fromCompressed & 65535;
        final int toComponentId = toCompressed & 65535;
        final int fromInterfaceId = fromCompressed >> 16;
        final int toInterfaceId = toCompressed >> 16;
        return new IfButtonDEvent(fromInterfaceId, fromComponentId, toInterfaceId, toComponentId, fromSlotId, toSlotId);
    }
}
