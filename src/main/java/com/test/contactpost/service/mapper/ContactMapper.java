package com.test.contactpost.service.mapper;

import com.test.contactpost.model.Contact;
import com.test.contactpost.model.dto.ContactResponseDto;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {
    public ContactResponseDto mapToDto(Contact contact) {
        ContactResponseDto contactResponseDto = new ContactResponseDto();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(contact.getFirstName()).append(" ").append(contact.getMiddleName())
                .append(" ").append(contact.getSurname());
        contactResponseDto.setFullName(stringBuilder.toString());
        return contactResponseDto;
    }
}
