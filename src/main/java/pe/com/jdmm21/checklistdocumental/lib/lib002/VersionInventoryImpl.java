package pe.com.jdmm21.checklistdocumental.lib.lib002;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import pe.com.jdmm21.checklistdocumental.dto.request.DocumentProcessDTO;
import pe.com.jdmm21.checklistdocumental.lib.lib002.mapper.Mapper;

@Component
public class VersionInventoryImpl implements VersionInventory {

    private static final Logger LOGGER = LoggerFactory.getLogger(VersionInventoryImpl.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public Map<String, String> executeGetVersionInformation(DocumentProcessDTO documentProcessDTO) {
        LOGGER.info("[PCLD][PCLDR002Impl] - START");
        Date effectiveDate = documentProcessDTO.getEffectiveDate();
        LOGGER.info("[PCLD][PCLDR002Impl] request date: {}", effectiveDate);
        List<Map<String, Object>> mapOut = new ArrayList<>();

        try {
            LOGGER.info("[PCLD][PCLDR002Impl] getting data from T_PCLD_RULES_VERSION");
            mapOut = jdbcTemplate.queryForList(Mapper.PCLD_GET_DATA_VERSION);
        } catch (RuntimeException e) {
            LOGGER.error("[PCLD][PCLDR002Impl] Error: {}", e.getMessage());
            LOGGER.info("[PCLD][PCLDR002Impl] - END");
            return Collections.emptyMap();
        }

        List<String> namespaceList = new ArrayList<>();
        List<String> rulesNameList = new ArrayList<>();
        List<String> versionList = new ArrayList<>();
        List<Date> startDateList = new ArrayList<>();
        List<Date> endDateList = new ArrayList<>();
        List<String> processIdList = new ArrayList<>();

        Map<String, String> result = new HashMap<>();

        mapOut.forEach(map -> {
            namespaceList.add((String) map.get(Mapper.RULES_PACKAGE_NAME));
            rulesNameList.add((String) map.get(Mapper.RULE_NAME));
            versionList.add((String) map.get(Mapper.RULE_VERSION_NAME));
            startDateList.add((Date) map.get(Mapper.START_VALIDITY_RULE_DATE));
            endDateList.add((Date) map.get(Mapper.END_VALIDITY_RULE_DATE));
            processIdList.add((String) map.get(Mapper.PROCESS_ID));
        });

        for (int i = 0; i < mapOut.size(); i++) {
            LOGGER.info("[PCLD][PCLDR002Impl] effectiveDate: {}", effectiveDate);
            LOGGER.info("[PCLD][PCLDR002Impl] startDateList: {}", startDateList.get(i));
            LOGGER.info("[PCLD][PCLDR002Impl] endDateList: {}", endDateList.get(i));

            if ((startDateList.get(i).compareTo(effectiveDate) * effectiveDate.compareTo(endDateList.get(i)) > 0)
                    && (processIdList.get(i).equals(documentProcessDTO.getId()))) {
                LOGGER.info("[PCLD][PCLDR002Impl] it found a register with the request data");
                result.put(Mapper.RULE_NAME, rulesNameList.get(i));
                result.put(Mapper.RULE_VERSION_NAME, versionList.get(i));
                result.put(Mapper.RULES_PACKAGE_NAME, namespaceList.get(i));
                break;
            }
        }

        LOGGER.info("[PCLD][PCLDR002Impl] rulesName: {}", result.get(Mapper.RULE_NAME));
        LOGGER.info("[PCLD][PCLDR002Impl] versionRules: {}", result.get(Mapper.RULE_VERSION_NAME));
        LOGGER.info("[PCLD][PCLDR002Impl] namespace: {}", result.get(Mapper.RULES_PACKAGE_NAME));
        LOGGER.info("[PCLD][PCLDR002Impl] - END");
        return result;
    }

}