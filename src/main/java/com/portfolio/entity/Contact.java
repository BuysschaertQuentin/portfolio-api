package com.portfolio.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/**
 * Contact entity for contact information.
 */
@Entity
@Table(name = "contact")
@Getter
@Setter
@NoArgsConstructor
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Column
    private String email;

    @Column
    private String phone;

    @Column(name = "address_city")
    private String addressCity;

    @Column(name = "address_country")
    private String addressCountry;

    @Column(name = "address_zip_code")
    private String addressZipCode;
}
