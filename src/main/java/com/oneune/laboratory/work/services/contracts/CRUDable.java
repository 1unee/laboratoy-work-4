package com.oneune.laboratory.work.services.contracts;

import com.oneune.laboratory.work.store.dtos.AbstractDto;
import com.oneune.laboratory.work.store.dtos.UserDto;
import java.util.List;


/**
 * Common CRUD contract
 * @param <D> dto
 */
public interface CRUDable<D extends AbstractDto> {
    D post(D dto);
    D getById(Long dtoId);
    List<UserDto> search(int page, int size);
    D put(Long dtoId, D dto);
    D deleteById(Long dtoId);
}
