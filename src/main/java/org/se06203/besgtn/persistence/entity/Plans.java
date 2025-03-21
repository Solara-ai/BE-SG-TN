package org.se06203.besgtn.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "plans")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("plans")
public class Plans {
    @Id
    private String id;
    private String epicId;
    private String date;
    private Integer quantityTask;
    private List<String> taskIds;
}


