package com.icicilombard.mcp.config;

import com.icicilombard.mcp.tool.InsuranceTools;
import com.icicilombard.mcp.tool.PolicyResourceTools;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpToolConfig {

    @Bean
    public ToolCallbackProvider toolCallbackProvider(
            InsuranceTools insuranceTools,
            PolicyResourceTools policyResourceTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(insuranceTools, policyResourceTools)
                .build();
    }
}
