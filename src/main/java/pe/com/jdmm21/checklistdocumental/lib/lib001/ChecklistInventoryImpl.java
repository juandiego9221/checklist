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
import pe.com.jdmm21.checklistdocumental.lib.lib001.mapper.Mapper;
import pe.com.jdmm21.checklistdocumental.lib.lib001.util.Utils;
import pe.com.jdmm21.checklistdocumental.lib.lib002.VersionInventory;
import pe.com.jdmm21.checklistdocumental.lib.lib004.RulesInventory;

@Component
public class ChecklistInventoryImpl implements ChecklistInventory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChecklistInventory.class);

    @Autowired
    VersionInventory versionInventory;

    @Autowired
    RulesInventory rulesInventory;

    @Override
    public List<DocumentTypeDTO> executeGetDocuments(RequestDTO requestDTO) {
        LOGGER.info("[PCLD][PCLDR001Impl] executeGetDocuments() START");
        Map<String, String> versionInput;
        LOGGER.info("request: " + requestDTO);

        LOGGER.info("[PCLD][PCLDR001Impl] validating input metadata format");
        if (Utils.containBadTypeFormat(requestDTO.getMetadata())
                || Utils.containBadBooleanFormat(requestDTO.getMetadata())
                || Utils.containBadNumberFormat(requestDTO.getMetadata())
                || Utils.containBadDateFormat(requestDTO.getMetadata())) {
            LOGGER.info("Error with format in input metadata");
            return Collections.emptyList();
        }

        LOGGER.info("[PCLD][PCLDR001Impl] validating input metadata format with success");
        LOGGER.info("[PCLD][PCLDR001Impl] Activity 1 - Get version information - START");
        versionInput = versionInventory.executeGetVersionInformation(requestDTO.getDocumentProcess());

        if (versionInput.isEmpty()) {
            LOGGER.info("[PCLD][PCLDR001Impl] Activity 1 - Get version information - ERROR");
            return Collections.emptyList();
        }
        LOGGER.info("[PCLD][PCLDR001Impl] Activity 1 - Get version information - END");

        LOGGER.info("[PCLD][PCLDR001Impl] Activity 2 - Get service rules information - START");
        String rulesResponseString = rulesInventory.executeGetDocumentTypes(versionInput, requestDTO.getProduct(),
                requestDTO.getBranch(), requestDTO.getChannel(), requestDTO.getMetadata());

        if (rulesResponseString == null) {
            LOGGER.info("[PCLD][PCLDR001Impl] Activity 2 - Get service rules information - ERROR");
            return Collections.emptyList();
        }

        if (rulesResponseString.equals(Mapper.EMPTY_STRING)) {
            List<DocumentTypeDTO> finalResponse = new ArrayList<>();
            LOGGER.info("[PCLD][PCLDR001Impl] executeGetDocuments() END");
            return finalResponse;
        }

        LOGGER.info("[PCLD][PCLDR001Impl] Activity 2 - Get service rules information - END");

        LOGGER.info("[PCLD][PCLDR001Impl] Activity 3 - Get catalog information - START");
        List<Map<String, Object>> resultCatalogo;

        List<DocumentTypeDTO> result = new ArrayList<>();
        DocumentTypeDTO documentTypeDTO = new DocumentTypeDTO();
        documentTypeDTO.setId("0001");
        result.add(documentTypeDTO);
        return result;
    }

}