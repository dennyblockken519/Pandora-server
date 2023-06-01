package com.zenyte.game.content.skills.hunter.plugins;

import com.zenyte.game.content.drops.table.DropTable;
import com.zenyte.game.content.skills.hunter.node.tables.*;
import com.zenyte.game.content.treasuretrails.ClueItem;
import com.zenyte.game.item.Item;
import com.zenyte.game.item.ItemId;
import com.zenyte.game.item.pluginextensions.ItemPlugin;
import com.zenyte.game.util.Utils;
import com.zenyte.game.world.entity.player.Player;
import com.zenyte.game.world.entity.player.container.impl.Inventory;
import com.zenyte.utils.Ordinal;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

/**
 * @author Corey
 * @since 10/01/2020
 */
public class ImplingJarPlugin extends ItemPlugin {
    @Override
    public void handle() {
        bind("Loot", (player, item, slotId) -> {
            final ImplingJarPlugin.ImplingJar jar = ImplingJar.get(item.getId());
            if (jar == null) {
                return;
            }
            final boolean breakJar = Utils.random(1, 10) == 1;
            if (player.getInventory().getFreeSlots() < (breakJar ? 1 : 2)) {
                player.sendMessage("You'll need to clear some space in your pack before looting the jar.");
                return;
            }
            final Inventory inventory = player.getInventory();
            inventory.deleteItem(item);
            for (final Item it : jar.generateLoot(player)) {
                inventory.addOrDrop(it);
            }
            if (!breakJar) {
                inventory.addOrDrop(new Item(ItemId.IMPLING_JAR));
            } else {
                player.sendFilteredMessage("You break the jar as you try to open it. You throw the shattered remains away.");
            }
        });
    }

    @Override
    public int[] getItems() {
        return ImplingJar.jars.keySet().toIntArray();
    }


    @Ordinal
    public enum ImplingJar {
        BABY(ItemId.BABY_IMPLING_JAR, BabyImplingJarTable.table, new ClueDrop(ClueItem.BEGINNER.getScrollBox(), 50), new ClueDrop(ClueItem.EASY.getScrollBox(), 100)),
        YOUNG(ItemId.YOUNG_IMPLING_JAR, YoungImplingJarTable.table, new ClueDrop(ClueItem.BEGINNER.getScrollBox(), 25), new ClueDrop(ClueItem.EASY.getScrollBox(), 50)),
        GOURMET(ItemId.GOURMET_IMPLING_JAR, GourmetImplingJarTable.table, new ClueDrop(ClueItem.EASY.getScrollBox(), 25)),
        EARTH(ItemId.EARTH_IMPLING_JAR, EarthImplingJarTable.table, new ClueDrop(ClueItem.MEDIUM.getScrollBox(), 100)),
        ESSENCE(ItemId.ESSENCE_IMPLING_JAR, EssenceImplingJarTable.table, new ClueDrop(ClueItem.MEDIUM.getScrollBox(), 50)),
        ECLECTIC(ItemId.ECLECTIC_IMPLING_JAR, EclecticImplingJarTable.table, new ClueDrop(ClueItem.MEDIUM.getScrollBox(), 25)),
        NATURE(ItemId.NATURE_IMPLING_JAR, NatureImplingJarTable.table, new ClueDrop(ClueItem.HARD.getScrollBox(), 100)),
        MAGPIE(ItemId.MAGPIE_IMPLING_JAR, MagpieImplingJarTable.table, new ClueDrop(ClueItem.HARD.getScrollBox(), 50)),
        NINJA(ItemId.NINJA_IMPLING_JAR, NinjaImplingJarTable.table, new ClueDrop(ClueItem.HARD.getScrollBox(), 25)),
        DRAGON(ItemId.DRAGON_IMPLING_JAR, DragonImplingJarTable.table, new ClueDrop(ClueItem.ELITE.getScrollBox(), 50)),
        LUCKY(ItemId.LUCKY_IMPLING_JAR, null) {
            @Override
            public Item[] generateLoot(Player player) {
                Item[] items = LuckyImplingJarTable.table.rollRewards(player);
                for (Item item : items) {
                    player.getCollectionLog().add(item);
                }
                return items;
            }
        };
        public static final Int2ObjectOpenHashMap<ImplingJar> jars;
        private static final ImplingJar[] values = values();

        static {
            Utils.populateMap(values, jars = new Int2ObjectOpenHashMap<>(values.length), ImplingJar::getItemId);
        }

        private final int itemId;
        private final DropTable table;
        private final ClueDrop[] clueDrops;

        ImplingJar(int itemId, DropTable table, ClueDrop... clueDrops) {
            this.itemId = itemId;
            this.table = table;
            this.clueDrops = clueDrops;
        }

        public static ImplingJar get(final int id) {
            return jars.get(id);
        }

        public Item getJarItem() {
            return new Item(itemId);
        }

        public Item[] generateLoot(Player player) {
            for (ClueDrop drop : clueDrops) {
                if (Utils.random(drop.getChance()) == 0) {
                    return new Item[]{table.rollItem(), new Item(drop.getItemId())};
                }
            }
            return new Item[]{table.rollItem()};
        }

        public int getItemId() {
            return this.itemId;
        }

        public DropTable getTable() {
            return this.table;
        }

        public ClueDrop[] getClueDrops() {
            return this.clueDrops;
        }
    }


    private static class ClueDrop {
        private final int itemId;
        private final int chance;

        public ClueDrop(final int itemId, final int chance) {
            this.itemId = itemId;
            this.chance = chance;
        }

        public int getItemId() {
            return this.itemId;
        }

        public int getChance() {
            return this.chance;
        }
    }
}
