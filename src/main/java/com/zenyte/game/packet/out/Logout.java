package com.zenyte.game.packet.out;

import com.zenyte.game.packet.GamePacketEncoder;
import com.zenyte.game.packet.ServerProt;
import com.zenyte.game.world.entity.player.LogLevel;
import com.zenyte.game.world.entity.player.Player;
import com.zenyte.network.game.packet.GamePacketOut;
import com.zenyte.network.io.RSBuffer;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;

/**
 * @author Tommeh | 28 jul. 2018 | 18:30:31
 * @see <a href="https://www.rune-server.ee/members/tommeh/">Rune-Server profile</a>}
 */
public class Logout implements GamePacketEncoder {
    @Override
    public void log(@NotNull final Player player) {
        log(player, Strings.EMPTY);
    }

    @Override
    public GamePacketOut encode() {
        final ServerProt prot = ServerProt.LOGOUT;
        final RSBuffer buffer = new RSBuffer(prot);
        return new GamePacketOut(prot, buffer);
    }

    @Override
    public LogLevel level() {
        return LogLevel.HIGH_PACKET;
    }
}
