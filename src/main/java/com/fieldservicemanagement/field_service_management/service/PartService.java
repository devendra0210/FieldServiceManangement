package com.fieldservicemanagement.field_service_management.service;

import com.fieldservicemanagement.field_service_management.common.dto.PartDTO;
import com.fieldservicemanagement.field_service_management.common.response.PageResponse;
import java.util.List;

public interface PartService {

    PartDTO createPart(PartDTO part);

    PartDTO getPartById(Long id);

    PageResponse<PartDTO> getPage(int page, int size, String name, String sku);

    PartDTO updatePart(Long id, PartDTO part);

    void deletePart(Long id);

}
