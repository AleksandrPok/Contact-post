package com.test.contactpost.service;

import com.test.contactpost.model.Contact;
import java.util.Optional;

public interface ContactService {
    Optional<Contact> getById(Long id);
}
