package com.parth_collab.storage_service.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "snapshots")
public class Snapshot {
    @Id
    private String id;
    private String documentId;
    private String userId;
    private String version;
    private String filePath;
    private Instant createdAt;
}
