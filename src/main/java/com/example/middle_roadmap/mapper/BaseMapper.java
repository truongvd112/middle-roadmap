package com.example.middle_roadmap.mapper;

import java.util.List;

public interface BaseMapper<E, D> {
    E toEntity(D dto);
    D toDto(E entity);
    List<E> toEntity(List<D> dto);
    List<D> toDto(List<E> entity);
}
