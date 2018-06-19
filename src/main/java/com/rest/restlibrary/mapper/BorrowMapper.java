package com.rest.restlibrary.mapper;

import com.rest.restlibrary.data.Borrow;
import com.rest.restlibrary.domain.BorrowDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BorrowMapper {
    //map dto to borrow(without datas)
    //map borrow to dto (with data)
    //map to list borrowDtos(with data)

    public Borrow mapToBorrow(BorrowDto borrowDto){
        return new Borrow(borrowDto.getReader(), borrowDto.getCopy());
    }

    public BorrowDto mapToBorrowDto(Borrow borrow){
        return new BorrowDto(borrow.getFromDate(), borrow.getUntilDate(), borrow.getCopy(), borrow.getReader());
    }

    public List<BorrowDto> mapToBorrowDtoList(List<Borrow> borrows){
        return borrows.stream()
                .map(b -> new BorrowDto(b.getFromDate(), b.getUntilDate(), b.getCopy(), b.getReader()))
                .collect(Collectors.toList());
    }
}
