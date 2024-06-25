package com.localaihub.platform.module.system.base.service.dataset;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/20 02:17
 */
@Service
public class OpenaiServiceImpl implements OpenaiService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public OpenaiServiceImpl(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public String getReplace(String description) {
        String url = UriComponentsBuilder.fromHttpUrl("https://api.openai.com/v1/chat/completions")
                .toUriString();

        String requestBody = "{"
                + "\"model\": \"gpt-3.5-turbo\","
                + "\"messages\": [{\"role\": \"user\", \"content\": \""
                + description + " 根据上述数据集介绍，推荐适用的模型和训练方法。"
                + "请使用专业的、简洁明了的学术风格进行回复。"
                + "请使用正式且具备权威性的语气进行回复。"
                + "回复对象为自然语言处理和人工智能领域的研究人员和开发者。"
                + "以JSON格式输出适用模型和训练方法的建议，JSON格式如下："
                + "{ \\\"推荐的模型\\\": [{\\\"模型名称\\\": \\\"模型的名称\\\", \\\"描述\\\": \\\"模型的详细描述\\\"}],"
                + "\\\"推荐的训练方法\\\": [{\\\"方法名称\\\": \\\"训练方法的名称\\\", \\\"描述\\\": \\\"训练方法的详细描述\\\"}]}。"
                + "请用中文回复，详细越好。"
                + "\"}],"
                + "\"max_tokens\": 1500,"
                + "\"n\": 1"
                + "}";


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    JsonNode jsonResponse = objectMapper.readTree(response.getBody());
                    String rawContent = jsonResponse.path("choices").get(0).path("message").path("content").asText().trim();
                    return formatResponse(rawContent);
                } catch (Exception e) {
                    return "Error parsing response from OpenAI";
                }
            } else {
                return "Error calling OpenAI: " + response.getStatusCode();
            }
        } catch (Exception ex) {
            ex.printStackTrace(); // 打印异常堆栈
            return "Unable to get a response from OpenAI";
        }
    }

    private String formatResponse(String responseContent) {
        try {
            // 将响应内容解析为 JSON 对象
            JsonNode jsonNode = objectMapper.readTree(responseContent);
            // 格式化 JSON 输出
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (Exception e) {
            return "Error formatting response: " + e.getMessage();
        }
    }
}