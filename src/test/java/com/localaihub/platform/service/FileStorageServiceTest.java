package com.localaihub.platform.service;

/**
 * @author jiang_star
 * @date 2024/3/28
 */

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.core.io.Resource;
import java.io.IOException;
import java.nio.file.Files;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class FileStorageServiceTest {

    @Autowired
    private FileStorageService fileStorageService;

    @Test
    public void testStoreFileAndLoadFileAsResource() {
        try {
            // 创建一个测试文件
            MockMultipartFile file = new MockMultipartFile(
                    "testFile.txt", // 文件名
                    "This is a test file content".getBytes() // 文件内容
            );

            // 调用storeFile方法存储文件
            String storedFilePath = fileStorageService.storeFile(file);
            assertNotNull(storedFilePath); // 确保文件存储成功并且返回了文件路径

            // 调用loadFileAsResource方法加载文件
            Resource loadedFile = fileStorageService.loadFileAsResource("testFile.txt");
            assertNotNull(loadedFile); // 确保文件成功加载

            // 读取加载的文件内容并进行断言
            String fileContent = new String(Files.readAllBytes(loadedFile.getFile().toPath()));
            assertEquals("This is a test file content", fileContent); // 确保加载的文件内容正确

        } catch (IOException e) {
            fail("IOException occurred: " + e.getMessage());
        }
    }
}
