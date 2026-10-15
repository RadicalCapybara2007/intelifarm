package com.intelifarm.service;

import com.intelifarm.dto.ParcelRequestDTO;
import com.intelifarm.exception.DuplicateResourceException;
import com.intelifarm.exception.ResourceNotFoundException;
import com.intelifarm.model.Parcel;
import com.intelifarm.repository.ParcelRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParcelService {

    private final ParcelRepository parcelRepository;

    public ParcelService(ParcelRepository parcelRepository) {
        this.parcelRepository = parcelRepository;
    }

    public Parcel registerParcel(ParcelRequestDTO requestDTO) {
        // Sanitizar entradas
        requestDTO.sanitize();

        // Validar unicidad por nombre
        if (parcelRepository.existsByName(requestDTO.getName())) {
            throw new DuplicateResourceException("Ya existe una parcela registrada con el nombre: " + requestDTO.getName());
        }

        Parcel parcel = new Parcel();
        parcel.setName(requestDTO.getName());
        parcel.setLocation(requestDTO.getLocation());
        parcel.setSoilType(requestDTO.getSoilType());
        parcel.setOwnerOrLead(requestDTO.getOwnerOrLead());

        return parcelRepository.save(parcel);
    }

    public List<Parcel> getAllParcels() {
        return parcelRepository.findAll();
    }

    public Parcel getParcelById(Long id) {
        return parcelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la parcela con el ID: " + id));
    }
}