package com.zenyte.game.world.entity.player.dialogue;

import com.zenyte.game.content.event.easter2020.EasterConstants;

public enum Expression {
    CALM(588),
    ANXIOUS(589),
    CALM_TALK(590),
    DEFAULT(591),
    EVIL(592),
    BAD(593),
    WICKED(594),
    ANNOYED(595),
    DISTRESSED(596),
    AFFLICTED(597),
    ALMOST_CRYING(598),
    DRUNK_LEFT(600),
    DRUNK_RIGHT(601),
    DISINTERESTED(602),
    SLEEPY(603),
    PLAIN_EVIL(604),
    LAUGH(604),
    SNIGGER(606),
    HAVE_FUN(607),
    GUFFAW(608),
    EVIL_LAUGH(609),
    SAD(610),
    MORE_SAD(611),
    ON_ONE_HAND(612),
    NEARLY_CRYING(613),
    ANGRY(614),
    FURIOUS(615),
    ENRAGED(616),
    MAD(617),
    WEISS_TROLL_NORMAL(8154),
    ORRVOR_QUO_MATEN(8215),
    IKKLE_HYDRA(8265),
    HIGH_REV_NORMAL(15073),
    HIGH_REV_SHOCKED(15075),
    HIGH_REV_SAD(15076),
    HIGH_REV_SCARED(15077),
    HIGH_REV_MAD(15078),
    HIGH_REV_WONDERING(15079),
    HIGH_REV_JOLLY(15080),
    HIGH_REV_HAPPY(15081),
    EASTER_BUNNY_SAD(EasterConstants.EXPRESSION_SAD),
    EASTER_BUNNY_HAPPY(EasterConstants.EXPRESSION_HAPPY),
    EASTER_BUNNY_VERY_HAPPY(EasterConstants.EXPRESSION_VERY_HAPPY),
    EASTER_BUNNY_NORMAL(EasterConstants.EXPRESSION_NORMAL),
    EASTER_BUNNY_SAY_NO(EasterConstants.EXPRESSION_SAY_NO),
    EASTER_BIRD_HAPPY(EasterConstants.BIRD_EXPRESSION_HAPPY),
    EASTER_BIRD_CHATTY(EasterConstants.BIRD_EXPRESSION_CHATTY),
    EASTER_BIRD_DRUNK(EasterConstants.BIRD_EXPRESSION_DRUNK),
    EASTER_BIRD_SAD(EasterConstants.BIRD_EXPRESSION_SAD),
    EASTER_BIRD_SAY_NO(EasterConstants.BIRD_EXPRESSION_SAY_NO),
    EASTER_BIRD_SNORING(EasterConstants.BIRD_EXPRESSION_SNORING);
    private final int id;

    Expression(final int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }
}
