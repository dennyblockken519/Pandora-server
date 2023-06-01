package com.zenyte.game.packet;

import com.zenyte.Constants;

/**
 * @author Tommeh | 28 jul. 2018 | 14:10:53
 * @see <a href="https://www.rune-server.ee/members/tommeh/">Rune-Server
 * profile</a>}
 */
public enum ServerProt {
    REBUILD_NORMAL(0, -2),
    REBUILD_REGION(51, -2),
    IF_OPENTOP(84, 2),
    IF_OPENSUB(77, 7),
    IF_MOVESUB(82, 8),
    IF_CLOSESUB(9, 4),
    IF_SETEVENTS(48, 12),
    IF_SETSCROLLPOS(76, 6),
    IF_SETANIM(61, 6),
    IF_SETOBJECT(20, 10),
    UPDATE_SITESETTINGS(37, -1),
    LOGOUT_TRANSFER(47, -1),
    TRIGGER_ONDIALOGABORT(52, 0),
    IF_SETNPCHEAD(35, 6),
    REFLECTION_CHECKER(53, -2),
    IF_SETMODEL(31, 6),
    IF_SETPOSITION(59, 8),
    IF_SETCOLOUR(24, 6),
    IF_CLEARITEMS(7, 4),
    IF_SETHIDE(21, 5),
    FRIENDLIST_LOADED(29, 0),
    IF_SETANGLE(50, 10),
    IF_SETPLAYERHEAD(30, 4),
    IF_SETTEXT(19, -2),
    IF_MODEL_ROTATE(41, 8),
    GAMEFRAME_INIT(56, -2),
    PLAYER_INFO(79, -2),
    NPC_INFO_SMALL(33, -2),
    NPC_INFO_LARGE(81, -2),
    UPDATE_IGNORELIST(32, -2),
    UPDATE_FRIENDLIST(58, -2),
    UPDATE_RUNENERGY(60, 1),
    UPDATE_RUNWEIGHT(71, 2),
    UPDATE_ZONE_PARTIAL_FOLLOWS(64, 2),
    UPDATE_ZONE_PARTIAL_ENCLOSED(17, -2),
    UPDATE_REBOOT_TIMER(72, 2),
    SYNC_CLIENT_VARCACHE(73, 0),
    UPDATE_INV_PARTIAL(44, -2),
    UPDATE_INV_FULL(70, -2),
    UPDATE_STAT(22, 6),
    UPDATE_UID192(57, 28),
    MESSAGE_PRIVATE(10, -2),
    MESSAGE_PRIVATE_ECHO(38, -2),
    MESSAGE_GAME(3, -1),
    MESSAGE_CLANCHANNEL(54, -1),
    SET_MAP_FLAG(67, 2),
    MINIMAP_TOGGLE(74, 1),
    SET_PLAYER_OP(66, -1),
    CHAT_FILTER_SETTINGS(80, 2),
    SET_PRIVATE_CHAT_FILTER(68, 1),
    SET_HINTARROW(36, 6),
    OBJ_ADD(26, 5),
    OBJ_UPDATE(15, 7),
    OBJ_DEL(14, 3),
    UPDATE_ZONE_FULL_FOLLOWS(25, 2),
    LOC_ANIM(49, 4),
    LOC_ADD(6, 4),
    LOC_DEL(13, 2),
    CAM_RESET(2, 0),
    CAM_LOOKAT(83, 6),
    CAM_MOVETO(75, 6),
    CAM_SHAKE(39, 4),
    FREE_CAM(34, 1),
    SET_TRADINGPOST(27, -2),
    SET_CAMTYPE(5, 4),
    VARP_SMALL(63, 3),
    VARP_LARGE(4, 6),
    CLANCHANNEL_MEMBER(45, -1),
    CLANCHANNEL_FULL(43, -2),
    MIDI_SONG_LOCATION(40, 5),
    MIDI_SONG(23, 2),
    SYNTH_SOUND(65, 5),
    AREA_SOUND(12, 5),
    UPDATE_INV_STOP_TRANSMIT(46, 2),
    RESET_CLIENT_VARCACHE(78, 0),
    RESET_ANIMS(28, 0),
    RUNCLIENTSCRIPT(62, -2),
    SPOTANIM_SPECIFIC(8, 6),
    GRAND_EXCHANGE_OFFER(55, 20),
    MAP_PROJANIM(11, 15),
    ATTACHED_PLAYER_OBJECT(42, 14),
    PING_STATISTICS_REQUEST(69, 8),
    OPEN_URL(18, -2),
    LOGOUT(1, 0),
    HEATMAP(16, 1);
    private final int opcode;
    private final int size;

    ServerProt(final int opcode, final int size) {
        this.opcode = opcode;
        this.size = size;
    }

    public int getInitialSize() {
        return size >= 0 ? size : 16;
    }

    public int getCapacity() {
        return size >= 0 ? size : size == -1 ? 255 : Constants.MAX_SERVER_BUFFER_SIZE;
    }

    public int getOpcode() {
        return this.opcode;
    }

    public int getSize() {
        return this.size;
    }
}
