package pe.com.jdmm21.checklistdocumental.lib.lib003;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import pe.com.jdmm21.checklistdocumental.dto.response.rules.DocumentType;
import pe.com.jdmm21.checklistdocumental.lib.lib003.mapper.Mapper;

@Component
public class CatalogInventoryImpl implements CatalogInventory {
    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogInventoryImpl.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Map<String, Object>> executeGetCatalogInformation(List<DocumentType> inputCatalogo) {
        LOGGER.info("[PCLD][PCLDR003Impl] - START");
        List<String> inputCodes = new ArrayList<>();
        inputCatalogo.forEach(listCatalog -> inputCodes.add(listCatalog.getId()));
        Map<String, Object> mapInput = Collections.singletonMap(Mapper.DOCUMENT_TYPE_ID, inputCodes);
        List<Map<String, Object>> mapOut = new ArrayList<>();
        try {
            LOGGER.info("[PCLD][PCLDR003Impl] Getting data from T_PCLD_DOCUMENT_TYPE_CATALOG");
            mapOut = jdbcTemplate.queryForList(Mapper.PCLD_GET_DATA_BY_ID_OPERATION, mapInput);
            LOGGER.info("[PCLD][PCLDR003Impl] - mapOut: " + mapOut.toString());
            return mapOut;
        } catch (RuntimeException e) {
            LOGGER.info("[PCLD][PCLDR003Impl] Error: " + e.getMessage());
            mapOut = Collections.emptyList();
            return mapOut;
        } finally {
            LOGGER.info("[PCLD][PCLDR003Impl] - END");
        }
    }

}