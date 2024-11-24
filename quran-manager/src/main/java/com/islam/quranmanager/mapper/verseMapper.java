package com.islam.quranmanager.mapper;

import com.islam.quranmanager.model.verse;
import dto.verseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
@Mapper(componentModel = "spring")
public interface verseMapper {
    verseMapper INSTANCE = Mappers.getMapper(verseMapper.class);
    // Map le verseDTO en verse

    verse toverse(verseDTO verseDTO);
}
