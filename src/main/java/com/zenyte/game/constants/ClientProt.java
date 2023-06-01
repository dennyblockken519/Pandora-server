package com.zenyte.game.constants;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import static com.zenyte.Constants.UNIQUE_PACKET_LIMIT;

/**
 * Sorted by Kris.
 *
 * @author Tommeh | 28 jul. 2018 | 12:45:15 | @author Kris | 23. sept 2018 : 02:09:42
 * @see <a href="https://www.rune-server.ee/members/tommeh/">Rune-Server profile</a>
 * @see <a href="https://www.rune-server.ee/members/kris/">Rune-Server profile</a>
 * @see <a href="https://rune-status.net/members/kris.354/">Rune-Status profile</a>
 */
public enum ClientProt {
    IF3_ACTION10(0, 8, UNIQUE_PACKET_LIMIT),
    OPNPC2(1, 3, UNIQUE_PACKET_LIMIT),
    OPHELDT(2, 14, UNIQUE_PACKET_LIMIT),
    OPLOC4(3, 7, UNIQUE_PACKET_LIMIT),
    LOGIN_INFO(4, -1, UNIQUE_PACKET_LIMIT),
    OPOBJ1(5, 7, UNIQUE_PACKET_LIMIT),
    OPOBJ3(6, 7, UNIQUE_PACKET_LIMIT),
    OPHELD4(7, 8, UNIQUE_PACKET_LIMIT),
    RESUME_P_STRINGDIALOG(8, -1, UNIQUE_PACKET_LIMIT),
    PACKET_9(9, 4, UNIQUE_PACKET_LIMIT),
    OPLOC1(10, 7, UNIQUE_PACKET_LIMIT),
    PING_STATISTICS(11, 10, UNIQUE_PACKET_LIMIT),
    SET_APPEARANCE(12, 13, UNIQUE_PACKET_LIMIT),
    CLAN_KICKUSER(13, -1, UNIQUE_PACKET_LIMIT),
    IF3_ACTION8(14, 8, UNIQUE_PACKET_LIMIT),
    CHAT_SETMODE(15, 3, UNIQUE_PACKET_LIMIT),
    BUG_REPORT(16, -2, UNIQUE_PACKET_LIMIT),
    RESUME_P_COUNTDIALOG(17, 4, UNIQUE_PACKET_LIMIT),
    OPOBJU(18, 15, UNIQUE_PACKET_LIMIT),
    IF3_ACTION4(19, 8, UNIQUE_PACKET_LIMIT),
    CLOSE_MODAL(20, 0, UNIQUE_PACKET_LIMIT),
    IF3_ACTION2(21, 8, UNIQUE_PACKET_LIMIT),
    NO_TIMEOUT(22, 0, UNIQUE_PACKET_LIMIT),
    OPNPCU(23, 11, UNIQUE_PACKET_LIMIT),
    OPHELD6(24, 2, UNIQUE_PACKET_LIMIT),
    MESSAGE_PRIVATE(25, -2, UNIQUE_PACKET_LIMIT),
    IF1_ACTION2(26, 8, UNIQUE_PACKET_LIMIT),
    OPPLAYER4(27, 3, UNIQUE_PACKET_LIMIT),
    IGNORELIST_DEL(28, -1, UNIQUE_PACKET_LIMIT),
    OPOBJ4(29, 7, UNIQUE_PACKET_LIMIT),
    OPNPC6(30, 2, UNIQUE_PACKET_LIMIT),
    OPNPC5(31, 3, UNIQUE_PACKET_LIMIT),
    IF1_ACTION5(32, 8, UNIQUE_PACKET_LIMIT),
    OPNPC3(33, 3, UNIQUE_PACKET_LIMIT),
    EVENT_MOUSE_MOVE(34, -1, UNIQUE_PACKET_LIMIT),
    WINDOW_STATUS(35, 5, UNIQUE_PACKET_LIMIT),
    OPLOC6(36, 2, UNIQUE_PACKET_LIMIT),
    FREECAM_EXIT(37, 0, UNIQUE_PACKET_LIMIT),
    PLAYER_REPORT(38, -1, UNIQUE_PACKET_LIMIT),
    EVENT_CAMERA_POSITION(39, 4, UNIQUE_PACKET_LIMIT),
    IF3_ACTION5(40, 8, UNIQUE_PACKET_LIMIT),
    EVENT_MOUSE_CLICK(41, 6, UNIQUE_PACKET_LIMIT),
    RESUME_P_OBJDIALOG(42, 2, UNIQUE_PACKET_LIMIT),
    PACKET_43(43, -1, UNIQUE_PACKET_LIMIT),
    OPLOCU(44, 15, UNIQUE_PACKET_LIMIT),
    DOUBLE_CLICK_WORLDMAP(45, 4, UNIQUE_PACKET_LIMIT),
    IF1_ACTION1(46, 8, UNIQUE_PACKET_LIMIT),
    OPPLAYER1(47, 3, UNIQUE_PACKET_LIMIT),
    IF3_ACTION3(48, 8, UNIQUE_PACKET_LIMIT),
    EVENT_MOUSE_IDLE(49, 0, UNIQUE_PACKET_LIMIT),
    OPPLAYER8(50, 3, UNIQUE_PACKET_LIMIT),
    OPOBJ5(51, 7, UNIQUE_PACKET_LIMIT),
    MOVE_MINIMAPCLICK(52, -1, 20),
    OPOBJT(53, 13, UNIQUE_PACKET_LIMIT),
    FRIENDLIST_DEL(54, -1, UNIQUE_PACKET_LIMIT),
    OPPLAYERT(55, 9, UNIQUE_PACKET_LIMIT),
    OPPLAYER2(56, 3, UNIQUE_PACKET_LIMIT),
    IF_BUTTOND(57, 16, UNIQUE_PACKET_LIMIT),
    OPHELD5(58, 8, UNIQUE_PACKET_LIMIT),
    OPNPC4(59, 3, UNIQUE_PACKET_LIMIT),
    COMMAND(60, -1, UNIQUE_PACKET_LIMIT),
    CLICK_WORLDMAP(61, 9, UNIQUE_PACKET_LIMIT),
    OPPLAYER3(62, 3, UNIQUE_PACKET_LIMIT),
    OPHELDU(63, 16, UNIQUE_PACKET_LIMIT),
    IF1_ACTION4(64, 8, UNIQUE_PACKET_LIMIT),
    IF1_ACTION3(65, 8, UNIQUE_PACKET_LIMIT),
    IF3_ACTION6(66, 8, UNIQUE_PACKET_LIMIT),
    EVENT_KEYBOARD(67, -2, UNIQUE_PACKET_LIMIT),
    IF3_ACTION1(68, 8, UNIQUE_PACKET_LIMIT),
    OP_MODEL(69, 4, UNIQUE_PACKET_LIMIT),
    RESUME_PAUSEBUTTON(70, 6, UNIQUE_PACKET_LIMIT),
    OPNPC1(71, 3, UNIQUE_PACKET_LIMIT),
    OPHELD3(72, 8, UNIQUE_PACKET_LIMIT),
    EVENT_APPLET_FOCUS(73, 1, UNIQUE_PACKET_LIMIT),
    OPOBJ2(74, 7, UNIQUE_PACKET_LIMIT),
    OPPLAYER7(75, 3, UNIQUE_PACKET_LIMIT),
    MAP_BUILD_COMPLETE(76, 0, UNIQUE_PACKET_LIMIT),
    FRIEND_SETRANK(77, -1, UNIQUE_PACKET_LIMIT),
    CLAN_JOINCHAT_LEAVECHAT(78, -1, UNIQUE_PACKET_LIMIT),
    OPLOC2(79, 7, UNIQUE_PACKET_LIMIT),
    OPHELDD(80, 9, UNIQUE_PACKET_LIMIT),
    OPPLAYERU(81, 11, UNIQUE_PACKET_LIMIT),
    PACKET_82(82, 16, UNIQUE_PACKET_LIMIT),
    OPPLAYER5(83, 3, UNIQUE_PACKET_LIMIT),
    IF3_ACTION9(84, 8, UNIQUE_PACKET_LIMIT),
    IF3_ACTION7(85, 8, UNIQUE_PACKET_LIMIT),
    OPNPCT(86, 9, UNIQUE_PACKET_LIMIT),
    OPHELD1(87, 8, UNIQUE_PACKET_LIMIT),
    FRIENDLIST_ADD(88, -1, UNIQUE_PACKET_LIMIT),
    OPLOC3(89, 7, UNIQUE_PACKET_LIMIT),
    IGNORELIST_ADD(90, -1, UNIQUE_PACKET_LIMIT),
    PACKET_91(91, -1, UNIQUE_PACKET_LIMIT),
    OPLOCT(92, 13, UNIQUE_PACKET_LIMIT),
    OPPLAYER6(93, 3, UNIQUE_PACKET_LIMIT),
    OPLOC5(94, 7, UNIQUE_PACKET_LIMIT),
    RESUME_P_NAMEDIALOG(95, -1, UNIQUE_PACKET_LIMIT),
    MOVE_GAME_CLICK(96, -1, 20),
    MESSAGE_PUBLIC(97, -1, UNIQUE_PACKET_LIMIT),
    OPHELD2(98, 8, UNIQUE_PACKET_LIMIT),
    PACKET_99(99, 7, UNIQUE_PACKET_LIMIT);
    private static final ClientProt[] values = values();
    private static final Int2ObjectMap<ClientProt> valueMap = new Int2ObjectOpenHashMap<>(values.length);

    static {
        for (final ClientProt constants : values()) {
            if (valueMap.put(constants.opcode, constants) != null) {
                throw new RuntimeException("OVERLAPPING OPCODE: " + constants.opcode);
            }
        }
    }

    private final int opcode;
    private final int size;
    private final int limit;

    ClientProt(final int opcode, final int size, final int limit) {
        this.opcode = opcode;
        this.size = size;
        this.limit = limit;
    }

    public static ClientProt get(final int opcode) {
        return valueMap.get(opcode);
    }

    /**
     * Gets the size of the packet.
     *
     * @param opcode the packet id.
     * @return size of the packet.
     * @throws IllegalStateException if the packet doesn't exist, throws illegal state exception.
     */
    public static int getSize(final int opcode) throws IllegalStateException {
        final ClientProt packet = valueMap.get(opcode);
        if (packet == null) {
            throw new IllegalStateException("Illegal opcode: " + opcode);
        }
        return packet.getSize();
    }

    public int getOpcode() {
        return this.opcode;
    }

    public int getSize() {
        return this.size;
    }

    public int getLimit() {
        return this.limit;
    }
}
