package com.oneune.laboratory.work.store.dtos;

import com.oneune.laboratory.work.services.contracts.Identifiable;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public abstract class AbstractDto implements Identifiable {
    @Setter(AccessLevel.PRIVATE)
    Long id;
}
