package org.se06203.besgtn.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "organizations")
@Data
public class Organizations {
    @Id
    private String id;
    private String name;
    private String adminId;
    private List<String> members;
}
