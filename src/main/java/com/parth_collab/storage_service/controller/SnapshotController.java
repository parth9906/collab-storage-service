package com.parth_collab.storage_service.controller;

import com.parth_collab.storage_service.model.Snapshot;
import com.parth_collab.storage_service.service.SnapshotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/storage")
@RequiredArgsConstructor
public class SnapshotController {

    private final SnapshotService snapshotService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadSnapshot(
            @RequestParam String documentId,
            @RequestParam String userId,
            @RequestPart MultipartFile file
    ) {
        try {
            Snapshot saved = snapshotService.saveSnapshot(documentId, userId, file);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error saving snapshot: " + e.getMessage());
        }
    }
}
