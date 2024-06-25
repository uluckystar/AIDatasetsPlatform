package com.localaihub.platform.module.system.base.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jiaxing Jiang
 * @version 0.1.0-SNAPSHOT
 * @date 2024/5/8 18:58
 */
@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI swaggerOpenApi() {
        return new OpenAPI()
                .info(new Info().title("训练集数据平台系统服务")
                        .description("\n" +
                                "训练集数据平台是构建现代数据驱动型企业的重要基础设施之一。它为企业提供了存储、管理、处理和分析大规模数据集的能力，从而支持各种数据相关的业务和应用。")
                        .version("v1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("设计文档")
                        .url("https://juejin.cn/user/254742430749736/posts"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }
}
