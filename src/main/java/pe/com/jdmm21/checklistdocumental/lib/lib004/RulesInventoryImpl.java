package pe.com.jdmm21.checklistdocumental.lib.lib004;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import pe.com.jdmm21.checklistdocumental.dto.common.MetadataDTO;
import pe.com.jdmm21.checklistdocumental.dto.request.BranchDTO;
import pe.com.jdmm21.checklistdocumental.dto.request.ChannelDTO;
import pe.com.jdmm21.checklistdocumental.dto.request.ProductDTO;
import pe.com.jdmm21.checklistdocumental.lib.lib004.mapper.Mapper;
import pe.com.jdmm21.checklistdocumental.lib.lib004.util.Utils;

@Component
public class RulesInventoryImpl implements RulesInventory {

    private static final Logger LOGGER = LoggerFactory.getLogger(RulesInventoryImpl.class);

    @Override
    public String executeGetDocumentTypes(Map<String, String> inputVersion, ProductDTO productDTO, BranchDTO branchDTO,
            ChannelDTO channelDTO, List<MetadataDTO> metadata) {
        LOGGER.info("[PCLD][PCLDR005Impl] - START");
        String rulesResponseString;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(Mapper.HEADER_USER_ID, "headervalue");
            String jsonInput = Utils.createJsonInput(metadata, branchDTO, channelDTO, productDTO);
            final HttpEntity<String> request = new HttpEntity<>(jsonInput, headers);
            Map<String, Object> queryParams = new HashMap<String, Object>();
            queryParams.put(Mapper.PARAM_NS, inputVersion.get(Mapper.RULES_PACKAGE_NAME));
            queryParams.put(Mapper.PARAM_DECISIONS, inputVersion.get(Mapper.RULE_NAME));
            queryParams.put(Mapper.PARAM_VERSIONS, inputVersion.get(Mapper.RULE_VERSION_NAME));
            LOGGER.info("[PCLD][PCLDR005Impl] Params for url: {}", queryParams.toString());
            LOGGER.info("[PCLD][PCLDR005Impl] JSON to send {}", request.toString());
            // rulesResponseString = "";
            RestTemplate restTemplate = new RestTemplate();
            rulesResponseString = restTemplate.postForObject("http://localhost:8080/demo3/test3", request, String.class,
                    queryParams);
            JSONObject jsonStringToObject = new JSONObject(rulesResponseString);
            if (jsonStringToObject.get(Mapper.DOCUMENT_TYPES_OBJECT).toString().equals(Mapper.EMPTY_RULES_RESPONSE)) {
                LOGGER.info("[PCLD][PCLDR005Impl] Empty rules reponse");
                return Mapper.EMPTY_STRING;
            }
            LOGGER.info("[PCLD][PCLDR005Impl] JSON Output: {}", rulesResponseString);
            return rulesResponseString;
        } catch (RestClientException e) {
            LOGGER.info("[PCLD][PCLDR005Impl] Error: {}", e.getMessage());
            return null;
        } finally {
            LOGGER.info("[PCLD][PCLDR005Impl] - END");
        }
    }

}