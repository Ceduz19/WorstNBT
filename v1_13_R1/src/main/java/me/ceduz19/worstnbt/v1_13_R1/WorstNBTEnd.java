package me.ceduz19.worstnbt.v1_13_R1;

import me.ceduz19.worstnbt.NBTEnd;
import net.minecraft.server.v1_13_R1.NBTTagEnd;
import org.jetbrains.annotations.NotNull;

class WorstNBTEnd implements NBTEnd {

    public static final WorstNBTEnd INSTANCE = new WorstNBTEnd();
    private final NBTTagEnd handle = new NBTTagEnd();

    private WorstNBTEnd() {}

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public @NotNull Object getHandle() {
        return handle;
    }

    public int hashCode() {
        return handle.hashCode();
    }

    public boolean equals(Object obj) {
        return obj instanceof WorstNBTEnd || obj instanceof NBTTagEnd;
    }

    public String toString() {
        return handle.toString();
    }

}
