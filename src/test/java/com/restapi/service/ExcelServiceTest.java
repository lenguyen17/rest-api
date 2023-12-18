package com.restapi.service;

import com.restapi.entity.User;
import com.restapi.exception.ExcelException;
import com.restapi.repository.UserRepository;
import com.restapi.util.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class ExcelServiceTest {

    @Mock
    private ExcelService excelService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MultipartFile multipartFile;

    @Mock
    private FileUtil fileUtil;

    private List<User> list;

    @BeforeEach
    public void setup(){
        list = Arrays.asList(
                User.build(1,"username","password","first",
                        "last","test", LocalDate.now(), "example@gmail.com")
        );
    }

    @Test
    void importData() throws IOException {
        File file = mock(File.class);
        when(excelService.importData(file)).thenReturn(list);

        List<User> result = excelService.importData(file);
        assertEquals(list.size(), result.size());

        verify(userRepository, times(0)).save(any(User.class));
    }

    @Test
    void exportData() throws ExcelException {
        byte[] bytes = "Test data".getBytes();
        when(excelService.exportData(ExcelService.EXPORT_TYPE.LOCAL)).thenReturn(bytes);
        when(excelService.exportData(ExcelService.EXPORT_TYPE.API)).thenReturn(bytes);

        byte[] localExportBytes = excelService.exportData(ExcelService.EXPORT_TYPE.LOCAL);
        assertNotNull(localExportBytes);

        byte[] apiExportBytes = excelService.exportData(ExcelService.EXPORT_TYPE.API);
        assertNotNull(apiExportBytes);

        verify(excelService, times(1)).exportData(ExcelService.EXPORT_TYPE.LOCAL);
        verify(excelService, times(1)).exportData(ExcelService.EXPORT_TYPE.API);
    }

    @Test
    void importDataScheduled() throws IOException, ExcelException {
        File file = mock(File.class);
        when(excelService.importData(file)).thenReturn(list);
        excelService.importDataScheduled();

        verify(excelService, times(1)).importDataScheduled();
    }
}