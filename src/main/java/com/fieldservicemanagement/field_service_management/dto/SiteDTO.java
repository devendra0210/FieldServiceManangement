package com.fieldservicemanagement.field_service_management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiteDTO {

    private Long id;

    @NotNull(message = "Customer Id is required")
    @Min(value = 1, message = "Customer Id must be greater than 0")
    private Long customerId;

    @NotBlank(message = "Site name is required")
    @Size(min = 2, max = 100, message = "Site name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 255, message = "Address must be between 5 and 255 characters")
    private String address;

}