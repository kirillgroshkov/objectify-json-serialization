package app.domain;

import app.domain.base.BaseEntity;
import app.model.CustomModel;
import app.ofy.SerializeToJson;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false) // equality by all fields
public class MyEntity extends BaseEntity<MyEntity> {

    @Id
    private Long id;

    private String userName;

    private Integer age;

    @SerializeToJson
    private Map<String,Integer> someMap;

    @SerializeToJson
    private CustomModel custom;
}
