package org.se06203.besgtn.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "plans")
@Data
public class Plans {
    @Id
    private String id;
    private String epicId;
    private LocalDate date;
    private int quantityTask;
    private List<String> taskIds;
}


