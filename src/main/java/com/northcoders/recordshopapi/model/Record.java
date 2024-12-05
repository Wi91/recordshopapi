package com.northcoders.recordshopapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serial;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Record {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long id;

    @Column
    String albumTitle;

    @Column
    String artistName;

    @Column
    Genre genre;

    @Column
    String dateReleased;

    @Column
    int stock;
}
