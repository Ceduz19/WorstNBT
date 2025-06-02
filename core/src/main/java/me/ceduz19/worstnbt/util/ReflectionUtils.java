package me.ceduz19.worstnbt.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public final class ReflectionUtils {

    private ReflectionUtils() {
        throw new IllegalStateException();
    }

    public static @Nullable Class<?> getClass(@NotNull String name) {
        try {
            return Class.forName(name);
        } catch (Exception e) {
            return null;
        }
    }

    public static @Nullable Constructor<?> getConstructor(@NotNull Class<?> clazz, boolean declared, Class<?> ... argTypes) {
        try {
            Constructor<?> c = declared ? clazz.getDeclaredConstructor(argTypes) : clazz.getConstructor(argTypes);
            if (!c.isAccessible()) c.setAccessible(true);
            return c;
        } catch (Exception e) {
            return null;
        }
    }

    public static @Nullable Object invokeConstructor(@NotNull Constructor<?> constructor, Object ... args) {
        try {
            return constructor.newInstance(args);
        } catch (Exception e) {
            return null;
        }
    }

    public static @Nullable Field getField(@NotNull Class<?> clazz, @NotNull String name, boolean declared) {
        try {
            Field f = declared ? clazz.getDeclaredField(name) : clazz.getField(name);
            if (!f.isAccessible()) f.setAccessible(true);
            return f;
        }
        catch (Exception e) {
            return null;
        }
    }

    public static @Nullable Object readField(@NotNull Field field, @Nullable Object instance) {
        try {
            return field.get(instance);
        } catch (Exception e) {
            return null;
        }
    }

    public static void writeField(@NotNull Field field, @Nullable Object instance, @Nullable Object value) {
        try {
            field.set(instance, value);
        } catch (Exception ignored) {}
    }

    public static @Nullable Method getMethod(@NotNull Class<?> clazz, @NotNull String name, boolean declared, Class<?> ... argTypes) {
        try {
            Method m = declared ? clazz.getDeclaredMethod(name, argTypes) : clazz.getMethod(name, argTypes);
            if (!m.isAccessible()) m.setAccessible(true);
            return m;
        } catch (Exception e) {
            return null;
        }
    }

    public static @Nullable Object invokeMethod(@NotNull Method method, @Nullable Object instance, Object ... args) {
        try {
            return method.invoke(instance, args);
        } catch (Exception e) {
            return null;
        }
    }
}
