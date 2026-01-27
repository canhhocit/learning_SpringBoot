package com.canhhocit.learn_twohours.Services;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.web.multipart.MultipartFile;

public interface IStorageService {
    public String storeFile(MultipartFile file);
    public Stream<Path> loadAll();// load all file insite a folder
    public byte[] readFileContent(String fileName); //view file
    public void deleteAllFiles();
}
