package com.dmitrymilya.visa.applicationprocessingservice.entity;

import com.dmitrymilya.visa.shared.model.DecisionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskEntity {

    private Long id;

    private Long visaApplicationId;

    private VisaApplicationEntity visaApplication;

    private OffsetDateTime createDttm;

    private DecisionEnum decision;

}
