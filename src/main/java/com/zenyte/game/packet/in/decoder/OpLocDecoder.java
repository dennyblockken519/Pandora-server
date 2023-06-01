package com.zenyte.game.packet.in.decoder;

import com.zenyte.game.constants.ClientProt;
import com.zenyte.game.packet.ClientProtDecoder;
import com.zenyte.game.packet.in.event.OpLocEvent;
import com.zenyte.game.world.entity.player.Player;
import com.zenyte.network.io.RSBuffer;
import org.apache.commons.lang3.ArrayUtils;

/**
 * @author Tommeh | 28 jul. 2018 | 19:59:48
 * @see <a href="https://www.rune-server.ee/members/tommeh/">Rune-Server profile</a>}
 */
public class OpLocDecoder implements ClientProtDecoder<OpLocEvent> {
    public static final int[] OPCODES = {ClientProt.OPLOC1.getOpcode(), ClientProt.OPLOC2.getOpcode(), ClientProt.OPLOC3.getOpcode(), ClientProt.OPLOC4.getOpcode(), ClientProt.OPLOC5.getOpcode()};

    /*ClientProt.OPLOC6.getOpcode()*/
    @Override
    public OpLocEvent decode(Player player, int opcode, RSBuffer buffer) {
        int id = -1;
        int x = -1;
        int y = -1;
        boolean run = false;
        final int option = ArrayUtils.indexOf(OPCODES, opcode) + 1;
        if (opcode == OPCODES[0]) {
            y = buffer.readShort();
            run = buffer.read128Byte() == 1;
            id = buffer.readShort128() & 65535;
            x = buffer.readShortLE128();
        } else if (opcode == OPCODES[1]) {
            run = buffer.readByte128() == 1;
            id = buffer.readShort() & 65535;
            y = buffer.readShort();
            x = buffer.readShort();
        } else if (opcode == OPCODES[2]) {
            x = buffer.readShortLE128();
            id = buffer.readShort128() & 65535;
            y = buffer.readShortLE128();
            run = buffer.readByte128() == 1;
        } else if (opcode == OPCODES[3]) {
            run = buffer.readByte128() == 1;
            y = buffer.readShort128();
            x = buffer.readShort128();
            id = buffer.readShortLE() & 65535;
        } else if (opcode == OPCODES[4]) {
            id = buffer.readShort() & 65535;
            run = buffer.readByte128() == 1;
            x = buffer.readShort128();
            y = buffer.readShortLE128();
        }
        return new OpLocEvent(id, x, y, option, run);
    }
}
