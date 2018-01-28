package cn.springcloud.codegen.engine.tools;

import java.util.*;

/**
 * @author Vincent.
 * @createdOn 2018/01/27 21:33
 */
public class MapTools {

    public static <K, V> Map<K, V> addMap(Map<K, V> originMap, Map<K, V> addendMap) {
        if (originMap == null){
            originMap = new HashMap<K, V>();
        }
        if (addendMap == null){
            return originMap;
        }
        for (Map.Entry<K, V> entry : addendMap.entrySet()) {
            if (originMap.get(entry.getKey()) == null) {
                if (entry.getValue() instanceof List) {
                    originMap.put(entry.getKey(), entry.getValue());
                } else {
                    List<Object> list = new ArrayList<Object>();
                    list.add(entry.getValue());
                    originMap.put(entry.getKey(), (V)list);
                }
            } else if (originMap.get(entry.getKey()) instanceof List) {
                if (entry.getValue() instanceof List) {
                    ((List) originMap.get(entry.getKey())).addAll((Collection) entry.getValue());
                } else {
                    ((List) originMap.get(entry.getKey())).add(entry.getValue());
                }
            } else {
                if (entry.getValue() instanceof List) {
                    ((List) entry.getValue()).add(originMap.get(entry.getKey()));
                    originMap.put(entry.getKey(), entry.getValue());
                } else {
                    List<Object> list = new ArrayList<Object>();
                    list.add(originMap.get(entry.getKey()));
                    list.add(entry.getValue());
                    originMap.put(entry.getKey(), (V)list);
                }
            }
        }
        return originMap;
    }
}
