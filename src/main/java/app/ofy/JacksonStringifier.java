package app.ofy;

import app.util.JsonUtil;

public class JacksonStringifier<T> implements Stringifier<T> {

    @Override
    public String toString(T t) {
        return JsonUtil.stringifySafe(t);
    }

    @Override
    public T fromString(String s, Class<T> clazz) {
        return JsonUtil.parseStringSafe(s, clazz);
    }
}
