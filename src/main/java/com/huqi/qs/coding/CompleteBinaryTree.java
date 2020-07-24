package com.huqi.qs.coding;

import java.util.function.Function;

public class CompleteBinaryTree implements Function<int[], int[]> {
    private int[] newArray;
    private int[] oldArray;

    @Override
    public int[] apply(int[] input) {
        oldArray = input;
        newArray = new int[input.length];

        bfsArray(0, 0);

        return newArray;
    }

    public int bfsArray(int newIndex, int oldIndex) {
        if (newIndex * 2 + 1 < oldArray.length) {
            oldIndex = bfsArray(newIndex * 2 + 1, oldIndex);
        }

        newArray[newIndex] = oldArray[oldIndex];
        oldIndex++;

        if (newIndex * 2 + 2 < oldArray.length) {
            oldIndex = bfsArray(newIndex * 2 + 2, oldIndex);
        }

        return oldIndex;
    }
}