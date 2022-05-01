package com.dmitrymilya.visa.shared.dto;

import com.dmitrymilya.visa.shared.model.CategoryEnum;
import com.dmitrymilya.visa.shared.model.EntryTypeEnum;
import lombok.Data;

import java.time.LocalDate;

@Data
public class VisaInfoDto {

    private EntryTypeEnum entryType;

    private CategoryEnum category;

    private LocalDate validFrom;

    private LocalDate validTo;

}
