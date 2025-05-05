package ru.otus.crm.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "addresses")
public class Address implements Cloneable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "street")
    private String street;

    public Address(Long id, String street) {
        this.id = id;
        this.street = street;
    }


    @Override
    @SuppressWarnings({"java:S2975", "java:S1182, CloneDoesntCallSuperClone"})
    public Address clone() {
        return new Address(this.id, this.street);
    }
}
