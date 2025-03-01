package org.se06203.sgtmbackend.persistence.entity;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "plans")
public class Plans {
    @Id
    private String id;

    private String epic;

    private String date;

    private int quantityTask;

    private List<String> taskIds;
}
