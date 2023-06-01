package com.zenyte.game.world.entity.player.update.mask;

import com.zenyte.game.world.entity.HitBar;
import com.zenyte.game.world.entity.masks.Hit;
import com.zenyte.game.world.entity.masks.UpdateFlag;
import com.zenyte.game.world.entity.npc.NPC;
import com.zenyte.game.world.entity.player.Player;
import com.zenyte.game.world.entity.player.update.UpdateMask;
import com.zenyte.network.io.RSBuffer;

import java.util.List;

/**
 * @author Kris | 7. mai 2018 : 17:05:32
 * @see <a href="https://www.rune-server.ee/members/kris/">Rune-Server
 * profile</a>}
 * @see <a href="https://rune-status.net/members/kris.354/">Rune-Status
 * profile</a>}
 */
public final class HitMask extends UpdateMask {
    @Override
    public UpdateFlag getFlag() {
        return UpdateFlag.HIT;
    }

    @Override
    public void writePlayer(final RSBuffer buffer, final Player player, final Player processedPlayer) {
        final List<Hit> hits = processedPlayer.getNextHits();
        int length = Math.min(255, hits.size());
        buffer.writeByte128(length);
        Hit hit;
        for (int i = 0; i < length; i++) {
            hit = hits.get(i);
            buffer.writeSmart(hit.getMark());
            buffer.writeSmart(hit.getDamage());
            buffer.writeSmart(hit.getDelay());
        }
        final List<HitBar> bars = processedPlayer.getHitBars();
        length = Math.min(255, bars.size());
        buffer.writeByteC(length);
        HitBar bar;
        for (int i = 0; i < length; i++) {
            bar = bars.get(i);
            buffer.writeSmart(bar.getType());
            buffer.writeSmart(0);//Second bar.
            buffer.writeSmart(0);//Delay.
            buffer.writeByte128(bar.getPercentage());
        }
    }

    @Override
    public void writeNPC(final RSBuffer buffer, final Player player, final NPC npc) {
        final List<Hit> hits = npc.getNextHits();
        int length = Math.min(255, hits.size());
        buffer.writeByteC(length);
        Hit hit;
        for (int i = 0; i < length; i++) {
            hit = hits.get(i);
            buffer.writeSmart(hit.getMark());
            buffer.writeSmart(hit.getDamage());
            buffer.writeSmart(hit.getDelay());
        }
        final List<HitBar> bars = npc.getHitBars();
        length = Math.min(255, bars.size());
        buffer.writeByte128(length);
        HitBar bar;
        for (int i = 0; i < length; i++) {
            bar = bars.get(i);
            buffer.writeSmart(bar.getType());
            buffer.writeSmart(0);//Second bar.
            buffer.writeSmart(0);//Delay.
            buffer.writeByte(bar.getPercentage());
        }
    }
}
