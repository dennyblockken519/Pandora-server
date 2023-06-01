package com.zenyte.game.world.entity.player;

import com.zenyte.game.item.Item;
import com.zenyte.game.item.ItemId;
import com.zenyte.game.world.entity.npc.combatdefs.AttackType;
import com.zenyte.game.world.entity.player.action.combat.CombatUtilities;
import com.zenyte.game.world.entity.player.container.impl.equipment.Equipment;
import com.zenyte.game.world.entity.player.container.impl.equipment.EquipmentSlot;
import com.zenyte.plugins.item.Blowpipe;
import mgi.types.config.items.ItemDefinitions;
import org.apache.logging.log4j.util.Strings;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A utility class for manipulating & setting player bonuses.
 *
 * @author Mack
 */
public class Bonuses {
    private final Player player;
    private final Map<Integer, Integer> bonuses;

    /**
     * Creates a new {@link Bonuses} instance for the player.
     *
     * @param player
     */
    public Bonuses(final Player player) {
        this.player = player;
        bonuses = new HashMap<>();
    }

    public void setBonus(final int index, final int bonus) {
        bonuses.put(index, bonus);
    }

    /**
     * Updates the bonus information.
     *
     * @param display
     */
    public void update() {
        if (!bonuses.isEmpty()) {
            bonuses.clear();
        }
        for (int equipmentIndex = 0; equipmentIndex < Equipment.SIZE; equipmentIndex++) {
            final Item item = player.getEquipment().getItem(equipmentIndex);
            if (Objects.nonNull(item) && Objects.nonNull(item.getDefinitions())) {
                final ItemDefinitions defs = item.getDefinitions();
                final int[] data = defs.getBonuses();
                if (data == null) {
                    continue;
                }
                for (final Bonus bonus : Bonus.VALUES) {
                    if (bonus.getBonusIndex() >= data.length) {
                        continue;
                    }
                    final int existing_value = bonuses.getOrDefault(bonus.getBonusIndex(), 0);
                    int b = data[bonus.getBonusIndex()];
                    if (b == 0) {
                        continue;
                    }
                    final int bonusIndex = bonus.getBonusIndex();
                    final int id = item.getId();
                    if ((id == 11283 || id == 21633 || id == 22002) && bonusIndex >= 5 && bonusIndex <= 9 && bonusIndex != 8) {
                        final int charges = player.getShield().getCharges();
                        b += charges > 50 ? 50 : charges < 0 ? 0 : charges;
                    }
                    if (equipmentIndex == 2 && bonus == Bonus.PRAYER && (id == 12851 || id == 12853)) {
                        if (CombatUtilities.hasFullBarrowsSet(player, "Verac's")) {
                            b += 4;
                        }
                    }
                    if (equipmentIndex == 3 && bonus.equals(Bonus.ATT_RANGED)) {
                        final Item wep = player.getWeapon();
                        final int weaponId = player.getEquipment().getId(EquipmentSlot.WEAPON.getSlot());
                        if (weaponId == 12926 && Blowpipe.getBlowpipeAmmunition(wep) != -1) {
                            final ItemDefinitions ammo = ItemDefinitions.get(Blowpipe.getBlowpipeAmmunition(wep));
                            if (ammo.getBonuses() == null) {
                                continue;
                            }
                            b += ammo.getBonuses()[Bonus.ATT_RANGED.bonusIndex];
                        }
                    } else if (equipmentIndex == 3 && bonus.equals(Bonus.RANGE_STRENGTH)) {
                        final Item wep = player.getWeapon();
                        final int weaponId = player.getEquipment().getId(EquipmentSlot.WEAPON.getSlot());
                        if (weaponId == 10033) {
                            continue;
                        }
                        if (weaponId == 12926 && Blowpipe.getBlowpipeAmmunition(wep) != -1) {
                            final ItemDefinitions ammo = ItemDefinitions.get(Blowpipe.getBlowpipeAmmunition(wep));
                            if (ammo.getBonuses() == null) {
                                continue;
                            }
                            b += ammo.getBonuses()[Bonus.RANGE_STRENGTH.bonusIndex];
                        }
                    } else if (equipmentIndex == 13 && bonus.equals(Bonus.RANGE_STRENGTH)) {
                        final int weaponId = player.getEquipment().getId(EquipmentSlot.WEAPON.getSlot());
                        if (weaponId == 10033 || weaponId == 12926 || weaponId == 7170) {
                            continue;
                        }
                        if (weaponId != -1 && weaponId != 19481 && weaponId != 20997) {
                            final ItemDefinitions weapon = ItemDefinitions.get(weaponId);
                            if (weapon.getBonuses() != null && weapon.getBonuses()[11] != 0) {
                                continue;
                            }
                        }
                    }
                    bonuses.put(bonus.getBonusIndex(), existing_value + b);
                }
            }
        }
        if (player.getInterfaceHandler().isVisible(84)) {
            for (final Bonus bonus : Bonus.VALUES) {
                if (bonus.equals(Bonus.UNDEAD)) {
                    final int necklace = player.getEquipment().getId(EquipmentSlot.AMULET);
                    final int percentage = necklace == ItemId.SALVE_AMULET || necklace == ItemId.SALVE_AMULETI ? 15 : necklace == ItemId.SALVE_AMULET_E || necklace == ItemId.SALVE_AMULETEI ? 20 : 0;
                    final String suffix = necklace == ItemId.SALVE_AMULET || necklace == ItemId.SALVE_AMULET_E ? " (melee)" : necklace == ItemId.SALVE_AMULETI || necklace == ItemId.SALVE_AMULETEI ? " (all styles)" : Strings.EMPTY;
                    player.getPacketDispatcher().sendComponentText(84, bonus.getChildId(), bonus.getBonusName() + ": " + percentage + "%" + suffix);
                    continue;
                } else if (bonus.equals(Bonus.SLAYER)) {
                    final Item helmet = player.getHelmet();
                    String percentage = Strings.EMPTY;
                    String suffix = Strings.EMPTY;
                    if (helmet != null) {
                        final String name = helmet.getName().toLowerCase();
                        if (name.contains("black mask") || name.contains("slayer helm")) {
                            final boolean allStyles = name.contains("(i)");
                            percentage = "15";
                            suffix = allStyles ? " (all styles)" : " (melee)";
                        }
                    }
                    player.getPacketDispatcher().sendComponentText(84, bonus.getChildId(), bonus.getBonusName() + ": " + percentage + "%" + suffix);
                    continue;
                }
                final int value = bonuses.getOrDefault(bonus.getBonusIndex(), 0);
                String prefix = "";
                String suffix = "";
                if (value >= 0) {
                    prefix = "+";
                } else {
                    prefix = "";
                }
                if (bonus.equals(Bonus.MAGIC_DAMAGE)) {
                    suffix = "%";
                }
                player.getPacketDispatcher().sendComponentText(84, bonus.getChildId(), bonus.getBonusName() + ": " + prefix + value + suffix);
            }
        }
    }

