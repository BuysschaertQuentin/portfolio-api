package com.portfolio.datamapper;

import org.springframework.stereotype.Component;

import com.portfolio.dto.contact.ContactResponseDTO;
import com.portfolio.entity.Contact;

/**
 * Mapper for Contact entity <-> DTO conversions.
 */
@Component
public class ContactMapper {

    /**
     * Converts Contact entity to ContactResponseDTO.
     *
     * @param contact the contact entity
     * @return the contact response DTO
     */
    public ContactResponseDTO toResponseDTO(Contact contact) {
        if (contact == null) {
            return null;
        }

        return ContactResponseDTO.builder()
                .id(contact.getId())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .addressCity(contact.getAddressCity())
                .addressCountry(contact.getAddressCountry())
                .addressZipCode(contact.getAddressZipCode())
                .build();
    }
}
