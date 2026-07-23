package com.fieldservicemanagement.field_service_management.common.response;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse implements Serializable {

    private boolean success;

    private int status;

    private String message;

    private LocalDateTime timestamp;

    private List<Errors> errors = new ArrayList<>();
}
