package me.ceduz19.worstnbt.v1_12_R1;

import me.ceduz19.worstnbt.NBTEnd;
import me.ceduz19.worstnbt.NBTType;
import me.ceduz19.worstnbt.util.ReflectionUtils;
import net.minecraft.server.v1_12_R1.NBTBase;
import net.minecraft.server.v1_12_R1.NBTTagEnd;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

class WorstNBTEnd implements NBTEnd {

    public static final WorstNBTEnd INSTANCE = new WorstNBTEnd();
    private static final NBTTagEnd HANDLE;

    private WorstNBTEnd() {}

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public @NotNull Object getHandle() {
        if (HANDLE == null)
            throw new IllegalStateException("unable to get handle: could not create an instance of NBTTagEnd");
        return HANDLE;
    }

    public int hashCode() {
        return NBTType.END.getId();
    }

    public boolean equals(Object obj) {
        return obj instanceof WorstNBTEnd || obj instanceof NBTTagEnd;
    }

    public String toString() {
        return "END";
    }

    static {
        Method m = ReflectionUtils.getMethod(NBTBase.class, "createTag", true, byte.class);
        HANDLE = m == null ? null : (NBTTagEnd) ReflectionUtils.invokeMethod(m, null, 0);
    }
}
