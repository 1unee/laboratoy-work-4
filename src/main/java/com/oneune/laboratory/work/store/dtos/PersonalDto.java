package com.oneune.laboratory.work.store.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Data
public class PersonalDto extends AbstractDto {
    String firstName;
    String lastName;
    String middleName;
}
