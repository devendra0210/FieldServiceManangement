package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.common.dto.PartDTO;

import java.util.List;

public interface PartService {

    PartDTO createPart(PartDTO part);

    PartDTO getPartById(Long id);

    List<PartDTO> getAllParts();

    PartDTO updatePart(Long id, PartDTO part);

    void deletePart(Long id);

}
