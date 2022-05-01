package com.dmitrymilya.visa.applicationprocessingservice.entity;

import com.dmitrymilya.visa.shared.model.CategoryEnum;
import com.dmitrymilya.visa.shared.model.EntryTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VisaInfoEntity {

    private Long id;

    private EntryTypeEnum entryType;

    private CategoryEnum category;

    private LocalDate validFrom;

    private LocalDate validTo;
}
