package com.huertos.controller;

import com.huertos.dto.ParcelRequestDTO;
import com.huertos.model.Parcel;
import com.huertos.service.ParcelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parcels")
public class ParcelController {

    private final ParcelService parcelService;

    public ParcelController(ParcelService parcelService) {
        this.parcelService = parcelService;
    }

    @PostMapping
    public ResponseEntity<Parcel> registerParcel(@Valid @RequestBody ParcelRequestDTO requestDTO) {
        Parcel newParcel = parcelService.registerParcel(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newParcel);
    }

    @GetMapping
    public ResponseEntity<List<Parcel>> getAllParcels() {
        return ResponseEntity.ok(parcelService.getAllParcels());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Parcel> getParcelById(@PathVariable Long id) {
        Parcel parcel = parcelService.getParcelById(id);
        return ResponseEntity.ok(parcel);
    }
}