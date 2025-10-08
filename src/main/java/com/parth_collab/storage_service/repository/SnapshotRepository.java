package com.parth_collab.storage_service.repository;

import com.parth_collab.storage_service.model.Snapshot;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SnapshotRepository extends MongoRepository<Snapshot, String> {
    List<Snapshot> findByDocumentIdOrderByCreatedAtDesc(String documentId);
}
