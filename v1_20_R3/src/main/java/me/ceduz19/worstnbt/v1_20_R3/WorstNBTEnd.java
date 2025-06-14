package me.ceduz19.worstnbt.v1_20_R3;

import me.ceduz19.worstnbt.NBTEnd;
import net.minecraft.nbt.EndTag;
import org.jetbrains.annotations.NotNull;

class WorstNBTEnd implements NBTEnd {

    public static final WorstNBTEnd INSTANCE = new WorstNBTEnd();
    private static final EndTag HANDLE = EndTag.INSTANCE;

    private WorstNBTEnd() {}

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public @NotNull Object getHandle() {
        return HANDLE;
    }

    public int hashCode() {
        return HANDLE.hashCode();
    }

    public boolean equals(Object obj) {
        return obj instanceof WorstNBTEnd || obj instanceof EndTag;
    }

    public String toString() {
        return HANDLE.toString();
    }
}
