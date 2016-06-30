package app.test;

import app.domain.MyEntity;
import app.model.CustomModel;
import app.test.gae.BaseDataTest;
import app.util.MapBuilder;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static com.googlecode.objectify.ObjectifyService.ofy;
import static org.junit.Assert.assertEquals;

@Slf4j
public class MainTest extends BaseDataTest {

    @Before
    public void before() {
        setupDataTest();
    }

    @After
    public void after() {
        tearDownDataTest();
    }

    @Test
    public void test1() {

        CustomModel cm = CustomModel.builder()
            .field1("field1 value")
            .field2(145L)
            .stringList(Lists.newArrayList("one", "two", "and three"))
            .build();

        MyEntity e = MyEntity.builder()
            .userName("John")
            .age(32)
            .someMap(MapBuilder.<String,Integer>build(
                "a1", 1,
                "a2", 2,
                "a3", 3
            ))
            .custom(cm)
            .build();

        e.save();
        log.info(e.toStringNice());
        long id = e.getId();

        ofy().clear();

        MyEntity e2 = ofy().load().type(MyEntity.class).id(id).now();
        log.info(e2.toStringNice());

        assertEquals(e, e2);
        assertEquals(e.getUserName(), e2.getUserName());
        assertEquals(e.getSomeMap(), e2.getSomeMap());
        assertEquals(e.getCustom(), e2.getCustom());
        assertEquals(e.getCustom().getField1(), e2.getCustom().getField1());
        assertEquals(e.getCustom().getStringList(), e2.getCustom().getStringList());
    }
}
