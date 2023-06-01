package mgi.types.config;

import com.zenyte.Game;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import mgi.tools.jagcached.ArchiveType;
import mgi.tools.jagcached.GroupType;
import mgi.tools.jagcached.cache.Archive;
import mgi.tools.jagcached.cache.Cache;
import mgi.tools.jagcached.cache.File;
import mgi.tools.jagcached.cache.Group;
import mgi.types.Definitions;
import mgi.utilities.ByteBuffer;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

/**
 * @author Kris | 12. dets 2017 : 1:19.15
 * @see <a href="https://www.rune-server.ee/members/kris/">Rune-Server profile</a>}
 * @see <a href="https://rune-status.net/members/kris.354/">Rune-Status profile</a>}
 */
public final class ObjectDefinitions implements Definitions, Cloneable, TransmogrifiableType {
    private int id;
    private String name;
    public static ObjectDefinitions[] definitions;
    private int varbit;
    private int optionsInvisible;
    private int[] models;
    private int[] types;
    private int[] transformedIds;
    private int ambientSoundId;
    private int varp;
    private int supportItems;
    private int[] anIntArray100;
    private int mapIconId;
    private int sizeX;
    private int clipType;
    private boolean isRotated;
    private int sizeY;
    private boolean projectileClip;
    private int anInt455;
    private boolean nonFlatShading;
    private int contouredGround;
    private int anInt456;
    private boolean modelClipped;
    private int ambient;
    private String[] options;
    private int contrast;
    private int anInt457;
    private boolean hollow;
    private int animationId;
    private int modelSizeX;
    private int decorDisplacement;
    private int modelSizeHeight;
    private int modelSizeY;
    private int[] modelColours;
    private boolean clipped;
    private short[] modelTexture;
    private int mapSceneId;
    private int[] replacementColours;
    private int offsetX;
    private short[] replacementTexture;
    private int offsetHeight;
    private int offsetY;
    private boolean obstructsGround;
    private int accessBlockFlag;
    private int finalTransformation;
    private Int2ObjectOpenHashMap<Object> parameters;

    public ObjectDefinitions() {
        setDefaults();
    }

    public ObjectDefinitions(final int id, final ByteBuffer buffer) {
        this.id = id;
        setDefaults();
        decode(buffer);
    }

    public static ObjectDefinitions getOrThrow(final int id) {
        final ObjectDefinitions object = get(id);
        if (object == null) {
            throw new IllegalStateException();
        }
        return object;
    }

    public static ObjectDefinitions get(final int id) {
        if (id < 0 || id >= definitions.length) {
            return null;
        }
        return definitions[id];
    }

    @Override
    public int defaultId() {
        return finalTransformation;
    }

    @Override
    public void load() {
        final Cache cache = Game.getCacheMgi();
        final Archive configs = cache.getArchive(ArchiveType.CONFIGS);
        final Group objects = configs.findGroupByID(GroupType.OBJECT);
        definitions = new ObjectDefinitions[objects.getHighestFileId()];//Hard cap at 40k for now.
        for (int id = 0; id < objects.getHighestFileId(); id++) {
            final File file = objects.findFileByID(id);
            if (file == null) {
                continue;
            }
            final ByteBuffer buffer = file.getData();
            if (buffer == null) {
                continue;
            }
            definitions[id] = new ObjectDefinitions(id, buffer);
        }
    }

    private void setDefaults() {
        name = "null";
        sizeX = 1;
        sizeY = 1;
        clipType = 2;
        projectileClip = true;
        optionsInvisible = -1;
        contouredGround = -1;
        nonFlatShading = false;
        modelClipped = false;
        animationId = -1;
        decorDisplacement = 16;
        ambient = 0;
        contrast = 0;
        options = new String[5];
        mapIconId = -1;
        mapSceneId = -1;
        isRotated = false;
        clipped = true;
        modelSizeX = 128;
        modelSizeHeight = 128;
        modelSizeY = 128;
        offsetX = 0;
        offsetHeight = 0;
        offsetY = 0;
        obstructsGround = false;
        hollow = false;
        supportItems = -1;
        varbit = -1;
        varp = -1;
        ambientSoundId = -1;
        anInt455 = 0;
        anInt456 = 0;
        anInt457 = 0;
    }

    public boolean containsOption(final int i, final String option) {
        if (options == null || options[i] == null || options.length <= i) {
            return false;
        }
        return options[i].equals(option);
    }

