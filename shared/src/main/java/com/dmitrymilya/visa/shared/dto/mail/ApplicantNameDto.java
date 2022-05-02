package com.dmitrymilya.visa.shared.dto.mail;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ApplicantNameDto {

    @NotNull
    private String lastName;

    @NotNull
    private String firstName;

    private String middleName;

    @JsonIgnore
    public String getFio() {
        return lastName + " " + firstName + (StringUtils.isBlank(middleName) ? "" : " " + middleName);
    }


}
