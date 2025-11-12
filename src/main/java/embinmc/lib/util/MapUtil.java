package embinmc.lib.util;

import java.util.Map;

@SuppressWarnings({"unused"})
public final class MapUtil {

    public static <Key, Value> Value getOrDefault(Map<Key, Value> map, Key key, Value defaultValue) {
        for (Map.Entry<Key, Value> entry : map.entrySet()) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return defaultValue;
    }

    public static <Key, Value> boolean hasKeyIdMap(Map<Key, Value> map, Key key) {
        for (Key key2 : map.keySet()) {
            if (key2.equals(key)) return true;
        }
        return false;
    }

    public static <Key, Value> void removeFromIdMap(Map<Key, Value> map, Key key) {
        for (Key key2 : map.keySet()) {
            if (key2.equals(key)) {
                map.remove(key2);
            }
        }
    }
}