    public boolean containsOption(final String o) {
        if (options == null) {
            return false;
        }
        for (final String option : options) {
            if (option == null) {
                continue;
            }
            if (option.equalsIgnoreCase(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void decode(final ByteBuffer buffer) {
        while (true) {
            final int opcode = buffer.readUnsignedByte();
            if (opcode == 0) {
                return;
            }
            decode(buffer, opcode);
        }
    }

    @Override
    public void decode(final ByteBuffer buffer, final int opcode) {
        switch (opcode) {
            case 1:
                int size = buffer.readUnsignedByte();
                if (size > 0) {
                    types = new int[size];
                    models = new int[size];
                    for (int index = 0; index < size; index++) {
                        models[index] = buffer.readUnsignedShort();
                        types[index] = buffer.readUnsignedByte();
                    }
                }
                return;
            case 2:
                name = buffer.readString();
                return;
            case 5:
                size = buffer.readUnsignedByte();
                if (size > 0) {
                    types = null;
                    models = new int[size];
                    for (int index = 0; index < size; index++) {
                        models[index] = buffer.readUnsignedShort();
                    }
                    return;
                }
                return;
            case 14:
                sizeX = buffer.readUnsignedByte();
                return;
            case 15:
                sizeY = buffer.readUnsignedByte();
                return;
            case 17:
                clipType = 0;
                projectileClip = false;
                return;
            case 18:
                projectileClip = false;
                return;
            case 19:
                optionsInvisible = buffer.readUnsignedByte();
                return;
            case 21:
                contouredGround = 0;
                return;
            case 22:
                nonFlatShading = true;
                return;
            case 23:
                modelClipped = true;
                return;
            case 24:
                animationId = buffer.readUnsignedShort();
                if (animationId == 65535) {
                    animationId = -1;
                }
                return;
            case 27:
                clipType = 1;
                return;
            case 28:
                decorDisplacement = buffer.readUnsignedByte();
                return;
            case 29:
                ambient = buffer.readByte();
                return;
            case 39:
                contrast = buffer.readByte() * 25;
                return;
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
                options[opcode - 30] = buffer.readString();
                if (options[opcode - 30].equalsIgnoreCase("Hidden")) {
                    options[opcode - 30] = null;
                }
                return;
            case 40:
                size = buffer.readUnsignedByte();
                modelColours = new int[size];
                replacementColours = new int[size];
                for (int count = 0; count < size; count++) {
                    modelColours[count] = (short) buffer.readUnsignedShort();
                    replacementColours[count] = (short) buffer.readUnsignedShort();
                }
                return;
            case 41:
                size = buffer.readUnsignedByte();
                modelTexture = new short[size];
                replacementTexture = new short[size];
                for (int count = 0; count < size; count++) {
                    modelTexture[count] = (short) buffer.readUnsignedShort();
                    replacementTexture[count] = (short) buffer.readUnsignedShort();
                }
                return;
            case 62:
                isRotated = true;
                return;
            case 64:
                clipped = false;
                return;
            case 65:
                modelSizeX = buffer.readUnsignedShort();// useless.
                return;
            case 66:
                modelSizeHeight = buffer.readUnsignedShort();// useless
                return;
            case 67:
                modelSizeY = buffer.readUnsignedShort();// useless
                return;
            case 68:
                mapSceneId = buffer.readUnsignedShort();
                return;
            case 69:
                accessBlockFlag = buffer.readUnsignedByte();
                return;
            case 70:
                offsetX = buffer.readShort();
                return;
            case 71:
                offsetHeight = buffer.readShort();
                return;
            case 72:
                offsetY = buffer.readShort();
                return;
            case 73:
                obstructsGround = true;
                return;
            case 74:
                hollow = true;
                return;
            case 75:
                supportItems = buffer.readUnsignedByte();
                return;
            case 77:
            case 92:
                varbit = buffer.readUnsignedShort();
                if (varbit == 65535) {
                    varbit = -1;
                }
                varp = buffer.readUnsignedShort();
                if (varp == 65535) {
                    varp = -1;
                }
                finalTransformation = -1;
                if (opcode == 92) {
                    finalTransformation = buffer.readUnsignedShort();
                    if (finalTransformation == 65535) {
                        finalTransformation = -1;
                    }
                }
                size = buffer.readUnsignedByte();
                transformedIds = new int[size + 2];
                for (int index = 0; index <= size; index++) {
                    transformedIds[index] = buffer.readUnsignedShort();
                    if (transformedIds[index] == 65535) {
                        transformedIds[index] = -1;
                    }
                }
                transformedIds[size + 1] = finalTransformation;
                return;
            case 78:
                ambientSoundId = buffer.readUnsignedShort();
                anInt455 = buffer.readUnsignedByte();
                return;
            case 79:
                anInt456 = buffer.readUnsignedShort();
                anInt457 = buffer.readUnsignedShort();
                anInt455 = buffer.readUnsignedByte();
                size = buffer.readUnsignedByte();
                anIntArray100 = new int[size];
                for (int count = 0; count < size; count++) {
                    anIntArray100[count] = buffer.readUnsignedShort();
                }
                return;
            case 81:
                contouredGround = buffer.readUnsignedByte() * 256;
                return;
            case 82:
                mapIconId = buffer.readUnsignedShort();
                return;
            case 249:
                parameters = buffer.readParameters();
                return;
            default:
                System.err.println("UNSUPPORTED OBJECT OPCODE: " + opcode);
        }
    }

    public String getOption(final int option) {
        if (options == null || options.length < option || option == 0) {
            return "";
        }
        return options[option - 1];
    }

    @Override
    public ByteBuffer encode() {
        final ByteBuffer buffer = new ByteBuffer(1024);
        if (types != null) {
            buffer.writeByte(1);
            buffer.writeByte(types.length);
            for (int count = 0; count < types.length; count++) {
                buffer.writeShort(models[count]);
                buffer.writeByte(types[count]);
            }
        }
        if (!name.equals("null")) {
            buffer.writeByte(2);
            buffer.writeString(name);
        }
        if (models != null) {
            buffer.writeByte(5);
            buffer.writeByte(models.length);
            for (final int model : models) {
                buffer.writeShort(model);
            }
        }
        if (sizeX != 1) {
            buffer.writeByte(14);
            buffer.writeByte(sizeX);
        }
        if (sizeY != 1) {
            buffer.writeByte(15);
            buffer.writeByte(sizeY);
        }
        if (clipType == 0 && !projectileClip) {
            buffer.writeByte(17);
        }
        if (!projectileClip) {
            buffer.writeByte(18);
        }
        if (optionsInvisible != -1) {
            buffer.writeByte(19);
            buffer.writeByte(optionsInvisible);
        }
        if (contouredGround == 0) {
            buffer.writeByte(21);
        }
        if (nonFlatShading) {
            buffer.writeByte(22);
        }
        if (modelClipped) {
            buffer.writeByte(23);
        }
        if (animationId != -1) {
            buffer.writeByte(24);
            buffer.writeShort(animationId);
        }
        if (clipType == 1) {
            buffer.writeByte(27);
        }
        if (decorDisplacement != 16) {
            buffer.writeByte(28);
            buffer.writeByte(decorDisplacement);
        }
        if (ambient != 0) {
            buffer.writeByte(29);
            buffer.writeByte(ambient);
        }
        for (int index = 0; index < 5; ++index) {
            if (options[index] == null) {
                continue;
            }
            buffer.writeByte((30 + index));
            final String option = options[index];
            buffer.writeString(option);
        }
        if (contrast != 0) {
            buffer.writeByte(39);
            buffer.writeByte((contrast / 25));
        }
        if (modelColours != null && replacementColours != null && modelColours.length != 0 && replacementColours.length != 0) {
            buffer.writeByte(40);
            buffer.writeByte(modelColours.length);
            for (int index = 0; index < modelColours.length; ++index) {
                buffer.writeShort(modelColours[index]);
                buffer.writeShort(replacementColours[index]);
            }
        }
        if (modelTexture != null && replacementTexture != null && modelTexture.length != 0 && replacementTexture.length != 0) {
            buffer.writeByte(41);
            buffer.writeByte(modelTexture.length);
            for (int index = 0; index < modelTexture.length; ++index) {
                buffer.writeShort(modelTexture[index]);
                buffer.writeShort(replacementTexture[index]);
            }
        }
        if (isRotated) {
            buffer.writeByte(62);
        }
        if (!clipped) {
            buffer.writeByte(64);
        }
        if (modelSizeX != 128) {
            buffer.writeByte(65);
            buffer.writeShort(modelSizeX);
        }
        if (modelSizeHeight != 128) {
            buffer.writeByte(66);
            buffer.writeShort(modelSizeHeight);
        }
        if (modelSizeY != 128) {
            buffer.writeByte(67);
            buffer.writeShort(modelSizeY);
        }
        if (mapSceneId != -1) {
            buffer.writeByte(68);
            buffer.writeShort(mapSceneId);
        }
        if (accessBlockFlag != 0) {
            buffer.writeByte(69);
            buffer.writeByte(accessBlockFlag);
        }
        if (offsetX != 0) {
            buffer.writeByte(70);
            buffer.writeShort(offsetX);
        }
        if (offsetHeight != 0) {
            buffer.writeByte(71);
            buffer.writeShort(offsetHeight);
        }
        if (offsetY != 0) {
            buffer.writeByte(72);
            buffer.writeShort(offsetY);
        }
        if (obstructsGround) {
            buffer.writeByte(73);
        }
        if (hollow) {
            buffer.writeByte(74);
        }
        if (supportItems != -1) {
            buffer.writeByte(75);
            buffer.writeByte(supportItems);
        }
        if (ambientSoundId != -1) {
            buffer.writeByte(78);
            buffer.writeShort(ambientSoundId);
            buffer.writeByte(anInt455);
        }
        if (anIntArray100 != null && anIntArray100.length != 0) {
            buffer.writeByte(79);
            buffer.writeShort(anInt456);
            buffer.writeShort(anInt457);
            buffer.writeByte(anInt455);
            buffer.writeByte(anIntArray100.length);
            for (final int value : anIntArray100) {
                buffer.writeShort(value);
            }
        }
        if (contouredGround != -1) {
            buffer.writeByte(81);
            buffer.writeByte(contouredGround / 256);
        }
        if (mapIconId != -1) {
            buffer.writeByte(82);
            buffer.writeShort(mapIconId);
        }
        if (transformedIds != null) {
            buffer.writeByte(77);
            buffer.writeShort(varbit);
            buffer.writeShort(varp);
            buffer.writeByte((transformedIds.length - 2));
            for (int i = 0; i <= transformedIds.length - 2; ++i) {
                buffer.writeShort(transformedIds[i]);
            }
            buffer.writeByte(92);
            buffer.writeShort(varbit);
            buffer.writeShort(varp);
            buffer.writeShort(finalTransformation);
            buffer.writeByte((transformedIds.length - 2));
            for (int i = 0; i <= transformedIds.length - 2; ++i) {
                buffer.writeShort(transformedIds[i]);
            }
        }
        if (parameters != null && !parameters.isEmpty()) {
            buffer.writeByte(249);
            buffer.writeParameters(parameters);
        }
        buffer.writeByte(0);
        return buffer;
    }

    @Override
    public void pack() {
        Game.getCacheMgi().getArchive(ArchiveType.CONFIGS).findGroupByID(GroupType.OBJECT).addFile(new File(id, encode()));
    }

    @Override
    public int getVarbitId() {
        return varbit;
    }

    @Override
    public int getVarpId() {
        return varp;
    }

    @Override
    public int[] getTransmogrifiedIds() {
        return this.transformedIds;
    }

    public void setOption(final int index, final String option) {
        if (options == null) {
            options = new String[5];
        }
        options[index] = option.isEmpty() ? null : option;
    }


    public static class ObjectDefinitionsBuilder {
        private int id;
        private String name;
        private int varbit;
        private int optionsInvisible;
        private int[] models;
        private int[] types;
        private int[] transformedIds;
        private int ambientSoundId;
        private int varp;
        private int supportItems;
        private int[] anIntArray100;
        private int mapIconId;
        private int sizeX;
        private int clipType;
        private boolean isRotated;
        private int sizeY;
        private boolean projectileClip;
        private int anInt455;
        private boolean nonFlatShading;
        private int contouredGround;
        private int anInt456;
        private boolean modelClipped;
        private int ambient;
        private String[] options;
        private int contrast;
        private int anInt457;
        private boolean hollow;
        private int animationId;
        private int modelSizeX;
        private int decorDisplacement;
        private int modelSizeHeight;
        private int modelSizeY;
        private int[] modelColours;
        private boolean clipped;
        private short[] modelTexture;
        private int mapSceneId;
        private int[] replacementColours;
        private int offsetX;
        private short[] replacementTexture;
        private int offsetHeight;
        private int offsetY;
        private boolean obstructsGround;
        private int accessBlockFlag;
        private int finalTransformation;
        private Int2ObjectOpenHashMap<Object> parameters;

        ObjectDefinitionsBuilder() {
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder id(final int id) {
            this.id = id;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder name(final String name) {
            this.name = name;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder varbit(final int varbit) {
            this.varbit = varbit;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder optionsInvisible(final int optionsInvisible) {
            this.optionsInvisible = optionsInvisible;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder models(final int[] models) {
            this.models = models;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder types(final int[] types) {
            this.types = types;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder transformedIds(final int[] transformedIds) {
            this.transformedIds = transformedIds;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder ambientSoundId(final int ambientSoundId) {
            this.ambientSoundId = ambientSoundId;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder varp(final int varp) {
            this.varp = varp;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder supportItems(final int supportItems) {
            this.supportItems = supportItems;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder anIntArray100(final int[] anIntArray100) {
            this.anIntArray100 = anIntArray100;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder mapIconId(final int mapIconId) {
            this.mapIconId = mapIconId;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder sizeX(final int sizeX) {
            this.sizeX = sizeX;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder clipType(final int clipType) {
            this.clipType = clipType;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder isRotated(final boolean isRotated) {
            this.isRotated = isRotated;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder sizeY(final int sizeY) {
            this.sizeY = sizeY;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder projectileClip(final boolean projectileClip) {
            this.projectileClip = projectileClip;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder anInt455(final int anInt455) {
            this.anInt455 = anInt455;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder nonFlatShading(final boolean nonFlatShading) {
            this.nonFlatShading = nonFlatShading;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder contouredGround(final int contouredGround) {
            this.contouredGround = contouredGround;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder anInt456(final int anInt456) {
            this.anInt456 = anInt456;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder modelClipped(final boolean modelClipped) {
            this.modelClipped = modelClipped;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder ambient(final int ambient) {
            this.ambient = ambient;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder options(final String[] options) {
            this.options = options;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder contrast(final int contrast) {
            this.contrast = contrast;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder anInt457(final int anInt457) {
            this.anInt457 = anInt457;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder hollow(final boolean hollow) {
            this.hollow = hollow;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder animationId(final int animationId) {
            this.animationId = animationId;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder modelSizeX(final int modelSizeX) {
            this.modelSizeX = modelSizeX;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder decorDisplacement(final int decorDisplacement) {
            this.decorDisplacement = decorDisplacement;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder modelSizeHeight(final int modelSizeHeight) {
            this.modelSizeHeight = modelSizeHeight;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder modelSizeY(final int modelSizeY) {
            this.modelSizeY = modelSizeY;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder modelColours(final int[] modelColours) {
            this.modelColours = modelColours;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder clipped(final boolean clipped) {
            this.clipped = clipped;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder modelTexture(final short[] modelTexture) {
            this.modelTexture = modelTexture;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder mapSceneId(final int mapSceneId) {
            this.mapSceneId = mapSceneId;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder replacementColours(final int[] replacementColours) {
            this.replacementColours = replacementColours;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder offsetX(final int offsetX) {
            this.offsetX = offsetX;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder replacementTexture(final short[] replacementTexture) {
            this.replacementTexture = replacementTexture;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder offsetHeight(final int offsetHeight) {
            this.offsetHeight = offsetHeight;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder offsetY(final int offsetY) {
            this.offsetY = offsetY;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder obstructsGround(final boolean obstructsGround) {
            this.obstructsGround = obstructsGround;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder accessBlockFlag(final int accessBlockFlag) {
            this.accessBlockFlag = accessBlockFlag;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder finalTransformation(final int finalTransformation) {
            this.finalTransformation = finalTransformation;
            return this;
        }

        @NotNull
        public ObjectDefinitions.ObjectDefinitionsBuilder parameters(final Int2ObjectOpenHashMap<Object> parameters) {
            this.parameters = parameters;
            return this;
        }

        @NotNull
        public ObjectDefinitions build() {
            return new ObjectDefinitions(this.id, this.name, this.varbit, this.optionsInvisible, this.models, this.types, this.transformedIds, this.ambientSoundId, this.varp, this.supportItems, this.anIntArray100, this.mapIconId, this.sizeX, this.clipType, this.isRotated, this.sizeY, this.projectileClip, this.anInt455, this.nonFlatShading, this.contouredGround, this.anInt456, this.modelClipped, this.ambient, this.options, this.contrast, this.anInt457, this.hollow, this.animationId, this.modelSizeX, this.decorDisplacement, this.modelSizeHeight, this.modelSizeY, this.modelColours, this.clipped, this.modelTexture, this.mapSceneId, this.replacementColours, this.offsetX, this.replacementTexture, this.offsetHeight, this.offsetY, this.obstructsGround, this.accessBlockFlag, this.finalTransformation, this.parameters);
        }

        @NotNull
        @Override
        public String toString() {
            return "ObjectDefinitions.ObjectDefinitionsBuilder(id=" + this.id + ", name=" + this.name + ", varbit=" + this.varbit + ", optionsInvisible=" + this.optionsInvisible + ", models=" + Arrays.toString(this.models) + ", types=" + Arrays.toString(this.types) + ", transformedIds=" + Arrays.toString(this.transformedIds) + ", ambientSoundId=" + this.ambientSoundId + ", varp=" + this.varp + ", supportItems=" + this.supportItems + ", anIntArray100=" + Arrays.toString(this.anIntArray100) + ", mapIconId=" + this.mapIconId + ", sizeX=" + this.sizeX + ", clipType=" + this.clipType + ", isRotated=" + this.isRotated + ", sizeY=" + this.sizeY + ", projectileClip=" + this.projectileClip + ", anInt455=" + this.anInt455 + ", nonFlatShading=" + this.nonFlatShading + ", contouredGround=" + this.contouredGround + ", anInt456=" + this.anInt456 + ", modelClipped=" + this.modelClipped + ", ambient=" + this.ambient + ", options=" + Arrays.deepToString(this.options) + ", contrast=" + this.contrast + ", anInt457=" + this.anInt457 + ", hollow=" + this.hollow + ", animationId=" + this.animationId + ", modelSizeX=" + this.modelSizeX + ", decorDisplacement=" + this.decorDisplacement + ", modelSizeHeight=" + this.modelSizeHeight + ", modelSizeY=" + this.modelSizeY + ", modelColours=" + Arrays.toString(this.modelColours) + ", clipped=" + this.clipped + ", modelTexture=" + Arrays.toString(this.modelTexture) + ", mapSceneId=" + this.mapSceneId + ", replacementColours=" + Arrays.toString(this.replacementColours) + ", offsetX=" + this.offsetX + ", replacementTexture=" + Arrays.toString(this.replacementTexture) + ", offsetHeight=" + this.offsetHeight + ", offsetY=" + this.offsetY + ", obstructsGround=" + this.obstructsGround + ", accessBlockFlag=" + this.accessBlockFlag + ", finalTransformation=" + this.finalTransformation + ", parameters=" + this.parameters + ")";
        }
    }

    @NotNull
    public static ObjectDefinitions.ObjectDefinitionsBuilder builder() {
        return new ObjectDefinitions.ObjectDefinitionsBuilder();
    }

    @NotNull
    public ObjectDefinitions.ObjectDefinitionsBuilder toBuilder() {
        return new ObjectDefinitions.ObjectDefinitionsBuilder().id(this.id).name(this.name).varbit(this.varbit).optionsInvisible(this.optionsInvisible).models(this.models).types(this.types).transformedIds(this.transformedIds).ambientSoundId(this.ambientSoundId).varp(this.varp).supportItems(this.supportItems).anIntArray100(this.anIntArray100).mapIconId(this.mapIconId).sizeX(this.sizeX).clipType(this.clipType).isRotated(this.isRotated).sizeY(this.sizeY).projectileClip(this.projectileClip).anInt455(this.anInt455).nonFlatShading(this.nonFlatShading).contouredGround(this.contouredGround).anInt456(this.anInt456).modelClipped(this.modelClipped).ambient(this.ambient).options(this.options).contrast(this.contrast).anInt457(this.anInt457).hollow(this.hollow).animationId(this.animationId).modelSizeX(this.modelSizeX).decorDisplacement(this.decorDisplacement).modelSizeHeight(this.modelSizeHeight).modelSizeY(this.modelSizeY).modelColours(this.modelColours).clipped(this.clipped).modelTexture(this.modelTexture).mapSceneId(this.mapSceneId).replacementColours(this.replacementColours).offsetX(this.offsetX).replacementTexture(this.replacementTexture).offsetHeight(this.offsetHeight).offsetY(this.offsetY).obstructsGround(this.obstructsGround).accessBlockFlag(this.accessBlockFlag).finalTransformation(this.finalTransformation).parameters(this.parameters);
    }

    @NotNull
    @Override
    public String toString() {
        return "ObjectDefinitions(id=" + this.getId() + ", name=" + this.getName() + ", varbit=" + this.getVarbit() + ", optionsInvisible=" + this.getOptionsInvisible() + ", models=" + Arrays.toString(this.getModels()) + ", types=" + Arrays.toString(this.getTypes()) + ", transformedIds=" + Arrays.toString(this.getTransformedIds()) + ", ambientSoundId=" + this.getAmbientSoundId() + ", varp=" + this.getVarp() + ", supportItems=" + this.getSupportItems() + ", anIntArray100=" + Arrays.toString(this.getAnIntArray100()) + ", mapIconId=" + this.getMapIconId() + ", sizeX=" + this.getSizeX() + ", clipType=" + this.getClipType() + ", isRotated=" + this.isRotated() + ", sizeY=" + this.getSizeY() + ", projectileClip=" + this.isProjectileClip() + ", anInt455=" + this.getAnInt455() + ", nonFlatShading=" + this.isNonFlatShading() + ", contouredGround=" + this.getContouredGround() + ", anInt456=" + this.getAnInt456() + ", modelClipped=" + this.isModelClipped() + ", ambient=" + this.getAmbient() + ", options=" + Arrays.deepToString(this.getOptions()) + ", contrast=" + this.getContrast() + ", anInt457=" + this.getAnInt457() + ", hollow=" + this.isHollow() + ", animationId=" + this.getAnimationId() + ", modelSizeX=" + this.getModelSizeX() + ", decorDisplacement=" + this.getDecorDisplacement() + ", modelSizeHeight=" + this.getModelSizeHeight() + ", modelSizeY=" + this.getModelSizeY() + ", modelColours=" + Arrays.toString(this.getModelColours()) + ", clipped=" + this.isClipped() + ", modelTexture=" + Arrays.toString(this.getModelTexture()) + ", mapSceneId=" + this.getMapSceneId() + ", replacementColours=" + Arrays.toString(this.getReplacementColours()) + ", offsetX=" + this.getOffsetX() + ", replacementTexture=" + Arrays.toString(this.getReplacementTexture()) + ", offsetHeight=" + this.getOffsetHeight() + ", offsetY=" + this.getOffsetY() + ", obstructsGround=" + this.isObstructsGround() + ", accessBlockFlag=" + this.getAccessBlockFlag() + ", finalTransformation=" + this.getFinalTransformation() + ", parameters=" + this.getParameters() + ")";
    }

    public ObjectDefinitions(final int id, final String name, final int varbit, final int optionsInvisible, final int[] models, final int[] types, final int[] transformedIds, final int ambientSoundId, final int varp, final int supportItems, final int[] anIntArray100, final int mapIconId, final int sizeX, final int clipType, final boolean isRotated, final int sizeY, final boolean projectileClip, final int anInt455, final boolean nonFlatShading, final int contouredGround, final int anInt456, final boolean modelClipped, final int ambient, final String[] options, final int contrast, final int anInt457, final boolean hollow, final int animationId, final int modelSizeX, final int decorDisplacement, final int modelSizeHeight, final int modelSizeY, final int[] modelColours, final boolean clipped, final short[] modelTexture, final int mapSceneId, final int[] replacementColours, final int offsetX, final short[] replacementTexture, final int offsetHeight, final int offsetY, final boolean obstructsGround, final int accessBlockFlag, final int finalTransformation, final Int2ObjectOpenHashMap<Object> parameters) {
        this.id = id;
        this.name = name;
        this.varbit = varbit;
        this.optionsInvisible = optionsInvisible;
        this.models = models;
        this.types = types;
        this.transformedIds = transformedIds;
        this.ambientSoundId = ambientSoundId;
        this.varp = varp;
        this.supportItems = supportItems;
        this.anIntArray100 = anIntArray100;
        this.mapIconId = mapIconId;
        this.sizeX = sizeX;
        this.clipType = clipType;
        this.isRotated = isRotated;
        this.sizeY = sizeY;
        this.projectileClip = projectileClip;
        this.anInt455 = anInt455;
        this.nonFlatShading = nonFlatShading;
        this.contouredGround = contouredGround;
        this.anInt456 = anInt456;
        this.modelClipped = modelClipped;
        this.ambient = ambient;
        this.options = options;
        this.contrast = contrast;
        this.anInt457 = anInt457;
        this.hollow = hollow;
        this.animationId = animationId;
        this.modelSizeX = modelSizeX;
        this.decorDisplacement = decorDisplacement;
        this.modelSizeHeight = modelSizeHeight;
        this.modelSizeY = modelSizeY;
        this.modelColours = modelColours;
        this.clipped = clipped;
        this.modelTexture = modelTexture;
        this.mapSceneId = mapSceneId;
        this.replacementColours = replacementColours;
        this.offsetX = offsetX;
        this.replacementTexture = replacementTexture;
        this.offsetHeight = offsetHeight;
        this.offsetY = offsetY;
        this.obstructsGround = obstructsGround;
        this.accessBlockFlag = accessBlockFlag;
        this.finalTransformation = finalTransformation;
        this.parameters = parameters;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getVarbit() {
        return this.varbit;
    }

    public void setVarbit(final int varbit) {
        this.varbit = varbit;
    }

    public int getOptionsInvisible() {
        return this.optionsInvisible;
    }

    public void setOptionsInvisible(final int optionsInvisible) {
        this.optionsInvisible = optionsInvisible;
    }

    public int[] getModels() {
        return this.models;
    }

    public void setModels(final int[] models) {
        this.models = models;
    }

    public int[] getTypes() {
        return this.types;
    }

    public void setTypes(final int[] types) {
        this.types = types;
    }

    public int[] getTransformedIds() {
        return this.transformedIds;
    }

    public void setTransformedIds(final int[] transformedIds) {
        this.transformedIds = transformedIds;
    }

    public int getAmbientSoundId() {
        return this.ambientSoundId;
    }

    public void setAmbientSoundId(final int ambientSoundId) {
        this.ambientSoundId = ambientSoundId;
    }

    public int getVarp() {
        return this.varp;
    }

    public int getSupportItems() {
        return this.supportItems;
    }

    public int[] getAnIntArray100() {
        return this.anIntArray100;
    }

    public int getMapIconId() {
        return this.mapIconId;
    }

    public int getSizeX() {
        return this.sizeX;
    }

    public void setSizeX(final int sizeX) {
        this.sizeX = sizeX;
    }

    public int getClipType() {
        return this.clipType;
    }

    public void setClipType(final int clipType) {
        this.clipType = clipType;
    }

    public boolean isRotated() {
        return this.isRotated;
    }

    public int getSizeY() {
        return this.sizeY;
    }

    public void setSizeY(final int sizeY) {
        this.sizeY = sizeY;
    }

    public boolean isProjectileClip() {
        return this.projectileClip;
    }

    public void setProjectileClip(final boolean projectileClip) {
        this.projectileClip = projectileClip;
    }

    public int getAnInt455() {
        return this.anInt455;
    }

    public void setAnInt455(final int anInt455) {
        this.anInt455 = anInt455;
    }

    public boolean isNonFlatShading() {
        return this.nonFlatShading;
    }

    public int getContouredGround() {
        return this.contouredGround;
    }

    public void setContouredGround(final int contouredGround) {
        this.contouredGround = contouredGround;
    }

    public int getAnInt456() {
        return this.anInt456;
    }

    public boolean isModelClipped() {
        return this.modelClipped;
    }

    public int getAmbient() {
        return this.ambient;
    }

    public String[] getOptions() {
        return this.options;
    }

    public void setOptions(final String[] options) {
        this.options = options;
    }

    public int getContrast() {
        return this.contrast;
    }

    public void setContrast(final int contrast) {
        this.contrast = contrast;
    }

    public int getAnInt457() {
        return this.anInt457;
    }

    public boolean isHollow() {
        return this.hollow;
    }

    public int getAnimationId() {
        return this.animationId;
    }

    public void setAnimationId(final int animationId) {
        this.animationId = animationId;
    }

    public int getModelSizeX() {
        return this.modelSizeX;
    }

    public void setModelSizeX(final int modelSizeX) {
        this.modelSizeX = modelSizeX;
    }

    public int getDecorDisplacement() {
        return this.decorDisplacement;
    }

    public int getModelSizeHeight() {
        return this.modelSizeHeight;
    }

    public void setModelSizeHeight(final int modelSizeHeight) {
        this.modelSizeHeight = modelSizeHeight;
    }

    public int getModelSizeY() {
        return this.modelSizeY;
    }

    public void setModelSizeY(final int modelSizeY) {
        this.modelSizeY = modelSizeY;
    }

    public int[] getModelColours() {
        return this.modelColours;
    }

    public void setModelColours(final int[] modelColours) {
        this.modelColours = modelColours;
    }

    public boolean isClipped() {
        return this.clipped;
    }

    public short[] getModelTexture() {
        return this.modelTexture;
    }

    public int getMapSceneId() {
        return this.mapSceneId;
    }

    public int[] getReplacementColours() {
        return this.replacementColours;
    }

    public void setReplacementColours(final int[] replacementColours) {
        this.replacementColours = replacementColours;
    }

    public int getOffsetX() {
        return this.offsetX;
    }

    public void setOffsetX(final int offsetX) {
        this.offsetX = offsetX;
    }

    public short[] getReplacementTexture() {
        return this.replacementTexture;
    }

    public int getOffsetHeight() {
        return this.offsetHeight;
    }

    public void setOffsetHeight(final int offsetHeight) {
        this.offsetHeight = offsetHeight;
    }

    public int getOffsetY() {
        return this.offsetY;
    }

    public void setOffsetY(final int offsetY) {
        this.offsetY = offsetY;
    }

    public boolean isObstructsGround() {
        return this.obstructsGround;
    }

    public int getAccessBlockFlag() {
        return this.accessBlockFlag;
    }

    public void setAccessBlockFlag(final int accessBlockFlag) {
        this.accessBlockFlag = accessBlockFlag;
    }

    public int getFinalTransformation() {
        return this.finalTransformation;
    }

    public void setFinalTransformation(final int finalTransformation) {
        this.finalTransformation = finalTransformation;
    }

    public Int2ObjectOpenHashMap<Object> getParameters() {
        return this.parameters;
    }

    public void setParameters(final Int2ObjectOpenHashMap<Object> parameters) {
        this.parameters = parameters;
    }
}
