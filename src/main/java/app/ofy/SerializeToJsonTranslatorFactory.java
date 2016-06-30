package app.ofy;

import com.googlecode.objectify.impl.Path;
import com.googlecode.objectify.impl.translate.*;
import lombok.extern.slf4j.Slf4j;

/**
 * Use it like this:
 * ObjectifyService.factory().getTranslators().addEarly(new SerializeToJsonTranslatorFactory());
 *
 * Add annotation on the fields you want to apply it to:
 * @SerializeToJson
 * private Map<String,Integer> someField;
 */
@Slf4j
public class SerializeToJsonTranslatorFactory implements TranslatorFactory<Object, String> {
    private StringifierFactory stringifierFactory;

    public SerializeToJsonTranslatorFactory(StringifierFactory stringifierFactory) {
        this.stringifierFactory = stringifierFactory;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Translator<Object, String> create(TypeKey<Object> tk, CreateContext createContext, Path path) {
        if(tk.getAnnotation(SerializeToJson.class) == null) return null; // use it only for fields with this annotation

        final Class clazz = tk.getTypeAsClass();
        // log.info("SerializeToJsonTranslatorFactory clazz: {}", clazz);

        final Stringifier stringifier = stringifierFactory.create();

        return new Translator<Object, String>() {
            @Override
            public Object load(String s, LoadContext loadContext, Path path) throws SkipException {
                // log.info("SerializeToJsonTranslatorFactory load: {}", s);
                return stringifier.fromString(s, clazz);
            }

            @Override
            public String save(Object o, boolean b, SaveContext saveContext, Path path) throws SkipException {
                // log.info("SerializeToJsonTranslatorFactory save: {}", o);
                return stringifier.toString(o);
            }
        };
    }
}
