package com.dmitrymilya.visa.caseresolutionservice.entity;

import com.dmitrymilya.visa.shared.entity.VisaCaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskEntity {

    private Long id;

    private Long visaCaseId;

    private VisaCaseEntity visaCase;

    private OffsetDateTime createDttm;

    private Boolean isResolved = Boolean.FALSE;

    private OffsetDateTime resolvedDttm;

    private String comment;

}
