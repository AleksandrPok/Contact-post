package com.test.contactpost.exception;

public class ContactNotFoundException extends RuntimeException {

    public ContactNotFoundException(Long id) {
        super("Contact not found, id : " + id);
    }
}
