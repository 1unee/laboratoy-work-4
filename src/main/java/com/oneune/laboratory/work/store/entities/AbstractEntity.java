package com.oneune.laboratory.work.store.entities;

import com.oneune.laboratory.work.services.contracts.Identifiable;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Embeddable
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@ToString
public abstract class AbstractEntity implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
}
