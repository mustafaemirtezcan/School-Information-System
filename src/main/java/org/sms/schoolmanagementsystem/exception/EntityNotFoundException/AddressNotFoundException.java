package org.sms.schoolmanagementsystem.exception.EntityNotFoundException;

import jakarta.persistence.EntityNotFoundException;

public class AddressNotFoundException extends EntityNotFoundException {
    public AddressNotFoundException(String entityName, long schoolId) {
        super(
                String.format("School with ID %d not found for entity %s", schoolId, entityName)
        );
    }
}
