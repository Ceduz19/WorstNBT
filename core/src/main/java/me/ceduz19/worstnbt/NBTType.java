package me.ceduz19.worstnbt;

import org.jetbrains.annotations.Nullable;

public enum NBTType {
    END         (0),
    BYTE        (1),
    SHORT       (2),
    INT         (3),
    LONG        (4),
    FLOAT       (5),
    DOUBLE      (6),
    BYTE_ARRAY  (7),
    STRING      (8),
    LIST        (9),
    COMPOUND    (10),
    INT_ARRAY   (11),
    LONG_ARRAY  (12),
    ANY_NUMERIC (99);

    private final byte id;

    NBTType(int id) {
        this.id = (byte)id;
    }

    public byte asId() {
        return this.id;
    }

    @Nullable
    public static NBTType byId(byte id) {
        for (NBTType t : NBTType.values())
            if (t.id == id) return t;

        return null;
    }
}
