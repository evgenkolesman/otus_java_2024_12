package ru.otus.crm.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "phones")
public class Phone implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    public Phone(Long id, String number) {
        this.id = id;
        this.number = number;
    }


    @Override
    @SuppressWarnings({"java:S2975", "java:S1182, CloneDoesntCallSuperClone"})
    public Phone clone() {
        val clientFull = client == null ? null : client.clone();
        return new Phone(this.id, this.number, clientFull);
    }

}
