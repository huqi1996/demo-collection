package com.huqi.qs.javase.util;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author huqi 20190325
 */
public class ListUtils {
    public static <E> List<E> removeAllWithoutRepeat(List<E> collection, List<?> remove) {
        List<E> list = new ArrayList<>();
        List<Integer> indexes = new ArrayList<>();
        Iterator iterator = collection.iterator();
        boolean flag;

        while (iterator.hasNext()) {
            flag = true;
            E obj = (E) iterator.next();
            for (int i = 0; i < remove.size(); i++) {
                if (remove.get(i).equals(obj) && !indexes.contains(i)) {
                    indexes.add(i);
                    flag = false;
                    break;
                }
            }
            if (flag) {
                list.add(obj);
            }
        }
        return list;
    }
}
