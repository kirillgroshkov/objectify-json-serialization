package app.ofy;

public interface StringifierFactory<T> {
    Stringifier<T> create();
}
