package com.restapi.service;

import com.restapi.entity.User;
import com.restapi.exception.ExcelException;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Service
@Slf4j
@EnableScheduling
public class ExcelService {
    private static final String baseDir = "src/main/java/com/restapi/excel/";

    public enum EXPORT_TYPE {
        API, LOCAL
    }

    @Autowired
    private UserService userService;

    @Value("${import.directory}")
    private String importDirectory;

    public static String getUniqueFileName() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = dateFormat.format(new Date());
        return "file_" + timestamp + ".xlsx";
    }

    public List<User> importData(File file) throws IOException {
        List<User> dataList = new ArrayList<>();

        try (Workbook workbook  = WorkbookFactory.create(file)) {
            Sheet sheet = workbook.getSheetAt(0);
            Row headerRow = sheet.getRow(0);

            // Get list column names to set property
            Iterator<Cell> cellIterator = headerRow.cellIterator();
            List<String> columnNames = new ArrayList<>();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                columnNames.add(cell.getStringCellValue());
            }

            // Loop from column 1 to save data
            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row currentRow = sheet.getRow(rowIndex);
                User user = new User();

                for (int colIndex = 0; colIndex < columnNames.size(); colIndex++) {
                    Cell currentCell = currentRow.getCell(colIndex);
                    String columnName = columnNames.get(colIndex).toLowerCase();
                    switch (columnName) {
                        case "userid":
                            user.setId((int) currentCell.getNumericCellValue());
                            break;
                        case "username":
                            user.setUsername(currentCell.getStringCellValue());
                            break;
                        case "password":
                            user.setPassword(currentCell.getStringCellValue());
                            break;
                        case "first_name":
                            user.setFirst_name(currentCell.getStringCellValue());
                            break;
                        case "last_name":
                            user.setLast_name(currentCell.getStringCellValue());
                            break;
                        case "address":
                            user.setAddress(currentCell.getStringCellValue());
                            break;
                        case "birthday":
                            DataFormatter dataFormatter = new DataFormatter();
                            if (currentCell.getCellType() == CellType.NUMERIC) {
                                Date javaDate= DateUtil.getJavaDate(currentCell.getNumericCellValue());
                                LocalDate birthday = LocalDate.parse(
                                        new SimpleDateFormat("yyyy-MM-dd").format(javaDate));
                                user.setBirthday(birthday);
                            } else if (currentCell.getCellType() == CellType.STRING) {
                                LocalDate birthday = LocalDate.parse(currentCell.getStringCellValue());
                                user.setBirthday(birthday);
                            }
                            break;
                        case "email":
                            user.setEmail(currentCell.getStringCellValue());
                            break;
                    }
                }
                dataList.add(user);
                userService.saveAllUser(dataList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public byte[] exportData(EXPORT_TYPE exportType) throws ExcelException {
        List<User> userList = userService.getAllUsers();
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Users");
            sheet.setColumnWidth(0, 6000);
            sheet.setColumnWidth(1, 4000);

            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("UserID");
            headerRow.createCell(1).setCellValue("Username");
            headerRow.createCell(2).setCellValue("Password");
            headerRow.createCell(3).setCellValue("First_name");
            headerRow.createCell(4).setCellValue("Last_name");
            headerRow.createCell(5).setCellValue("Address");
            headerRow.createCell(6).setCellValue("Birthday");
            headerRow.createCell(7).setCellValue("Email");

            // Create header cell style
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

            XSSFFont font = (XSSFFont) workbook.createFont();
            font.setFontName("Arial");
            font.setFontHeightInPoints(((short) 14));
            font.setBold(true);
            headerStyle.setFont(font);


            // Apply the style to each cell in the row
            for (int i = 0; i < headerRow.getPhysicalNumberOfCells(); i++) {
                headerRow.getCell(i).setCellStyle(headerStyle);
            }

            // Add data from user list to excel
            int rowNum = 1;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            for (User user : userList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(user.getId());
                row.createCell(1).setCellValue(user.getUsername());
                row.createCell(2).setCellValue(user.getPassword());
                row.createCell(3).setCellValue(user.getFirst_name());
                row.createCell(4).setCellValue(user.getLast_name());
                row.createCell(5).setCellValue(user.getAddress());
                row.createCell(6).setCellValue(user.getBirthday().format(formatter));
                row.createCell(7).setCellValue(user.getEmail());
            }
            log.info("Users size: " + userList.size());
            switch (exportType) {
                case API:
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    workbook.write(outputStream);
                    return outputStream.toByteArray();
                case LOCAL:
                    try (FileOutputStream fileOut = new FileOutputStream(baseDir + "/exportfiles/" + getUniqueFileName())) {
                        workbook.write(fileOut);
                    } catch (Exception e) {
                        throw new RuntimeException(e);}
                    break;
                default:
                    throw new IllegalArgumentException("Invalid export type: " + exportType);
            }
        } catch (Exception e) {
            throw new ExcelException("Error creating workbook" + e.toString());
        }
        return null;
    }

    @Scheduled(cron = "0 0 7 * * ?")
    public void importDataScheduled() throws ExcelException {
        try {
            Files.list(Paths.get(baseDir + "importfiles"))
                    .filter(path -> path.toString().toLowerCase().endsWith(".xlsx"))
                    .forEach(x -> {
                        try {
                            log.info("File scan: " + x.getFileName());
                            importData(x.toFile());
                            log.info("File imported: " + x.getFileName());
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        } catch (Exception e) {
            throw new ExcelException("Error load file: " + baseDir+"importfiles");
        }
    }

    @Scheduled(cron = "0 0 18 * * ?")
    public void exportScheduledData() throws ExcelException {
        exportData(EXPORT_TYPE.LOCAL);
    }
}

