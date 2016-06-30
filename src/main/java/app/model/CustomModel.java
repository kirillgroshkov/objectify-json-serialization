package app.model;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CustomModel {
    private String field1;
    private Long field2;
    private List<String> stringList;
}