    /**
     * Gets the bonus value for the specified index.
     *
     * @param bonusIndex
     * @return
     */
    public int getBonus(final int bonusIndex) {
        return bonuses.getOrDefault(bonusIndex, 0);
    }

    public int getBonus(final Bonus bonus) {
        return getBonus(bonus.bonusIndex);
    }

    public int getDefenceBonus(final AttackType type) {
        final Bonuses.Bonus bonus = type.isMagic() ? Bonus.DEF_MAGIC : type.isRanged() ? Bonus.DEF_RANGE : type == AttackType.CRUSH ? Bonus.DEF_CRUSH : type == AttackType.SLASH ? Bonus.DEF_SLASH : Bonus.DEF_STAB;
        return getBonus(bonus.getBonusIndex());
    }

    public enum Bonus {
        ATT_STAB(0, 23, "Stab"),
        ATT_SLASH(1, 24, "Slash"),
        ATT_CRUSH(2, 25, "Crush"),
        ATT_MAGIC(3, 26, "Magic"),
        ATT_RANGED(4, 27, "Range"),
        DEF_STAB(5, 29, "Stab"),
        DEF_SLASH(6, 30, "Slash"),
        DEF_CRUSH(7, 31, "Crush"),
        DEF_MAGIC(8, 32, "Magic"),
        DEF_RANGE(9, 33, "Range"),
        STRENGTH(10, 35, "Melee strength"),
        RANGE_STRENGTH(11, 36, "Ranged strength"),
        MAGIC_DAMAGE(12, 37, "Magic damage"),
        PRAYER(13, 38, "Prayer"),
        UNDEAD(14, 40, "Undead"),
        SLAYER(15, 41, "Slayer");
        public static final Bonus[] VALUES = values();
        private final int bonusIndex;
        private final int childId;
        private final String bonusName;

        Bonus(final int bonusIndex, final int childId, final String bonusName) {
            this.bonusIndex = bonusIndex;
            this.childId = childId;
            this.bonusName = bonusName;
        }

        public int getBonusIndex() {
            return bonusIndex;
        }

        public int getChildId() {
            return childId;
        }

        public String getBonusName() {
            return bonusName;
        }
    }
}
