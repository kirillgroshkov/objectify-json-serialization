package app.domain.base;

import lombok.extern.slf4j.Slf4j;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Slf4j
public abstract class BaseEntity<T> {
    public void delete() {
        ofy().delete().entity(this).now();
    }

    public void deleteAsync() {
        ofy().delete().entity(this);
    }

    @SuppressWarnings("unchecked")
    public T save() {
        ofy().save().entity(this).now();
        return (T) this;
    }

    public T saveSafe() {
        try {
            return save();
        } catch(Throwable e) {
            log.error("", e);
            return null;
        }
    }

    public void saveAsync() {
        ofy().save().entity(this);
    }

    public void saveAsyncSafe() {
        try {
            saveAsync();
        } catch(Throwable e) {
            log.error("", e);
        }
    }

    public String toStringNice() {
        return toString()
            .replaceAll(", ", "\n  ")
            .replaceAll("\\{", " {\n  ")
            .replaceAll("\\(", " (\n  ")
            .replaceAll("\\}", "\n  }")
            .replaceAll("\\)", "\n  )")
            ;
    }
}
