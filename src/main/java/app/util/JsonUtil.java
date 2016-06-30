package app.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUtil {
    private static final ObjectMapper mapper;

    static {
        mapper = new ObjectMapper(new JsonFactory());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }

    public static ObjectMapper getDefaultMapper() {
        return mapper;
    }

    public static <T> T parseString(String str, Class<T> clazz, boolean safe) {
        if(str == null) str = "{}";

        try {
            return mapper.readValue(str, clazz);
        } catch(Throwable e) {
            if(safe) {
                log.warn("", e); return null;
            }
            throw new RuntimeException(e);
        }
    }

    public static String stringify(Object o, boolean safe, boolean pretty) {
        if(o == null) return null;

        try {
            return pretty
                ? mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o)
                : mapper.writeValueAsString(o);
        } catch (Throwable e) {
            if(safe) {
                log.warn("", e); return null;
            }
            throw new RuntimeException(e);
        }
    }

    // Shortcuts

    public static JsonNode parseString(String str) {
        return parseString(str, JsonNode.class, false);
    }

    public static <T> T parseString(String str, Class<T> clazz) {
        return parseString(str, clazz, false);
    }

    public static <T> T parseStringSafe(String str, Class<T> clazz) {
        return parseString(str, clazz, true);
    }

    public static String stringify(Object o) {
        return stringify(o, false, false);
    }

    public static String stringifySafe(Object o) {
        return stringify(o, true, false);
    }

    public static String stringifyPretty(Object o) {
        return stringify(o, false, true);
    }

    public static String stringifyPrettySafe(Object o) {
        return stringify(o, true, true);
    }

}
