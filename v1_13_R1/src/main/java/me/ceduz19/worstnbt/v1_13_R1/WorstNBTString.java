package me.ceduz19.worstnbt.v1_13_R1;

import me.ceduz19.worstnbt.NBTString;
import net.minecraft.server.v1_13_R1.NBTTagString;
import org.jetbrains.annotations.NotNull;

class WorstNBTString implements NBTString {

    private final NBTTagString handle;

    WorstNBTString(@NotNull String s) {
        this(new NBTTagString(s));
    }

    WorstNBTString(NBTTagString handle) {
        this.handle = handle;
    }

    @Override
    public @NotNull String getAsString() {
        return this.handle.b_();
    }

    @Override
    public boolean isEmpty() {
        return getAsString().isEmpty();
    }

    @Override
    public @NotNull Object getHandle() {
        return this.handle;
    }

    public int hashCode() {
        return this.handle.hashCode();
    }

    public boolean equals(Object obj) {
        return obj == this || this.handle.equals(obj instanceof WorstNBTString ? ((WorstNBTString) obj).handle : obj);
    }

    public String toString() {
        return this.handle.toString();
    }
}
