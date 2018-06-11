package com.rest.restlibrary.mapper;

import com.rest.restlibrary.data.Copy;
import com.rest.restlibrary.domain.CopyDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CopyMapper {
    public Copy mapToCopy(CopyDto copyDto){
        return new Copy(copyDto.getId(), copyDto.getBook(), copyDto.getInventoryNumber(), copyDto.getBorrows());
    }

    public CopyDto mapToCopyDto(Copy copy){
        return new CopyDto(copy.getId(), copy.getBook(), copy.getInventoryNumber(), copy.getBorrows());
    }

    public List<CopyDto> mapToCopyDtosList(List<Copy> copies){
        return copies.stream()
                .map(c -> new CopyDto(c.getId(), c.getBook(), c.getInventoryNumber(), c.getBorrows()))
                .collect(Collectors.toList());
    }
}
