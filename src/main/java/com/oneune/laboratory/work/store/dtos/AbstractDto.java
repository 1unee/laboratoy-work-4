package com.oneune.laboratory.work.store.dtos;

import com.oneune.laboratory.work.services.contracts.Identifiable;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@ToString
public abstract class AbstractDto implements Identifiable {
    Long id;
}
