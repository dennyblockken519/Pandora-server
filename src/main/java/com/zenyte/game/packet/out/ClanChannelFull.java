package com.zenyte.game.packet.out;

import com.zenyte.game.content.clans.ClanChannel;
import com.zenyte.game.content.clans.ClanChannelBuilder;
import com.zenyte.game.packet.GamePacketEncoder;
import com.zenyte.game.packet.ServerProt;
import com.zenyte.game.world.entity.player.LogLevel;
import com.zenyte.game.world.entity.player.Player;
import com.zenyte.network.game.packet.GamePacketOut;
import com.zenyte.network.io.RSBuffer;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

/**
 * @author Tommeh | 28 jul. 2018 | 18:09:09
 * @see <a href="https://www.rune-server.ee/members/tommeh/">Rune-Server profile</a>}
 */
public class ClanChannelFull implements GamePacketEncoder {
    private final ClanChannelBuilder builder;

    public ClanChannelFull(final ClanChannelBuilder builder) {
        this.builder = builder;
    }

    @Override
    public void log(@NotNull final Player player) {
        final ClanChannel channel = builder.getChannel();
        this.log(player, "Channel owner: " + channel.getOwner() + ", prefix: " + channel.getPrefix() + ", members: " + channel.getMembers().size());
    }

    @Override
    public GamePacketOut encode() {
        final ServerProt prot = ServerProt.CLANCHANNEL_FULL;
        final RSBuffer buffer = new RSBuffer(prot);
        final ByteBuf prebuiltBuffer = builder.getBuffer().resetReaderIndex();
        buffer.writeBytes(prebuiltBuffer);
        return new GamePacketOut(prot, buffer);
    }

    @Override
    public LogLevel level() {
        return LogLevel.LOW_PACKET;
    }
}
