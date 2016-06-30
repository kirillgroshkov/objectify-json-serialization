package app.ofy;

import app.domain.MyEntity;
import com.googlecode.objectify.ObjectifyService;

public class ObjectifyInit {
    public static void init() {
        // Register Objectify Translators and Entities

        ObjectifyService.factory().getTranslators().addEarly(new SerializeToJsonTranslatorFactory(new JacksonStringifierFactory()));

        ObjectifyService.register(MyEntity.class);
    }
}
