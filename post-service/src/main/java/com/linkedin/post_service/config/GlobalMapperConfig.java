package com.linkedin.post_service.config;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@MapperConfig(
        componentModel = "spring",

        // Don't overwrite entity fields with null from DTO
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,

        // (Optional) Constructor injection
        injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface GlobalMapperConfig {}
