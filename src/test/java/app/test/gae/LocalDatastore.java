package app.test.gae;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;

@Slf4j
public class LocalDatastore {
    public static final String DEF_APP_ID = "app";
    public static final String DEF_AUTH_DOMAIN = "gmail.com";

    private LocalServiceTestHelper helper = null;
    private Closeable ofyCloseable = null;

    public static LocalDatastoreServiceTestConfig createConfig(String localDbPath) {
        LocalDatastoreServiceTestConfig cfg = new LocalDatastoreServiceTestConfig();
        cfg.setNoStorage(false);
        cfg.setBackingStoreLocation(localDbPath);
        cfg.setApplyAllHighRepJobPolicy();
        return cfg;
    }

    public static LocalDatastoreServiceTestConfig createConfigNoStorage() {
        LocalDatastoreServiceTestConfig cfg = new LocalDatastoreServiceTestConfig();
        cfg.setNoStorage(true);
        cfg.setApplyAllHighRepJobPolicy();
        return cfg;
    }

    public static LocalDatastore create(LocalDatastoreServiceTestConfig cfg) {
        return new LocalDatastore(cfg);
    }

    public static LocalDatastore create(String localDbPath) {
        return new LocalDatastore(createConfig(localDbPath));
    }

    public static LocalDatastore createNoStorage() {
        return new LocalDatastore(createConfigNoStorage());
    }

    private LocalDatastore(LocalDatastoreServiceTestConfig cfg) {
        helper = new LocalServiceTestHelper(cfg);
        helper
            .setEnvAppId(DEF_APP_ID)
            .setEnvAuthDomain(DEF_AUTH_DOMAIN)
            .setUp();

        ofyCloseable = ObjectifyService.begin();

        // log.info("LocalDatastore created");
    }


    public void tearDown() throws IOException {
        ofyCloseable.close();
        helper.tearDown();
    }

    public void tearDownSafe() {
        try {
            tearDown();
        } catch (Throwable e) {
            log.error("", e);
        }
    }
}
