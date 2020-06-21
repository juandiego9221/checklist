package pe.com.jdmm21.checklistdocumental.lib.lib001;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pe.com.jdmm21.checklistdocumental.dto.common.KeyDTO;
import pe.com.jdmm21.checklistdocumental.dto.common.MetadataDTO;
import pe.com.jdmm21.checklistdocumental.dto.request.RequestDTO;
import pe.com.jdmm21.checklistdocumental.dto.response.DocumentTypeDTO;
import pe.com.jdmm21.checklistdocumental.lib.lib001.mapper.Mapper;
import pe.com.jdmm21.checklistdocumental.lib.lib001.util.Utils;
import pe.com.jdmm21.checklistdocumental.lib.lib002.VersionInventory;
import pe.com.jdmm21.checklistdocumental.lib.lib003.CatalogInventory;
import pe.com.jdmm21.checklistdocumental.lib.lib004.RulesInventory;

@Component
public class ChecklistInventoryImpl implements ChecklistInventory {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChecklistInventory.class);

    @Autowired
    VersionInventory versionInventory;

    @Autowired
    RulesInventory rulesInventory;

    @Autowired
    CatalogInventory catalogInventory;

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

        resultCatalogo = catalogInventory.executeGetCatalogInformation(Utils.getInputCatalog(rulesResponseString));
        if (resultCatalogo.isEmpty()) {
            LOGGER.info("[PCLD][PCLDR001Impl] Activity 3 - Get catalog information - ERROR");
            return Collections.emptyList();
        }
        LOGGER.info("[PCLD][PCLDR001Impl] Activity 3 - Get catalog information - END");

        List<DocumentTypeDTO> finalResponse = generateResponseDocumentType(resultCatalogo, rulesResponseString);
        return finalResponse;

        // List<DocumentTypeDTO> result = new ArrayList<>();
        // DocumentTypeDTO documentTypeDTO = new DocumentTypeDTO();
        // documentTypeDTO.setId("0001");
        // result.add(documentTypeDTO);
        // return result;
    }

    private List<DocumentTypeDTO> generateResponseDocumentType(List<Map<String, Object>> resultCatalog,
            String rulesResponseString) {
        LOGGER.info("[PCLD][PCLDR001Impl] Activity 4 - Get documentation information - START");
        resultCatalog = filterResultCatalog(resultCatalog, rulesResponseString);
        DocumentTypeDTO documentTypeDTO;
        List<DocumentTypeDTO> response = new ArrayList<>();
        for (Map<String, Object> map : resultCatalog) {
            documentTypeDTO = new DocumentTypeDTO();
            documentTypeDTO.setDescription((getDescription((String) map.get(Mapper.DOCUMENT_TYPE_ID),
                    rulesResponseString, (String) map.get(Mapper.DOCUMENT_TYPE_DESC))));
            documentTypeDTO.setId((String) map.get(Mapper.DOCUMENT_TYPE_ID));
            documentTypeDTO
                    .setIsRequired(getIsRequired((String) map.get(Mapper.DOCUMENT_TYPE_ID), rulesResponseString));
            documentTypeDTO.setName((String) map.get(Mapper.DOCUMENT_TYPE_NAME));
            documentTypeDTO.setMetadata(getMetadataResponse((String) map.get(Mapper.DOCUMENT_TYPE_ID),
                    rulesResponseString, (String) map.get(Mapper.DOCUMENT_TYPE_GLOBAL_ID),
                    (String) map.get(Mapper.VALUED_DOCUMENT_IND_TYPE).toString(),
                    (String) map.get(Mapper.REUSE_IND_TYPE)));

            response.add(documentTypeDTO);
        }
        LOGGER.info("[PCLD][PCLDR001Impl] documentType: {}", response.toString());
        LOGGER.info("[PCLD][PCLDR001Impl] Activity 4 - Get documentation information - END");
        return response;
    }

    private List<Map<String, Object>> filterResultCatalog(List<Map<String, Object>> resultCatalog,
            String rulesResponseString) {
        List<Map<String, Object>> resultCatalogFiltered = resultCatalog.stream()
                .filter(x -> rulesResponseString.contains((String) x.get(Mapper.DOCUMENT_TYPE_ID)))
                .collect(Collectors.toList());
        return resultCatalogFiltered;
    }

    private String getDescription(String localDocumentTypeId, String json, String catalogDescription) {
        HashMap<String, String> descriptionValue = Utils.getJsonFinalData(json, localDocumentTypeId);
        if (descriptionValue.get(Mapper.DCHECKLIST_DESCRIPTION) != null) {
            return descriptionValue.get(Mapper.DCHECKLIST_DESCRIPTION);
        } else {
            return catalogDescription;
        }
    }

    private boolean getIsRequired(String localDocumentTypeId, String json) {
        HashMap<String, String> isRequiredValue = Utils.getJsonFinalData(json, localDocumentTypeId);
        return isRequiredValue.get(Mapper.DCHECKLIST_IS_REQUIRED).equalsIgnoreCase(Mapper.TRUE_VALUE) ? true : false;
    }

    private List<MetadataDTO> getMetadataResponse(String localDocumentTypeId, String rulesResponseString,
            String globalId, String valueInd, String reusabilityIndType) {
        HashMap<String, String> responseToProccess = Utils.getJsonFinalData(rulesResponseString, localDocumentTypeId);

        List<MetadataDTO> listmeMetadataDTOs = new ArrayList<>();
        MetadataDTO metadataStatus;
        MetadataDTO metadataGlobalId;
        MetadataDTO metadataValuedType;
        MetadataDTO metadataNewField;
        MetadataDTO metadataReusability;

        KeyDTO keyStatus;
        KeyDTO keyGlobalId;
        KeyDTO keyValuedType;
        KeyDTO keyNewField;
        KeyDTO keyReusaubility;

        metadataStatus = new MetadataDTO();
        metadataGlobalId = new MetadataDTO();
        metadataValuedType = new MetadataDTO();
        metadataReusability = new MetadataDTO();

        keyStatus = new KeyDTO();
        keyGlobalId = new KeyDTO();
        keyValuedType = new KeyDTO();
        keyReusaubility = new KeyDTO();

        // OBLIGATORIOS
        keyGlobalId.setName(Mapper.DOCUMENT_TYPE_GLOBAL_ID);
        keyGlobalId.setKeyType(Mapper.TYPE_STRING);
        keyValuedType.setName(Mapper.VALUED_DOCUMENT_IND_TYPE);
        keyValuedType.setKeyType(Mapper.TYPE_STRING);
        keyReusaubility.setName(Mapper.REUSE_IND_TYPE);
        keyReusaubility.setKeyType(Mapper.TYPE_STRING);
        metadataValuedType.setKey(keyValuedType);
        metadataValuedType.setValue(valueInd);
        metadataGlobalId.setKey(keyGlobalId);
        metadataGlobalId.setValue(globalId);
        metadataReusability.setKey(keyReusaubility);
        metadataReusability.setValue(reusabilityIndType);
        // OPCIONALES
        if (responseToProccess.get(Mapper.DCHECKLIST_IS_REQUIRED).equals(Mapper.FALSE_VALUE)) {
            keyStatus.setName(Mapper.DCHECKLIST_STATUS);
            keyStatus.setKeyType(Mapper.TYPE_BOOLEAN);
            metadataStatus.setKey(keyStatus);
            metadataStatus.setValue(responseToProccess.get(Mapper.DCHECKLIST_STATUS));
            listmeMetadataDTOs.add(metadataStatus);
        }

        Stream<Entry<String, String>> filteredList = responseToProccess.entrySet().stream()
                .filter(x -> !x.getKey().equalsIgnoreCase(Mapper.DCHECKLIST_IS_REQUIRED))
                .filter(x -> !x.getKey().equalsIgnoreCase(Mapper.DCHECKLIST_STATUS))
                .filter(x -> !x.getKey().equalsIgnoreCase(Mapper.DCHECKLIST_DESCRIPTION))
                .filter(x -> !x.getKey().equalsIgnoreCase(Mapper.DCHECKLIST_ID));
        List<Entry<String, String>> listNewFields = filteredList.collect(Collectors.toList());
        for (Entry<String, String> entry : listNewFields) {
            keyNewField = new KeyDTO();
            keyNewField.setName(entry.getKey());
            keyNewField.setKeyType(Utils.getType(entry.getValue()));
            metadataNewField = new MetadataDTO();
            metadataNewField.setKey(keyNewField);
            metadataNewField.setValue(Utils.formatValue(entry.getValue()));
            listmeMetadataDTOs.add(metadataNewField);
        }

        listmeMetadataDTOs.add(metadataValuedType);
        listmeMetadataDTOs.add(metadataGlobalId);
        listmeMetadataDTOs.add(metadataReusability);

        return listmeMetadataDTOs;

    }

}