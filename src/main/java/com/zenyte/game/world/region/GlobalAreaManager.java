package com.zenyte.game.world.region;

import com.zenyte.Game;
import com.zenyte.game.tasks.WorldTasksManager;
import com.zenyte.game.world.Position;
import com.zenyte.game.world.World;
import com.zenyte.game.world.entity.Location;
import com.zenyte.game.world.entity.npc.spawns.NPCSpawnLoader;
import com.zenyte.game.world.entity.player.Player;
import com.zenyte.game.world.region.area.plugins.CycleProcessPlugin;
import com.zenyte.utils.efficientarea.Polygon;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMaps;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import mgi.tools.jagcached.ArchiveType;
import mgi.tools.jagcached.cache.Archive;
import mgi.tools.jagcached.cache.Cache;
import mgi.tools.jagcached.cache.Group;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Predicate;

/**
 * @author Kris | 16. mai 2018 : 15:08:43
 * @see <a href="https://www.rune-server.ee/members/kris/">Rune-Server profile</a>
 */
public class GlobalAreaManager {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GlobalAreaManager.class);
    private static final Logger logger = LoggerFactory.getLogger(GlobalAreaManager.class);
    private static final Queue<Area> areas = new ConcurrentLinkedQueue<>();
    private static final Int2ObjectMap<List<Area>> mappedAreas = Int2ObjectMaps.synchronize(new Int2ObjectOpenHashMap<>());
    private static final Map<Class<?>, Area> mapAreas = new HashMap<>();
    private static final Map<String, Area> namedAreas = new HashMap<>();
    private static final Predicate<Player> removalPredicate = Player::isNulled;

    public static Queue<Area> getAllAreas() {
        return areas;
    }

    public static void add(final Class<? extends Area> c) {
        try {
            if (c.isAnonymousClass() || c.isMemberClass() || Modifier.isAbstract(c.getModifiers())) {
                return;
            }
            if (DynamicArea.class.isAssignableFrom(c)) {
                return;
            }
            final Area area = c.newInstance();
            mapAreas.put(c, area);
            areas.add(area);
            namedAreas.put(area.name(), area);
        } catch (final Exception e) {
            log.error(Strings.EMPTY, e);
        }
    }

    public static Area get(final String name) {
        final com.zenyte.game.world.region.Area area = namedAreas.get(name);
        if (area == null) {
            throw new RuntimeException("Area by the name of " + name + " does not exist.");
        }
        return area;
    }

    @SuppressWarnings("unchecked cast")
    public static <T extends Area> T getArea(@NotNull final Class<T> clazz) {
        final com.zenyte.game.world.region.Area area = mapAreas.get(clazz);
        if (area == null) {
            throw new RuntimeException("Area by the type of " + clazz.getSimpleName() + " does not exist.");
        }
        return (T) area;
    }

    @SuppressWarnings("unchecked cast")
    public static <T extends Area> Optional<T> getOptionalArea(@NotNull final Class<T> clazz) {
        final com.zenyte.game.world.region.Area area = mapAreas.get(clazz);
        if (area == null) {
            return Optional.empty();
        }
        return Optional.of((T) area);
    }

    public static void setInheritance() {
        for (final com.zenyte.game.world.region.Area area : areas) {
            if (!(area instanceof DynamicArea)) {
                Class<?> clazz = area.getClass().getSuperclass();
                while (Area.class.isAssignableFrom(clazz)) {
                    if (clazz == Area.class) {
                        break;
                    }
                    final Class<?> superClass = clazz.getSuperclass();
                    if (!Modifier.isAbstract(clazz.getModifiers())) {
                        final com.zenyte.game.world.region.Area superArea = mapAreas.get(clazz);
                        if (superArea == null) {
                            throw new RuntimeException("Super area for class " + clazz + " cannot be null!");
                        }
                        area.addSuper(superArea);
                        superArea.addExtends(area);
                        break;
                    }
                    clazz = superClass;
                }
            }
        }
    }

    public static void map() {
        final IntOpenHashSet regionList = new IntOpenHashSet(2000);
        final Cache cache = Game.getCacheMgi();
        final Archive archive = cache.getArchive(ArchiveType.MAPS);
        for (int rx = 0; rx < 100; rx++) {
            for (int ry = 0; ry < 256; ry++) {
                final Group group = archive.findGroupByName("m" + rx + "_" + ry);
                final int id = group == null ? -1 : group.getID();
                if (id != -1) {
                    regionList.add(rx << 8 | ry);
                }
            }
        }
        for (int z = 0; z < 4; z++) {
            for (int id : regionList) {
                final int x = (id >> 8) << 6;
                final int y = (id & 255) << 6;
                final Polygon chunkPolygon = new RSPolygon(new int[][]{new int[]{x, y}, new int[]{x + 64, y}, new int[]{x + 64, y + 64}, new int[]{x, y + 64}}, 0).getPolygon();
                final com.zenyte.utils.efficientarea.Area chunkArea = new com.zenyte.utils.efficientarea.Area(chunkPolygon);
                for (final com.zenyte.game.world.region.Area a : areas) {
                    for (final RSPolygon poly : a.getPolygons()) {
                        if (!poly.getPlanes().contains(z)) {
                            continue;
                        }
                        final Polygon polygon = poly.getPolygon();
                        if (!polygon.getBounds2D().intersects(chunkArea.getBounds2D())) {
                            continue;
                        }
                        final com.zenyte.utils.efficientarea.Area area = new com.zenyte.utils.efficientarea.Area(polygon);
                        area.intersect(chunkArea);
                        if (!area.isEmpty()) {
                            final int regionId = regionHash(x, y, z);
                            List<com.zenyte.game.world.region.Area> list = mappedAreas.get(regionId);
                            if (list == null) {
                                list = new ArrayList<>();
                                mappedAreas.put(regionId, list);
                            }
                            list.add(a);
                        }
                    }
                }
            }
        }
        WorldTasksManager.schedule(() -> {
            for (final Player player : World.getPlayers()) {
                GlobalAreaManager.update(player, false, false);
            }
        });
        NPCSpawnLoader.populateAreaMap();
    }

    private static void mapDynamic(final Area dynamicArea) {
        if (dynamicArea instanceof DynamicArea a) {
            for (int x = a.getChunkX(); x <= a.getChunkX() + a.getSizeX(); x++) {
                for (int y = a.getChunkY(); y <= a.getChunkY() + a.getSizeY(); y++) {
                    for (int z = 0; z < 4; z++) {
                        final int regionId = regionHash(x << 3, y << 3, z);
                        List<com.zenyte.game.world.region.Area> list = mappedAreas.get(regionId);
                        if (list == null) {
                            list = new ArrayList<>();
                            mappedAreas.put(regionId, list);
                        }
                        if (!list.contains(dynamicArea)) {
                            list.add(dynamicArea);
                        }
                    }
                }
            }
        }
    }

    private static int regionHash(final int x, final int y, final int z) {
        return (((x >> 6) << 8) + (y >> 6) | z << 16);
    }

    public static void checkIntersections() {
        for (final com.zenyte.game.world.region.Area area : areas) {
            for (final com.zenyte.game.world.region.Area intersectingArea : areas) {
                if (area == intersectingArea) continue;
                if (intersects(area, intersectingArea)) {
                    final HashSet<com.zenyte.game.world.region.Area> areasSuperAreas = new HashSet<>();
                    final HashSet<com.zenyte.game.world.region.Area> intersectingAreasSuperAreas = new HashSet<>();
                    Area a = area;
                    while ((a = a.superArea) != null) {
                        areasSuperAreas.add(a);
                    }
                    a = intersectingArea;
                    while ((a = a.superArea) != null) {
                        intersectingAreasSuperAreas.add(a);
                    }
                    if (!areasSuperAreas.contains(intersectingArea) && !intersectingAreasSuperAreas.contains(area)) {
                        logger.error("Areas intersecting without inheritance: " + area + ", " + intersectingArea);
                    }
                }
            }
        }
    }

    private static boolean intersects(final Area first, final Area other) {
        for (int z = 0; z < 4; z++) {
            final com.zenyte.utils.efficientarea.Area area = new com.zenyte.utils.efficientarea.Area();
            for (final RSPolygon polygon : first.getPolygons()) {
                if (!polygon.getPlanes().contains(z)) continue;
                area.add(new com.zenyte.utils.efficientarea.Area(polygon.getPolygon()));
            }
            if (area.isEmpty()) continue;
            final com.zenyte.utils.efficientarea.Area otherArea = new com.zenyte.utils.efficientarea.Area();
            for (final RSPolygon polygon : other.getPolygons()) {
                if (!polygon.getPlanes().contains(z)) continue;
                otherArea.add(new com.zenyte.utils.efficientarea.Area(polygon.getPolygon()));
            }
            if (otherArea.isEmpty() || !area.getBounds2D().intersects(otherArea.getBounds2D())) continue;
            area.intersect(otherArea);
            if (!area.isEmpty()) return true;
        }
        return false;
    }

    public static void add(final Area area) {
        areas.add(area);
        mapDynamic(area);
    }

    public static void remove(final Area area) {
        areas.remove(area);
        for (final List<com.zenyte.game.world.region.Area> map : mappedAreas.values()) {
            map.remove(area);
        }
    }

    public static void update(final Player player, final boolean login, final boolean logout) {
        final com.zenyte.game.world.region.Area area = player.getArea();
        final com.zenyte.game.world.region.Area a = getArea(player.getLocation());
        if (area != a || logout || login) {
            if (area != null) {
                area.removePlayer(player, logout);
            }
            if (logout) {
                return;
            }
            if (a != null) {
                a.addPlayer(player, login);
            }
        }
    }

    public static Area getArea(final Position position) {
        final Location tile = position.getPosition();
        final List<com.zenyte.game.world.region.Area> areas = mappedAreas.get(regionHash(tile.getX(), tile.getY(), tile.getPlane()));
        if (areas == null) {
            return null;
        }
        for (final com.zenyte.game.world.region.Area a : areas) {
            final com.zenyte.game.world.region.Area extension = a.getExtension(position);
            if (extension.inside(position.getPosition())) return extension;
        }
        return null;
    }

    public static void process() {
        for (final Area area : areas) {
            if (area.getAreaTimer().incrementAndGet() % 10 == 0 && !area.getPlayers().isEmpty()) {
                area.getPlayers().removeIf(removalPredicate);
            }
            if (area instanceof CycleProcessPlugin) {
                try {
                    ((CycleProcessPlugin) area).process();
                } catch (Exception e) {
                    log.error(Strings.EMPTY, e);
                }
            }
        }
    }
}
