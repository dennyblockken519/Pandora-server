package com.zenyte.game.content.skills.runecrafting;

import com.google.common.base.Preconditions;
import com.zenyte.game.content.achievementdiary.DiaryReward;
import com.zenyte.game.content.achievementdiary.diaries.*;
import com.zenyte.game.content.follower.Pet;
import com.zenyte.game.content.follower.impl.SkillingPet;
import com.zenyte.game.content.treasuretrails.clues.SherlockTask;
import com.zenyte.game.item.Item;
import com.zenyte.game.world.entity.masks.Animation;
import com.zenyte.game.world.entity.masks.Graphics;
import com.zenyte.game.world.entity.player.Action;
import com.zenyte.game.world.entity.player.Skills;
import com.zenyte.game.world.entity.player.dailychallenge.challenge.SkillingChallenge;
import com.zenyte.plugins.itemonitem.ChipDarkEssenceBlockItemAction;

import java.util.OptionalInt;

/**
 * @author Kris | 19. dets 2017 : 2:47.03
 * @see <a href="https://www.rune-server.ee/members/kris/">Rune-Server profile</a>}
 * @see <a href="https://rune-status.net/members/kris.354/">Rune-Status profile</a>}
 */
public final class BasicRunecraftingAction extends Action {
    private static final Animation RUNECRAFTING_ANIM = new Animation(791);
    private static final Graphics RUNECRAFTING_GFX = new Graphics(186, 0, 96);
    private final Runecrafting rune;

    public BasicRunecraftingAction(final Runecrafting rune) {
        this.rune = rune;
    }

    @Override
    public boolean start() {
        if (player.getSkills().getLevel(Skills.RUNECRAFTING) < rune.getLevel()) {
            player.sendMessage("You need at least " + rune.getLevel() + " Runecrafting to runecraft " + rune.toString().replace("_", " ").toLowerCase() + "s.");
            return false;
        }
        if (rune.getEssenceType() == 2 && !player.getInventory().containsItem(7936, 1)) {
            player.sendMessage("You need some pure essence to runecraft " + rune.toString().toLowerCase().replace("_", " ") + "s.");
            return false;
        } else if (rune.getEssenceType() == 1 && !player.getInventory().containsItem(1436, 1) && !player.getInventory().containsItem(7936, 1)) {
            player.sendMessage("You need some " + (rune.getEssenceType() == 1 ? "rune or " : "") + "pure essence to runecraft " + rune.toString().toLowerCase().replace("_", " ") + "s.");
            return false;
        } else if (rune.getEssenceType() == 3 && !player.getInventory().containsItem(7938, 1)) {
            player.sendMessage("You need some dark essence fragments to runecraft " + rune.toString().toLowerCase().replace("_", " ") + "s.");
            return false;
        }
        player.setAnimation(RUNECRAFTING_ANIM);
        player.setGraphics(RUNECRAFTING_GFX);
        player.lock(2);
        return true;
    }

    @Override
    public boolean process() {
        return true;
    }

