package com.zenyte.game.ui;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Modifier;

/**
 * @author Kris | 19/10/2018 01:48
 * @see <a href="https://www.rune-server.ee/members/kris/">Rune-Server profile</a>
 */
public class NewInterfaceHandler {
    public static final Int2ObjectOpenHashMap<Interface> INTERFACES = new Int2ObjectOpenHashMap<>();
    private static final Logger log = LoggerFactory.getLogger(NewInterfaceHandler.class);

    public static void add(final Class<? extends Interface> clazz) {
        try {
            if (Modifier.isAbstract(clazz.getModifiers())) {
                return;
            }
            final Interface instance = clazz.newInstance();
            instance.attach();
            instance.build();
            INTERFACES.put(instance.getInterface().getId(), instance);
        } catch (Exception e) {
            log.error(Strings.EMPTY, e);
        }
    }
}
