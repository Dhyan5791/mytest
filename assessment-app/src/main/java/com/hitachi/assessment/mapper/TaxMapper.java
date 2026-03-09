package com.hitachi.assessment.mapper;

import com.hitachi.assessment.dto.TaxResponseDto;
import com.hitachi.assessment.entity.Tax;

public class TaxMapper {
	public static TaxResponseDto toDto(Tax tax) {
        return new TaxResponseDto(
                tax.getTaxId(),
                tax.getTaxName(),
                tax.getRate()
        );
    }
}
