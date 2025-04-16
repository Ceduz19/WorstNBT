package me.ceduz19.worstnbt.core.util;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;

public final class ArrayUtils {

    private static final int MAX_LENGTH = 0x7FFFFFF7;

    private ArrayUtils() {
        throw new IllegalStateException();
    }

    @NotNull
    public static <T1, T2> @NotNull T1 @NotNull [] toArray(@NotNull T1 @NotNull [] to, int originalSize, @NotNull Iterator<T2> iterator) {
        int length;
        T1[] newArray = to.length >= originalSize ? to : (T1[]) Array.newInstance(to.getClass().getComponentType(), originalSize);

        for (int i = 0; i < newArray.length; ++i) {
            if (!iterator.hasNext()) {
                if (newArray != to) {
                    System.arraycopy(to, i, newArray, i, to.length - i);
                }
                return newArray;
            }
            newArray[i] = iterator.next();
        }

        int i = length = newArray.length;
        while (iterator.hasNext()) {
            if (i == length) {
                length = ArrayUtils.increaseLength(length, 1, (length >> 1) + 1);
                newArray = Arrays.copyOf(newArray, length);
            }
            newArray[i++] = iterator.next();
        }

        return newArray;
    }

    private static int increaseLength(int old, int min, int pref) {
        int length = old + Math.max(min, pref);
        if (length > 0 && length <= MAX_LENGTH) return length;

        length = old + min;
        if (length < 0) throw new OutOfMemoryError();

        return Math.max(length, MAX_LENGTH);
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    public static Object arrayAdd(Object array, int index, Object element) {
        if (!array.getClass().isArray()) throw new IllegalArgumentException("object is not an array");
        if (index < 0) throw new IndexOutOfBoundsException("index < 0");

        int length = Array.getLength(array);
        if (index > length) throw new IndexOutOfBoundsException("index: " + index + ", length: " + length);

        Object newArray = Array.newInstance(array.getClass().getComponentType(), length + 1);
        System.arraycopy(array, 0, newArray, 0, length);
        Array.set(newArray, index, element);
        if (index < length) System.arraycopy(array, index, newArray, index + 1, length - index);

        return newArray;
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    private static Object _set(Object array, int index, Object element) {
        if (!array.getClass().isArray()) throw new IllegalArgumentException("object is not an array");
        if (index < 0) throw new IndexOutOfBoundsException("index less than 0");

        int length = Array.getLength(array);
        if (index > length) throw new IndexOutOfBoundsException("index: " + index + ", length: " + length);

        Object newArray = Array.newInstance(array.getClass().getComponentType(), length);
        System.arraycopy(array, 0, newArray, 0, length);
        Array.set(newArray, index, element);
        return newArray;
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    public static Object arrayRemove(Object array, int index) {
        if (!array.getClass().isArray()) throw new IllegalArgumentException("object is not an array");
        if (index < 0) throw new IndexOutOfBoundsException("index less than 0");

        int length = Array.getLength(array);
        if (index > length) throw new IndexOutOfBoundsException("index: " + index + ", length: " + length);

        Object newArray = Array.newInstance(array.getClass().getComponentType(), length - 1);
        System.arraycopy(array, 0, newArray, 0, index);
        if (index < length - 1) System.arraycopy(array, index + 1, newArray, index, length - index - 1);

        return newArray;
    }
}
