package com.restapi.util;

import io.micrometer.common.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class FileUtil {
    public static boolean isFileWithExtension(File file, String extension) {
        return file != null && file.isFile() &&
                StringUtils.isNotBlank(file.getName()) &&
                file.getName().toLowerCase().endsWith(extension);
    }

    public static boolean isExcelFile(File file) {
        return isFileWithExtension(file, ".xlsx") || isFileWithExtension(file, ".xls");
    }

    public static boolean isCsvFile(File file) {
        return isFileWithExtension(file, ".csv");
    }

    public static File convertToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }
        return file;
    }

    public static long parseSize(String size) {
        if (size.endsWith("KB")) {
            return Long.parseLong(size.substring(0, size.length() - 2)) * 1024;
        } else if (size.endsWith("MB")) {
            return Long.parseLong(size.substring(0, size.length() - 2)) * 1024 * 1024;
        } else if (size.endsWith("GB")) {
            return Long.parseLong(size.substring(0, size.length() - 2)) * 1024 * 1024 * 1024;
        } else {
            throw new IllegalArgumentException("Invalid size format: " + size);
        }
    }
}
