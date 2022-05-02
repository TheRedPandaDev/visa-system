package com.dmitrymilya.visa.shared.dto.application;

import com.dmitrymilya.visa.shared.model.CategoryEnum;
import com.dmitrymilya.visa.shared.model.EntryTypeEnum;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class VisaInfoDto {

    @NotNull
    private EntryTypeEnum entryType;

    @NotNull
    private CategoryEnum category;

    @NotNull
    private LocalDate validFrom;

    @NotNull
    private LocalDate validTo;

}
