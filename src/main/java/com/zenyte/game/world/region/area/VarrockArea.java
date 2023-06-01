package com.zenyte.game.world.region.area;

import com.zenyte.game.world.region.RSPolygon;

/**
 * @author Kris | 16. mai 2018 : 16:21:46
 * @see <a href="https://www.rune-server.ee/members/kris/">Rune-Server profile</a>}
 * @see <a href="https://rune-status.net/members/kris.354/">Rune-Status profile</a>}
 */
public final class VarrockArea extends KingdomOfMisthalin {

    @Override
    public RSPolygon[] polygons() {
        return new RSPolygon[]{new RSPolygon(new int[][]{{3174, 3425}, {3174, 3399}, {3181, 3399}, {3182, 3398}, {3182, 3382},
                {3203, 3382}, {3203, 3378}, {3207, 3378}, {3207, 3381}, {3209, 3381}, {3209, 3382}, {3242, 3382},
                {3242, 3380}, {3245, 3380}, {3245, 3382}, {3253, 3382}, {3253, 3380}, {3265, 3380}, {3265, 3376},
                {3274, 3376}, {3274, 3377}, {3275, 3377}, {3276, 3378}, {3277, 3377}, {3286, 3377}, {3289, 3380},
                {3289, 3383}, {3288, 3384}, {3288, 3389}, {3283, 3394}, {3284, 3394}, {3284, 3395}, {3288, 3391},
                {3288, 3407}, {3287, 3408}, {3278, 3408}, {3278, 3407}, {3276, 3407}, {3273, 3410}, {3273, 3436},
                {3270, 3436}, {3270, 3463}, {3262, 3471}, {3262, 3491}, {3261, 3492}, {3254, 3492}, {3251, 3495},
                {3251, 3501}, {3249, 3501}, {3249, 3502}, {3235, 3502}, {3229, 3508}, {3228, 3507}, {3201, 3507},
                {3197, 3503}, {3197, 3502}, {3191, 3496}, {3191, 3492}, {3190, 3492}, {3190, 3488}, {3191, 3488},
                {3191, 3478}, {3188, 3475}, {3188, 3467}, {3174, 3467}})};
    }

    @Override
    public String name() {
        return "Varrock";
    }

}
