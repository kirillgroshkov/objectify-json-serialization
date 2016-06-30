package app.test.gae;

import app.ofy.ObjectifyInit;
import app.test.BaseTest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseDataTest extends BaseTest {
    protected LocalDatastore localDatastore;

    protected void setupDataTest() {
        ObjectifyInit.init();

        localDatastore = LocalDatastore.createNoStorage();
    }

    protected void tearDownDataTest() {
        localDatastore.tearDownSafe();
        log.info("BaseDataTest tearedDown ok");
    }
}
