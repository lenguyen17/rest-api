package com.restapi.controller;

import com.restapi.entity.User;
import com.restapi.exception.ExcelException;
import com.restapi.repository.UserRepository;
import com.restapi.service.ExcelService;
import com.restapi.util.FileUtil;
import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.restapi.util.FileUtil.isExcelFile;

@RestController
@RequestMapping("/api/data")
public class DataController {

    @Autowired
    private ExcelService excelService;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxFileSize;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/import")
    public ResponseEntity<?> importData(@RequestParam("file") MultipartFile multipartFile)
            throws IOException, ExcelException {
        long maxSize = FileUtil.parseSize(maxFileSize);
        if (multipartFile.getSize() > FileUtil.parseSize(maxFileSize)) {
            throw new FileSizeLimitExceededException("File size exceeds the limit", multipartFile.getSize(), maxSize);
        }
        File file = File.createTempFile("temp", null);
//        File file = ExcelUtil.convertToFile(multipartFile);
        file = FileUtil.convertToFile(multipartFile);
        if (isExcelFile(file)) {
            List<User> importedData = excelService.importData(file);
            file.delete();
            return ResponseEntity.ok(importedData);
        } else {
            throw new ExcelException("File is not supported");
        }
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData() throws IOException, ExcelException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(
                MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
        headers.setContentDispositionFormData("attachment", "users.xlsx");

        return new ResponseEntity<>(excelService.exportData(ExcelService.EXPORT_TYPE.API),
                headers, org.springframework.http.HttpStatus.OK);
    }


}
