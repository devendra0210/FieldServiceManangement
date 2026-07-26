package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.common.dto.PartDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PartService {

    PartDTO createPart(PartDTO part);

    PartDTO getPartById(Long id);

    Page<PartDTO> getAllParts(
            int page,
            int size,
            String sortBy,
            String sortDir);

    PartDTO updatePart(Long id, PartDTO part);

    void deletePart(Long id);

}
