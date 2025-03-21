package org.se06203.besgtn.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "organizations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("organizations")
public class Organizations {
    @Id
    private String id;
    private String name;
    private List<String> admins;
    private List<String> members;
}
