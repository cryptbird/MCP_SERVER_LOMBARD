package com.icicilombard.mcp.config;

import com.icicilombard.mcp.tool.InsuranceTools;
import com.icicilombard.mcp.tool.PolicyResourceTools;
import io.modelcontextprotocol.server.McpServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class McpFeatureConfig {

    @Bean
    ToolCallbackProvider toolCallbackProvider(
            InsuranceTools insuranceTools,
            PolicyResourceTools policyResourceTools
    ) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(insuranceTools, policyResourceTools)
                .build();
    }

    @Bean
    List<McpServerFeatures.SyncResourceSpecification> syncResourceSpecifications() {
        return List.of(
                textResource("resource://policy/overview",
                        "Policy Overview",
                        "High-level ICICI Lombard home policy overview.",
                        "text/markdown",
                        readClasspath("static/policy/01-policy-overview.md")),
                textResource("resource://reference/faq",
                        "FAQ Common Scenarios",
                        "Frequently asked questions and response guidance.",
                        "text/markdown",
                        readClasspath("static/reference/faq-common-scenarios.md")),
                textResource("resource://reference/limits",
                        "Coverage Limits Quick Reference",
                        "JSON quick reference for coverage limits.",
                        "application/json",
                        readClasspath("static/reference/coverage-limits-quick-ref.json")),
                textResource("resource://metadata/tools",
                        "MCP Tools Metadata",
                        "Metadata pointer to authoritative tools spec.",
                        "text/markdown",
                        readClasspath("static/metadata/mcp-tools-section.md")),
                textResource("resource://metadata/implementation-plan",
                        "Implementation Plan Metadata",
                        "Metadata pointer to implementation plan guidance.",
                        "text/markdown",
                        readClasspath("static/metadata/mcp-server-implementation-plan.md"))
        );
    }

    @Bean
    List<McpServerFeatures.SyncPromptSpecification> syncPromptSpecifications() {
        McpSchema.PromptArgument sessionIdArg = new McpSchema.PromptArgument(
                "sessionId",
                "Stable session id used across tool calls in one conversation.",
                true
        );

        McpSchema.Prompt quoteFlowPrompt = new McpSchema.Prompt(
                "home-insurance-journey",
                "Guides the assistant through quote to policy flow with the correct tool order.",
                List.of(sessionIdArg)
        );

        McpServerFeatures.SyncPromptSpecification quoteFlowSpec =
                new McpServerFeatures.SyncPromptSpecification(
                        quoteFlowPrompt,
                        (exchange, request) -> new McpSchema.GetPromptResult(
                                "Use this guidance while handling ICICI Lombard Home Insurance journey.",
                                List.of(new McpSchema.PromptMessage(
                                        McpSchema.Role.ASSISTANT,
                                        new McpSchema.TextContent("""
                                                You are assisting users with ICICI Lombard home insurance.
                                                Use a single stable sessionId across calls.
                                                Follow this tool order:
                                                1) generateQuote
                                                2) createProposal
                                                3) createPaymentLink
                                                4) Ask user to confirm payment completion
                                                5) fetchPolicyDetails
                                                If any required prior state is missing, ask the user for missing details and call the previous tool first.
                                                """)
                                ))
                        )
                );

        return List.of(quoteFlowSpec);
    }

    private McpServerFeatures.SyncResourceSpecification textResource(
            String uri,
            String name,
            String description,
            String mimeType,
            String content
    ) {
        McpSchema.Resource resource = new McpSchema.Resource(uri, name, description, mimeType, null);
        return new McpServerFeatures.SyncResourceSpecification(
                resource,
                (exchange, request) -> new McpSchema.ReadResourceResult(
                        List.of(new McpSchema.TextResourceContents(uri, mimeType, content))
                )
        );
    }

    private String readClasspath(String path) {
        try {
            return new String(new ClassPathResource(path).getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to read classpath resource: " + path, e);
        }
    }
}
