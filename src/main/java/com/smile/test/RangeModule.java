package com.smile.test;

import java.util.TreeMap;

/**
 * @author smile
 */
public class RangeModule {
    /**
     * k:left
     * v:right
     */
    TreeMap<Integer, Integer> ranges = new TreeMap<>();

    public void addRange(int left, int right) {
        if (left >= right) {
            return;
        }

        Integer first = ranges.floorKey(left);
        Integer second = ranges.floorKey(right);

        // ranges中存在[k,v) 与 [left,right) 左侧交叉
        if (first != null && ranges.get(first) >= left) {
            left = first;
        }
        // ranges中存在[k,v) 与 [left,right) 右侧交叉
        if (second != null && ranges.get(second) > right) {
            right = ranges.get(second);
        }
        ranges.put(left, right);

        // 清除(left,right]中的无用重复数据
        ranges.subMap(left, false, right, true).clear();
    }

    public void removeRange(int left, int right) {
        if (left >= right) {
            return;
        }

        Integer first = ranges.floorKey(left);
        Integer second = ranges.floorKey(right);

        // first与second顺序不能颠倒，防止first==second，出现错误
        if (second != null && ranges.get(second) > right) {
            ranges.put(right, ranges.get(second));
        }
        if (first != null && ranges.get(first) > left) {
            ranges.put(first, left);
        }
        ranges.subMap(left, true, right, false).clear();
    }

    public boolean queryRange(int left, int right) {
        Integer first = ranges.floorKey(left);
        return first != null && ranges.get(first) >= right;
    }
}
