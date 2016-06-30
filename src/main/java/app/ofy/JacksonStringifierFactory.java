package app.ofy;

public class JacksonStringifierFactory<T> implements StringifierFactory<T> {
    @Override
    public Stringifier<T> create() {
        return new JacksonStringifier<>();
    }
}
