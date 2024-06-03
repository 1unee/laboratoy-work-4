package com.oneune.laboratory.work.services.contracts;

import com.oneune.laboratory.work.store.dtos.AbstractDto;

import java.util.List;

/**
 * Common CRUD contract
 * @param <D> dto
 */
public interface CRUDable<D extends AbstractDto> {
    D post(D dto);
    D getById(Long dtoId);
    List<D> search(int page, int size);
    D put(Long dtoId, D dto);
    D deleteById(Long dtoId);
}
