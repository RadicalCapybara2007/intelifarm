package com.intelifarm.dto;

import java.util.Locale;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ParcelRequestDTO {

    @NotBlank(message = "El nombre de la parcela es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String name;

    @NotBlank(message = "La ubicación GPS es obligatoria")
    @Size(max = 100, message = "La ubicación no puede exceder los 100 caracteres")
    @Pattern(
        regexp = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?),\\s*[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$",
        message = "La ubicación debe tener un formato de coordenadas válidas (Latitud, Longitud)"
    )
    private String location;

    @NotBlank(message = "El tipo de suelo es obligatorio")
    @Pattern(regexp = "^(arcilloso|limoso|arenoso)$", message = "El tipo de suelo debe ser: arcilloso, limoso o arenoso")
    private String soilType;

    @NotBlank(message = "El nombre del responsable (ownerOrLead) es obligatorio")
    @Size(max = 100, message = "El nombre del responsable no puede exceder los 100 caracteres")
    private String ownerOrLead;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location == null ? null : location.trim();
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType == null ? null : soilType.trim().toLowerCase(Locale.ROOT);
    }

    public String getOwnerOrLead() {
        return ownerOrLead;
    }

    public void setOwnerOrLead(String ownerOrLead) {
        this.ownerOrLead = ownerOrLead == null ? null : ownerOrLead.trim();
    }
}