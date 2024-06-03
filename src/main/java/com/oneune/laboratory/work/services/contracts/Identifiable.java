package com.oneune.laboratory.work.services.contracts;

import com.oneune.laboratory.work.store.enums.SortTypeEnum;
import lombok.NonNull;

import java.io.Serializable;
import java.util.*;

import static java.util.Comparator.naturalOrder;
import static java.util.Comparator.reverseOrder;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;


public interface Identifiable extends Serializable {

   Long getId();

   static <I extends Identifiable> Optional<I> findById(final List<I> containerForSearching,
                                                         @NonNull Long targetId) {
        return containerForSearching.stream()
                .filter(item -> !Objects.isNull(item.getId()))
                .filter(item -> item.getId().equals(targetId))
                .findFirst();
    }

    private static Comparator<? super Long> getSortingOrder(SortTypeEnum sortType) {
        return switch (sortType) {
            case ASC -> naturalOrder();
            case DESC -> reverseOrder();
            default -> throw new RuntimeException("Sorting types have got only two types");
        };
    }
    static <I extends Identifiable> List<Long> extractIds(final Collection<I> identifiableItems,
                                                          SortTypeEnum sortType) {
        return identifiableItems.stream()
                .map(Identifiable::getId)
                .sorted(getSortingOrder(sortType))
                .collect(toList());
    }

    static <I extends Identifiable> boolean areAllEntitiesPosted(final Collection<I> identifiableItems) {
        final long filteredAmount = identifiableItems.stream()
                .filter(item -> nonNull(item.getId()))
                .count();
        return identifiableItems.size() == filteredAmount;
    }

    static <I extends Identifiable> Collection<I> findNonPostedEntities(final Collection<I> identifiableItems) {
        return identifiableItems.stream()
                .filter(item -> isNull(item.getId()))
                .collect(toList());
    }
}
