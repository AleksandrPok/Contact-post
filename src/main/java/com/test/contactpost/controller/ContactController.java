package com.test.contactpost.controller;

import com.test.contactpost.exception.ContactNotFoundException;
import com.test.contactpost.model.dto.ContactResponseDto;
import com.test.contactpost.service.ContactService;
import com.test.contactpost.service.mapper.ContactMapper;
import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;
    private final ContactMapper contactMapper;

    @PostMapping()
    ContactResponseDto getById(@RequestParam("id") @Min(1) Long id) {
        return contactMapper.mapToDto(contactService.getById(id)
                .orElseThrow(() -> new ContactNotFoundException(id)));
    }
}
