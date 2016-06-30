package app.servlet;

import app.domain.MyEntity;
import app.model.CustomModel;
import app.util.JsonUtil;
import app.util.MapBuilder;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static com.googlecode.objectify.ObjectifyService.ofy;

@Slf4j
public class TestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

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
        long id = e.getId();

        ofy().clear();

        MyEntity e2 = ofy().load().type(MyEntity.class).id(id).now();


        log.info("hej");

        PrintWriter out = resp.getWriter();
        out.println(JsonUtil.stringifyPrettySafe(e2));
    }
}
