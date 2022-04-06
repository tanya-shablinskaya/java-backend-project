package com.gmail.puhovashablinskaya.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    ResponseEntity<String> process(MultipartFile file);
}
