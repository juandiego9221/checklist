package pe.com.jdmm21.checklistdocumental.lib.lib001;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import pe.com.jdmm21.checklistdocumental.dto.request.RequestDTO;
import pe.com.jdmm21.checklistdocumental.dto.response.DocumentTypeDTO;
import pe.com.jdmm21.checklistdocumental.lib.lib001.util.Utils;

@Component
public class ChecklistInventoryImpl implements ChecklistInventory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChecklistInventory.class);

    @Override
    public List<DocumentTypeDTO> executeGetDocuments(RequestDTO requestDTO) {
        LOGGER.info("[PCLD][PCLDR001Impl] executeGetDocuments() START");
        LOGGER.info("request: " + requestDTO);

        LOGGER.info("[PCLD][PCLDR001Impl] validating input metadata format");
        if (Utils.containBadTypeFormat(requestDTO.getMetadataDTO())||Utils.containBadBooleanFormat(requestDTO.getMetadataDTO())) {
            return Collections.emptyList();
        }

        List<DocumentTypeDTO> result = new ArrayList<>();
        DocumentTypeDTO documentTypeDTO = new DocumentTypeDTO();
        documentTypeDTO.setId("0001");
        result.add(documentTypeDTO);
        return result;
    }

}