package me.ceduz19.worstnbt.v1_21_R3;

import me.ceduz19.worstnbt.core.NBTString;
import net.minecraft.nbt.StringTag;
import org.jetbrains.annotations.NotNull;

class WorstNBTString implements NBTString {

    private final StringTag handle;

    WorstNBTString(String s) {
        this(StringTag.valueOf(s));
    }

    WorstNBTString(StringTag handle) {
        this.handle = handle;
    }

    @Override
    public @NotNull String getAsString() {
        return this.handle.getAsString();
    }

    @Override
    public boolean isEmpty() {
        return this.handle.getAsString().isEmpty();
    }

    @Override
    public @NotNull Object getHandle() {
        return this.handle;
    }

    public int hashCode() {
        return this.handle.hashCode();
    }

    public boolean equals(Object obj) {
        return obj == this || this.handle.equals(obj instanceof WorstNBTString w ? w.handle : obj);
    }

    public String toString() {
        return this.handle.toString();
    }
}
