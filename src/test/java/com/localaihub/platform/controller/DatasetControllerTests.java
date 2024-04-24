package com.localaihub.platform.controller;

import com.localaihub.platform.service.FileStorageService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * @author jiang_star
 * @date 2024/3/26
 */
@SpringBootTest
@AutoConfigureMockMvc
public class DatasetControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileStorageService fileStorageService;

    @Test
    public void testFileUpload() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Test content".getBytes());
        MockHttpServletRequestBuilder multipartRequest = MockMvcRequestBuilders.multipart("/api/datasets/upload")
                .file(file)
                .with(SecurityMockMvcRequestPostProcessors.csrf());

        mockMvc.perform(multipartRequest)
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("File uploaded successfully")));
    }

    // 添加额外的测试方法来模拟身份验证
    @Test
    public void testWithAuthenticatedUser() throws Exception {
        // 模拟身份验证的用户
        UserDetails user = User.withUsername("user").password("password").roles("USER").build();

        // 使用模拟的用户进行身份验证
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()));
        SecurityContextHolder.setContext(securityContext);

        // 发起请求
        mockMvc.perform(get("/api/datasets/upload"))
                .andExpect(status().isOk());
    }
}
