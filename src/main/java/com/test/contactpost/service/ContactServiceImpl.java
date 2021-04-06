package com.test.contactpost.service;

import com.test.contactpost.model.Contact;
import com.test.contactpost.repository.ContactRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;

    @Override
    public Optional<Contact> getById(Long id) {
        return contactRepository.findById(id);
    }
}
