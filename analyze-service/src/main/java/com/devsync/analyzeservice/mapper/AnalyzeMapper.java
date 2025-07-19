package com.devsync.analyzeservice.mapper;


import com.devsync.analyzeservice.dto.model.AnalyzeDto;
import com.devsync.analyzeservice.entity.Analyze;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnalyzeMapper {
    AnalyzeDto toDto(Analyze entity);
    Analyze toEntity(AnalyzeDto dto);
}
