package pe.com.jdmm21.checklistdocumental.lib.lib001;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.com.jdmm21.checklistdocumental.dto.request.RequestDTO;
import pe.com.jdmm21.checklistdocumental.dto.response.DocumentTypeDTO;
import pe.com.jdmm21.checklistdocumental.lib.lib001.util.Utils;
import pe.com.jdmm21.checklistdocumental.lib.lib002.VersionInventory;

@Component
public class ChecklistInventoryImpl implements ChecklistInventory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChecklistInventory.class);

    @Autowired
    VersionInventory versionInventory;

    @Override
    public List<DocumentTypeDTO> executeGetDocuments(RequestDTO requestDTO) {
        LOGGER.info("[PCLD][PCLDR001Impl] executeGetDocuments() START");
        Map<String, String> versionInput;
        LOGGER.info("request: " + requestDTO);

        LOGGER.info("[PCLD][PCLDR001Impl] validating input metadata format");
        if (Utils.containBadTypeFormat(requestDTO.getMetadataDTO())
                || Utils.containBadBooleanFormat(requestDTO.getMetadataDTO())
                || Utils.containBadNumberFormat(requestDTO.getMetadataDTO())
                || Utils.containBadDateFormat(requestDTO.getMetadataDTO())) {
            LOGGER.info("Error with format in input metadata");
            return Collections.emptyList();
        }

        LOGGER.info("[PCLD][PCLDR001Impl] validating input metadata format with success");
        LOGGER.info("[PCLD][PCLDR001Impl] Activity 1 - Get version information - START");
        versionInput = versionInventory.executeGetVersionInformation(requestDTO.getDocumentProcessDTO());

        if (versionInput.isEmpty()) {
            LOGGER.info("[PCLD][PCLDR001Impl] Activity 1 - Get version information - ERROR");
            return Collections.emptyList();
        }
        LOGGER.info("[PCLD][PCLDR001Impl] Activity 1 - Get version information - END");

        List<DocumentTypeDTO> result = new ArrayList<>();
        DocumentTypeDTO documentTypeDTO = new DocumentTypeDTO();
        documentTypeDTO.setId("0001");
        result.add(documentTypeDTO);
        return result;
    }

}