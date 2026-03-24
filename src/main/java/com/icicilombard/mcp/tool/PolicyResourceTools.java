package com.icicilombard.mcp.tool;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class PolicyResourceTools {

    @Tool(name = "getPolicyResource", description = "Retrieve a policy/resource file by topic key.")
    public String getPolicyResource(
            @ToolParam(description = "overview, tools, implementation-plan, faq, limits") String topic
    ) {
        return switch (topic) {
            case "resources-section" -> readFromWorkspace("mcp-resources-section.md");
            case "tools-section" -> readFromWorkspace("mcp-tools-section.md");
            case "implementation-plan" -> readFromWorkspace("MCP_SERVER_IMPLEMENTATION_PLAN.md");
            case "overview" -> readClasspathByTopic("static/policy/01-policy-overview.md");
            case "faq" -> readClasspathByTopic("static/reference/faq-common-scenarios.md");
            case "limits" -> readClasspathByTopic("static/reference/coverage-limits-quick-ref.json");
            case "tools" -> readClasspathByTopic("static/metadata/mcp-tools-section.md");
            default -> throw new IllegalArgumentException("Unknown topic: " + topic);
        };
    }

    private String readFromWorkspace(String fileName) {
        Path path = Path.of(fileName);
        try {
            if (Files.exists(path)) {
                return Files.readString(path, StandardCharsets.UTF_8);
            }
            return "Resource not found in workspace: " + fileName;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read workspace resource: " + fileName, e);
        }
    }

    private String readClasspathResource(String classpath) throws IOException {
        return new String(new ClassPathResource(classpath).getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    }

    private String readClasspathByTopic(String classpath) {
        try {
            return readClasspathResource(classpath);
        } catch (IOException e) {
            throw new IllegalStateException("Unable to read classpath resource: " + classpath, e);
        }
    }
}
