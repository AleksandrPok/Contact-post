package com.test.contactpost.controller;

import com.test.contactpost.exception.ContactNotFoundException;
import com.test.contactpost.model.dto.ContactRequestDto;
import com.test.contactpost.model.dto.ContactResponseDto;
import com.test.contactpost.service.ContactService;
import com.test.contactpost.service.mapper.ContactMapper;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/contacts")
public class ContactController {
    private final ContactService contactService;
    private final ContactMapper contactMapper;

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ContactResponseDto getById(@RequestBody @Valid ContactRequestDto contactRequestDto) {
        Long id = contactRequestDto.getId();
        return contactMapper.mapToDto(contactService.getById(id)
                .orElseThrow(() -> new ContactNotFoundException(id)));
    }
}
