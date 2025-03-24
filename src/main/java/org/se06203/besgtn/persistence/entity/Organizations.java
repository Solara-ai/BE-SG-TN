package org.se06203.besgtn.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "organizations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TypeAlias("organizations")
public class Organizations {
    @Id
    @Builder.Default
    private String id = new ObjectId().toString();
    private String name;
    private List<String> admins;
    private List<String> members;
}