    @Override
    public int processWithDelay() {
        int runes = rune.getEssenceType() == 2 ? player.getInventory().getAmountOf(7936) : (player.getInventory().getAmountOf(7936) + player.getInventory().getAmountOf(1436));
        if (rune.getEssenceType() == 1) {
            player.getInventory().deleteItem(1436, runes);
        }
        if (rune.getEssenceType() == 1 || rune.getEssenceType() == 2) {
            player.getInventory().deleteItem(7936, runes);
        } else if (rune.getEssenceType() == 3) {
            final OptionalInt slot = ChipDarkEssenceBlockItemAction.findFragmentsSlot(player);
            final int slotId = slot.orElseThrow(RuntimeException::new);
            final Item item = player.getInventory().getItem(slotId);
            Preconditions.checkArgument(item.getId() == 7938);
            runes = item.getCharges();
            player.getInventory().deleteItem(slotId, item);
        }
        int multiplier = rune.getDoubleRunes() == -1 ? 0 : (int) Math.floor(player.getSkills().getLevel(Skills.RUNECRAFTING) / rune.getDoubleRunes());
        multiplier += 1;
        int amount = runes * multiplier;
        if (DiaryReward.RADAS_BLESSING4.eligibleFor(player) && rune.equals(Runecrafting.BLOOD_RUNE)) {
            amount *= 1.1;
        }
        final double experience = rune.getExperience() * runes;
        if (rune.equals(Runecrafting.COSMIC_RUNE)) {
            SherlockTask.CRAFT_MULTIPLE_COSMIC_RUNES.progress(player);
        }
        if (amount >= 140 && rune.equals(Runecrafting.MIND_RUNE)) {
            player.getAchievementDiaries().update(FaladorDiary.CRAFT_MIND_RUNES);
        } else if (amount >= 252 && rune.equals(Runecrafting.AIR_RUNE)) {
            player.getAchievementDiaries().update(FaladorDiary.CRAFT_AIR_RUNES);
        } else if (rune.equals(Runecrafting.COSMIC_RUNE)) {
            if (amount >= 56) {
                player.getAchievementDiaries().update(LumbridgeDiary.CRAFT_COSMIC_RUNES);
            }
            player.getDailyChallengeManager().update(SkillingChallenge.CRAFT_COSMIC_RUNES, amount * 2);
        } else if (amount >= 56 && rune.equals(Runecrafting.ASTRAL_RUNE)) {
            player.getAchievementDiaries().update(FremennikDiary.CRAFT_ASTRAL_RUNES);
        } else if (rune.equals(Runecrafting.DEATH_RUNE)) {
            player.getDailyChallengeManager().update(SkillingChallenge.CRAFT_DEATH_RUNES, amount * 2);
            player.getAchievementDiaries().update(ArdougneDiary.CRAFT_DEATH_RUNES);
        } else if (rune.equals(Runecrafting.NATURE_RUNE)) {
            if (amount >= 56) {
                player.getAchievementDiaries().update(KaramjaDiary.CRAFT_56_NATURE_RUNES);
            }
            SherlockTask.CRAFT_A_NATURE_RUNE.progress(player);
            player.getAchievementDiaries().update(KaramjaDiary.CRAFT_NATURE_RUNES);
            player.getDailyChallengeManager().update(SkillingChallenge.CRAFT_NATURE_RUNES, amount * 2);
        } else if (rune.equals(Runecrafting.EARTH_RUNE)) {
            if (amount >= 100) {
                player.getAchievementDiaries().update(VarrockDiary.CRAFT_100_EARTH_RUNES);
            }
            player.getAchievementDiaries().update(VarrockDiary.CRAFT_EARTH_RUNES);
        } else if (rune.equals(Runecrafting.WATER_RUNE)) {
            if (amount >= 140) {
                player.getAchievementDiaries().update(LumbridgeDiary.CRAFT_140_WATER_RUNES);
            }
            player.getAchievementDiaries().update(LumbridgeDiary.CRAFT_WATER_RUNES);
            player.getDailyChallengeManager().update(SkillingChallenge.CRAFT_WATER_RUNES, amount * 2);
        } else if (rune.equals(Runecrafting.FIRE_RUNE)) {
            player.getDailyChallengeManager().update(SkillingChallenge.CRAFT_FIRE_RUNES, amount * 2);
        } else if (rune.equals(Runecrafting.LAW_RUNE)) {
            player.getDailyChallengeManager().update(SkillingChallenge.CRAFT_LAW_RUNES, amount * 2);
        } else if (rune.equals(Runecrafting.SOUL_RUNE)) {
            player.getDailyChallengeManager().update(SkillingChallenge.CRAFT_SOUL_RUNES, amount * 2);
        } else if (rune.equals(Runecrafting.WRATH_RUNE)) {
            player.getDailyChallengeManager().update(SkillingChallenge.CRAFT_WRATH_RUNES, amount * 2);
        } else if (rune.equals(Runecrafting.BLOOD_RUNE)) {
            player.getAchievementDiaries().update(KourendDiary.CRAFT_ONE_OR_MORE_BLOOD_RUNES);
        }
        player.getSkills().addXp(Skills.RUNECRAFTING, experience);
        player.getInventory().addItem(new Item(rune.getRuneId(), amount * 2));
        player.sendFilteredMessage("You bind the Temple's power into " + rune.toString().replace("_", " ").toLowerCase() + "s.");
        player.sendFilteredMessage("The gods bless your efforts and grant you extra runes.");
        if (player.getFollower() != null) {
            final Pet pet = player.getFollower().getPet();
            if (SkillingPet.isRiftGuardian(pet) && !player.getBooleanAttribute("rift_guardian_colour_lock")) {
                player.setPetId(rune.getPet().getPetId());
                player.getFollower().setTransformation(rune.getPet().getPetId());
            }
        }
        final double chance = experience > 2500000 ? 0.5F : (experience / 5000000);
        rune.getPet().roll(player, (int) (1.0F / chance) - 1);
        return -1;
    }
}
