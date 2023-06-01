package com.zenyte.plugins.renewednpc;

import com.zenyte.game.item.Item;
import com.zenyte.game.item.degradableitems.RepairableItem;
import com.zenyte.game.util.Utils;
import com.zenyte.game.world.entity.npc.actions.NPCPlugin;
import com.zenyte.game.world.entity.player.container.impl.Inventory;
import com.zenyte.game.world.entity.player.dialogue.Dialogue;

import java.util.HashMap;

/**
 * @author Tommeh | 21-3-2019 | 19:04
 * @see <a href="https://www.rune-server.ee/members/tommeh/">Rune-Server profile</a>}
 */
public class Perdu extends NPCPlugin {
    @Override
    public void handle() {
        bind("Repair", (player, npc) -> {
            player.getDialogueManager().start(new Dialogue(player, npc) {
                @Override
                public void buildDialogue() {
                    final Inventory inventory = player.getInventory();
                    final HashMap<Integer, RepairableItem> data = new HashMap<>();
                    int total = 0;
                    for (int slot = 0; slot < Inventory.SIZE; slot++) {
                        final Item item = inventory.getItem(slot);
                        if (item == null) {
                            continue;
                        }
                        final RepairableItem repairable = RepairableItem.getItem(item);
                        if (repairable == null || item.getId() == repairable.getIds()[0] || (repairable.ordinal() <= RepairableItem.VERACS_PLATESKIRT.ordinal() && item.getCharges() == 90000)) {
                            continue;
                        }
                        data.put(slot, repairable);
                        total += (int) repairable.getCost(player, item, false);
                    }
                    if (data.isEmpty()) {
                        npc("You currently have no items in your inventory that need repairing.");
                        return;
                    }
                    final Item cost = new Item(995, total);
                    npc("Repairing all items will cost you " + Utils.format(total) + " gold.");
                    options("Do you wish to proceed?", "Yes.", "No.").onOptionOne(() -> {
                        if (inventory.containsItem(cost)) {
                            data.forEach((slot, repairable) -> {
                                final Item item = inventory.getItem(slot);
                                inventory.deleteItem(item);
                                final Item repaired = new Item(repairable.getIds()[0], 1);//Do not add charges cus the default item has 0 for tradability and stackability reasons.
                                inventory.addItem(repaired);
                            });
                            inventory.deleteItem(cost);
                        } else {
                            setKey(5);
                        }
                    });
                    plain(5, "You don't have enough gold to repair your item(s).");
                }
            });
        });
    }

    @Override
    public int[] getNPCs() {
        return new int[]{7456};
    }
}
