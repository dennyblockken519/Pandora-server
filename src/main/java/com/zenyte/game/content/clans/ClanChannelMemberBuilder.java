package com.zenyte.game.content.clans;

import com.zenyte.Constants;
import com.zenyte.game.packet.ServerProt;
import com.zenyte.game.world.entity.player.Player;
import org.apache.logging.log4j.util.Strings;

import java.util.Map;

/**
 * @author Kris | 28/01/2019 16:00
 * @see <a href="https://www.rune-server.ee/members/kris/">Rune-Server profile</a>
 */
public class ClanChannelMemberBuilder extends ClanChannelBuilder {
    private final Player player;
    private final boolean add;

    ClanChannelMemberBuilder(final Player player, final Player clanOwner, final boolean add, final ClanChannel channel) {
        super(ServerProt.CLANCHANNEL_MEMBER, channel, clanOwner);
        this.player = player;
        this.add = add;
    }

    @Override
    public ClanChannelBuilder build() {
        assert !buffer.isReadable() : "Buffer is already built";
        final Map<String, ClanRank> rankedMembers = channel.getRankedMembers();
        buffer.writeString(player.getName());
        buffer.writeShort(Constants.WORLD_PROFILE.getNumber());
        buffer.writeByte(!add ? -128 : getRank(rankedMembers.get(player.getUsername()), player, clanOwner));
        buffer.writeString(Strings.EMPTY);
        return this;
    }
}
