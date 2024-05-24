package ru.shutov.itone.tasktracker.mapper;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UnmappedPolicyMapperConfig {
}
