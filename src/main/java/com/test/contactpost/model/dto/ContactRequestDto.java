package com.test.contactpost.model.dto;

import javax.validation.constraints.Min;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ContactRequestDto {
    @Min(1)
    private Long id;
}
