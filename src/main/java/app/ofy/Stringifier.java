package app.ofy;

public interface Stringifier<T> {
    String toString(T o);

    T fromString(String s, Class<T> clazz);
}
