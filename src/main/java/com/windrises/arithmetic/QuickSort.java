package com.windrises.arithmetic;

import java.lang.reflect.Field;

/**
 * @author liuhaozhen
 * @version Revision 1.0.0
 * @date 2019/12/9 9:49
 */
public class QuickSort {
    /**
     * 选择一个哨兵节点，将所有小于哨兵节点的元素放到左边，大于哨兵节点的元素放到右边
     * 最后将哨兵节点和小部分中的最右边那个值交换（j），i就处于大部分的最左边
     *
     * @param array 数组元素
     * @param low
     * @param high
     */
    public static void quickSort(Integer[] array, int low, int high) {
        if (low >= high) {
            return;
        }
        int index = divide(array, low, high);
        quickSort(array, low, index - 1);
        quickSort(array, index + 1, high);
    }

    private static int divide(Integer[] array, int low, int high) {
        int i = low + 1, j = high;
        while (i <= j) {
            while (i <= j && array[i] <= array[low]) {
                i++;
            }
            while (i <= j && array[j] > array[low]) {
                j--;
            }
            if (i <= j) {
                int k = array[j];
                array[j] = array[i];
                array[i] = k;
            }
        }
        int t = array[low];
        array[low] = array[j];
        array[j] = t;
        return j;
    }
}
