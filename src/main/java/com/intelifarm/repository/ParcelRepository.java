package com.intelifarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.intelifarm.model.Parcel;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    boolean existsByName(String name);
    boolean existsByNameIgnoreCase(String name);
}