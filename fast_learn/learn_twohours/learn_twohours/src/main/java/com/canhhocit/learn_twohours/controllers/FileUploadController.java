package com.canhhocit.learn_twohours.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.canhhocit.learn_twohours.Services.IStorageService;
import com.canhhocit.learn_twohours.models.ResponseObject;

@Controller
@RequestMapping("api/v1/files")
public class FileUploadController {
    // inject Storage service here
    @Autowired
    private IStorageService storageService;

    @PostMapping("/fileUpload")
    public ResponseEntity<ResponseObject> updloadFile(@RequestParam("file") MultipartFile file) {
        try {
            // save files to a folder => use service\
            String generatedFileName = storageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "Upload file sucessfully", generatedFileName));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body(new ResponseObject("okn't", e.getMessage(), ""));
        }
    }

    @GetMapping("/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(
            @PathVariable("fileName") String fileName) {

        try {
            byte[] bytes = storageService.readFileContent(fileName);

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // hoặc detect
                    .body(bytes);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<ResponseObject> getUploadedFiles() {
        try {
            List<String> urls = storageService.loadAll()
                    .map(path -> {
                        String urlPath = MvcUriComponentsBuilder
                                .fromMethodName(
                                        FileUploadController.class,
                                        "readDetailFile",
                                        path.getFileName().toString())
                                .build()
                                .toUri()
                                .toString();
                        return urlPath;
                    })
                    .collect(Collectors.toList());

            return ResponseEntity.ok(
                    new ResponseObject("OK", "List files successfully", urls));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("FAILED", e.getMessage(), null));
        }
    }

    @DeleteMapping("")
    public ResponseEntity<ResponseObject> deleteAllFiles() {
        try {
            storageService.deleteAllFiles();
            return ResponseEntity.ok(
                    new ResponseObject("OK", "All files deleted successfully", ""));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseObject("FAILED", e.getMessage(), ""));
        }
    }

}
