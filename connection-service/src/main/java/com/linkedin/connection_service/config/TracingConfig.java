package com.linkedin.connection_service.config;

import brave.handler.MutableSpan;
import brave.handler.SpanHandler;
import brave.propagation.TraceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TracingConfig {

    @Bean
    SpanHandler eurekaSpanFilter() {
        return new SpanHandler() {
            @Override
            public boolean end(TraceContext context, MutableSpan span, Cause cause) {
                String url = span.tag("http.url");
                return url == null || !url.contains("/eureka/");
            }
        };
    }
    
}
