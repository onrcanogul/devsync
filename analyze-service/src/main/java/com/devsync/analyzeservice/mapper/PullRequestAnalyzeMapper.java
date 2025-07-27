package com.devsync.analyzeservice.mapper;

import com.devsync.analyzeservice.model.model.PullRequestAnalyzeDto;
import com.devsync.analyzeservice.entity.PullRequestAnalyze;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PullRequestAnalyzeMapper {
    PullRequestAnalyzeDto toDto(PullRequestAnalyze entity);
    PullRequestAnalyze toEntity(PullRequestAnalyzeDto dto);
}
