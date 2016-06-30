
# objectify-json-serialization

> Serialize custom entity fields to/from JSON with [Objectify]

# Problem

Well described in [issue] by @stickfigure:

> I am missing an annotation, which allows me to serialize and store an object graph using Json instead of Java Serialization.
> 
> Json is much more flexible, tolerant and faster, and I would love to see it in objectify natively.
> 
> Details:
> 
> 1.  Java Serialization is opaque, Json is human readable.
> 2. Java Serialization typically takes longer and produces a larger file than Json.
> 3. And most important: The main problem with Java Serialization is versioning. By default, it stores the full package name of a class. Even simple refactoring will break it and render the stored data unreadable. Java Serialization may be an acceptable technique for wrapping data short-term (such as for example data that is transferred over REST and immediately unwrapped on the other side), but I don't consider it suitable for long-term data storage (such as data store), as schemas evolve over time.

# Solution

Add [@SerializeToJson] annotation to the fields you want to be serialized as JSON:

```
@Entity
public class MyEntity {

    @Id
    private Long id;

    private String userName;

    private Integer age;

    @SerializeToJson
    private Map<String,Integer> someMap;

    @SerializeToJson
    private CustomModel custom;
}

```

# Implementation

Most of the magic is done in [SerializeToJsonTranslatorFactory].

Register it like this:

    ObjectifyService.factory().getTranslators().addEarly(new SerializeToJsonTranslatorFactory(new JacksonStringifierFactory()));
    
Note that `addEarly()` is used instead of normal `add()`. Otherwise other TranslatorFactory that's built-in in Objectify takes priority.

I abstracted away JSON implementation behind [Stringifier], [StringifierFactory] and provided implementation for Jackson in  
[JacksonStringifier], [JacksonStringifierFactory].

# Result
## Before

Look at `custom` and `someMap` properties. By default Objectify doesn't know how to serialize them, so it uses Datastore's `EmbeddedEntity`.

![Before][before]


## After

With [@SerializeToJson] annotation and [SerializeToJsonTranslatorFactory] - Objectify uses JSON serialization. Nice!

![After][after]

# License
MIT © [Kirill Groshkov](https://github.com/kirillgroshkov)


[before]: https://raw.githubusercontent.com/kirillgroshkov/objectify-json-serialization/master/screens/before.png
[after]: https://raw.githubusercontent.com/kirillgroshkov/objectify-json-serialization/master/screens/after.png
[@SerializeToJson]: https://github.com/kirillgroshkov/objectify-json-serialization/blob/master/src/main/java/app/ofy/SerializeToJson.java
[SerializeToJsonTranslatorFactory]: https://github.com/kirillgroshkov/objectify-json-serialization/blob/master/src/main/java/app/ofy/SerializeToJsonTranslatorFactory.java
[Stringifier]: https://github.com/kirillgroshkov/objectify-json-serialization/blob/master/src/main/java/app/ofy/Stringifier.java
[StringifierFactory]: https://github.com/kirillgroshkov/objectify-json-serialization/blob/master/src/main/java/app/ofy/StringifierFactory.java
[JacksonStringifier]: https://github.com/kirillgroshkov/objectify-json-serialization/blob/master/src/main/java/app/ofy/JacksonStringifier.java
[JacksonStringifierFactory]: https://github.com/kirillgroshkov/objectify-json-serialization/blob/master/src/main/java/app/ofy/JacksonStringifierFactory.java
[objectify]: https://github.com/objectify/objectify
[issue]: https://github.com/objectify/objectify/issues/244

