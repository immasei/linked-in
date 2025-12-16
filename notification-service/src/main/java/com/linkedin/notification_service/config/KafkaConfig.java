package com.linkedin.notification_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.kafka.support.converter.ConversionException;
import java.lang.reflect.Type;

@Configuration
public class KafkaConfig {

    @Bean
    public RecordMessageConverter recordMessageConverter(ObjectMapper mapper) {
        return new UnwrappingStringJsonMessageConverter(mapper);
    }

    static class UnwrappingStringJsonMessageConverter extends StringJsonMessageConverter {
        private final ObjectMapper mapper;

        UnwrappingStringJsonMessageConverter(ObjectMapper mapper) {
            this.mapper = mapper;
        }

        @Override
        protected Object extractAndConvertValue(ConsumerRecord<?, ?> record, Type type) {
            Object v = record.value();
            if (!(v instanceof String s)) {
                return super.extractAndConvertValue(record, type);
            }

            try {
                String trimmed = s.trim();

                // "\"{...}\""
                if (trimmed.startsWith("\"") && trimmed.endsWith("\"")) {
                    String unwrappedJson = mapper.readValue(trimmed, String.class);
                    // parse json text into target type ie PostCreated
                    return mapper.readValue(unwrappedJson, mapper.constructType(type));
                }

                // "{...}"
                return mapper.readValue(trimmed, mapper.constructType(type));
            } catch (Exception e) {
                throw new ConversionException("Failed to convert JSON value: " + s, record, e);
            }
        }
    }
}
