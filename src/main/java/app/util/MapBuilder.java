package app.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.HashMap;
import java.util.Map;

public class MapBuilder {

    public static HashMap<String,String> buildStringMap(String... data) {
        return buildStringMap(false, data);
    }
    public static HashMap<String,String> buildStringMapSkipNulls(String... data) {
        return buildStringMap(true, data);
    }

    public static HashMap<String,String> buildStringMap(boolean skipNulls, String... data) {
        HashMap<String,String> map = new HashMap<>();
        populate(map, skipNulls, (Object[]) data);
        return map;
    }

    public static <K,V> HashMap<K, V> build(Object... data) {
        return build(false, data);
    }
    public static <K,V> HashMap<K, V> buildSkipNulls(Object... data) {
        return build(true, data);
    }

    public static <K,V> HashMap<K, V> build(boolean skipNulls, Object... data) {
        HashMap<K, V> map = new HashMap<>();
        populate(map, skipNulls, data);
        return map;
    }

    public static <K,V> BiMap<K, V> buildBi(Object... data) {
        HashBiMap<K, V> map = HashBiMap.create();
        populate(map, false, data);
        return map;
    }

    @SuppressWarnings("unchecked")
    private static <K,V> void populate(Map<K,V> map, boolean skipNulls, Object... data) {
        if(data.length % 2 != 0) throw new IllegalArgumentException("Odd number of arguments: "+data.length);

        K key = null;
        int step = -1;

        for(Object t : data){
            step++;
            if(step % 2 == 0) {
                if(t == null) throw new IllegalArgumentException("Null key value");
                key = (K) t;
                continue;
            }

            if(!skipNulls || t != null) map.put(key, (V) t);
        }
    }
}
