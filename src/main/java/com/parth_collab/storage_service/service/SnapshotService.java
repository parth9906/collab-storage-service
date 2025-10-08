package com.parth_collab.storage_service.service;

import com.parth_collab.storage_service.model.Snapshot;
import com.parth_collab.storage_service.repository.SnapshotRepository;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnapshotService {

    private final MinioClient minioClient;
    private final SnapshotRepository snapshotRepository;

    @Value("${minio.bucket-name}")
    private String bucketName;

    public Snapshot saveSnapshot(String documentId, String userId, MultipartFile file) throws Exception {
        String objectName = "snapshots/" + documentId + "/" + UUID.randomUUID() + ".json";

        try (InputStream inputStream = file.getInputStream()) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build()
            );
        }

        Snapshot snapshot = Snapshot.builder()
                .documentId(documentId)
                .userId(userId)
                .filePath(objectName)
                .version(UUID.randomUUID().toString())
                .createdAt(Instant.now())
                .build();

        return snapshotRepository.save(snapshot);
    }
}
