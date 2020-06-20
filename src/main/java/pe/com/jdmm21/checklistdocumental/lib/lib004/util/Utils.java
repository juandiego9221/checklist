package pe.com.jdmm21.checklistdocumental.lib.lib004.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.json.JSONArray;
import org.json.JSONObject;

import pe.com.jdmm21.checklistdocumental.dto.common.MetadataDTO;
import pe.com.jdmm21.checklistdocumental.dto.request.BranchDTO;
import pe.com.jdmm21.checklistdocumental.dto.request.ChannelDTO;
import pe.com.jdmm21.checklistdocumental.dto.request.ProductDTO;
import pe.com.jdmm21.checklistdocumental.lib.lib004.mapper.Mapper;

public class Utils {
    private Utils() {

    }

    public static String createJsonInput(List<MetadataDTO> metadatas, BranchDTO branchDTO, ChannelDTO channelDTO,
            ProductDTO productDTO) {
        Map<String, List<MetadataDTO>> listValues = new HashMap<>();
        JSONObject jsonOutput = new JSONObject();
        JSONObject jsonBranch = new JSONObject();
        JSONObject jsonChannel = new JSONObject();
        JSONObject jsonProduct = new JSONObject();
        JSONObject jsonOtherObjects;
        Map<String, Object> otherParams = new HashMap<String, Object>();

        Map<String, Object> mapProduct = new HashMap<>();
        Map<String, Object> mapBranch = new HashMap<>();
        Map<String, Object> mapChannel = new HashMap<>();
        Map<String, Object> mapOtherObjects = new HashMap<>();

        Map<String, Object> mapNumberProduct = new HashMap<>();
        Map<String, Object> mapNumberBranch = new HashMap<>();
        Map<String, Object> mapNumberChannel = new HashMap<>();
        Map<String, Object> mapNumberOtherObjects = new HashMap<>();

        Map<String, Object> mapDateProduct = new HashMap<>();
        Map<String, Object> mapDateBranch = new HashMap<>();
        Map<String, Object> mapDateChannel = new HashMap<>();
        Map<String, Object> mapDateOtherObjects = new HashMap<>();

        if (!metadatas.isEmpty()) {
            metadatas.forEach(metadataDTO -> {
                String key = generateKey(metadataDTO.getKey().getName());
                if (listValues.containsKey(key)) {
                    List<MetadataDTO> list1 = listValues.get(key);
                    list1.add(metadataDTO);
                } else {
                    List<MetadataDTO> list2 = new ArrayList<>();
                    list2.add(metadataDTO);
                    listValues.put(key, list2);
                }
            });
        }

        if (!metadatas.isEmpty()) {
            for (String key1 : listValues.keySet()) {

                if (key1.toLowerCase().startsWith(Mapper.BRANCH)) {
                    jsonBranch = generateJson(listValues, key1);
                    mapBranch = generateJsonWithArrayStringType(listValues, key1);
                    mapNumberBranch = generateJsonWithArrayNumberType(listValues, key1);
                    mapDateBranch = generateJsonWithArrayDateType(listValues, key1);
                    jsonOutput.put(key1.toLowerCase(), jsonBranch);
                } else if (key1.toLowerCase().startsWith(Mapper.CHANNEL)) {
                    jsonChannel = generateJson(listValues, key1);
                    mapChannel = generateJsonWithArrayStringType(listValues, key1);
                    mapNumberChannel = generateJsonWithArrayNumberType(listValues, key1);
                    mapDateChannel = generateJsonWithArrayDateType(listValues, key1);
                    jsonOutput.put(key1.toLowerCase(), jsonChannel);
                } else if (key1.toLowerCase().startsWith(Mapper.PRODUCT)) {
                    jsonProduct = generateJson(listValues, key1);
                    mapProduct = generateJsonWithArrayStringType(listValues, key1);
                    mapNumberProduct = generateJsonWithArrayNumberType(listValues, key1);
                    mapDateProduct = generateJsonWithArrayDateType(listValues, key1);
                    jsonOutput.put(key1.toLowerCase(), jsonProduct);
                } else if (key1.toLowerCase().contains(Mapper.COMODIN)) {
                    otherParams = generateJsonWithOtherParams(listValues, key1);
                    jsonOutput.put((String) otherParams.get(Mapper.KEY_VALUE), otherParams.get(Mapper.JSON_VALUE));
                } else {
                    jsonOtherObjects = generateJson(listValues, key1);
                    mapOtherObjects = generateJsonWithArrayStringType(listValues, key1);
                    mapNumberOtherObjects = generateJsonWithArrayNumberType(listValues, key1);
                    mapDateOtherObjects = generateJsonWithArrayDateType(listValues, key1);
                    jsonOutput.put(key1.toLowerCase(), generateJsonOtherObjects(jsonOtherObjects, mapOtherObjects,
                            mapNumberOtherObjects, mapDateOtherObjects));
                }
            }
        }

        jsonOutput.put(Mapper.BRANCH,
                generateJsonBranch(jsonBranch, branchDTO, mapBranch, mapNumberBranch, mapDateBranch));
        jsonOutput.put(Mapper.CHANNEL,
                generateJsonChannel(jsonChannel, channelDTO, mapChannel, mapNumberChannel, mapDateChannel));
        jsonOutput.put(Mapper.PRODUCT,
                generateJsonProduct(jsonProduct, productDTO, mapProduct, mapNumberProduct, mapDateProduct));
        return jsonOutput.toString();
    }

    private static String generateKey(String keyName) {
        String key;
        if (keyName.contains(Mapper.DOT_STRING)) {
            key = keyName.substring(0, keyName.indexOf(Mapper.DOT));
            return key;
        }
        return keyName.concat(Mapper.COMODIN);
    }

    private static JSONObject generateJson(Map<String, List<MetadataDTO>> listValues, String key) {
        JSONObject jsonToReturn = new JSONObject();

        Stream<MetadataDTO> filterList = listValues.get(key).stream()
                .filter(metadata -> !metadata.getKey().getKeyType().equalsIgnoreCase(Mapper.TYPE_ARRAY_OF_DATE))
                .filter(metadata -> !metadata.getKey().getKeyType().equalsIgnoreCase(Mapper.TYPE_ARRAY_OF_NUMBER))
                .filter(metadata -> !metadata.getKey().getKeyType().equalsIgnoreCase(Mapper.TYPE_ARRAY_OF_STRING));
        filterList.forEach(metadata -> jsonToReturn.put(
                metadata.getKey().getName().substring(metadata.getKey().getName().indexOf(Mapper.DOT) + 1),
                parseValue(metadata.getValue(), metadata.getKey().getKeyType())));
        return jsonToReturn;
    }

    private static Map<String, Object> generateJsonWithOtherParams(Map<String, List<MetadataDTO>> listValues,
            String key) {
        HashMap<String, Object> response = new HashMap<>();
        Stream<MetadataDTO> filteredList = listValues.get(key).stream()
                .filter(metadata -> !metadata.getKey().getName().contains(Mapper.DOT_STRING));
        filteredList.forEach(metadata -> {
            response.put(Mapper.JSON_VALUE,
                    (metadata.getKey().getKeyType().equals(Mapper.TYPE_STRING)
                            || metadata.getKey().getKeyType().equals(Mapper.TYPE_NUMBER)
                            || metadata.getKey().getKeyType().equals(Mapper.TYPE_BOOLEAN)
                            || metadata.getKey().getKeyType().equals(Mapper.TYPE_DATE))
                                    ? parseValue(metadata.getValue(), metadata.getKey().getKeyType())
                                    : parseArray(metadata.getValue(), metadata.getKey().getKeyType()));
            response.put(Mapper.KEY_VALUE, metadata.getKey().getName());
        });
        return response;
    }

    private static Map<String, Object> generateJsonWithArrayStringType(Map<String, List<MetadataDTO>> listValues,
            String key) {
        HashMap<String, Object> response = new HashMap<>();

        JSONObject jsonToReturn = new JSONObject();
        Stream<MetadataDTO> filteredList = listValues.get(key).stream()
                .filter(metadata -> metadata.getKey().getKeyType().equalsIgnoreCase(Mapper.TYPE_ARRAY_OF_STRING));
        filteredList.forEach(metadata -> {
            jsonToReturn.put(metadata.getKey().getName().substring(metadata.getKey().getName().indexOf(Mapper.DOT) + 1),
                    generateJsonArrayWithStringValues(metadata.getValue()));
            response.put(Mapper.JSON_VALUE, jsonToReturn
                    .get(metadata.getKey().getName().substring(metadata.getKey().getName().indexOf(Mapper.DOT) + 1)));
            response.put(Mapper.KEY_VALUE,
                    metadata.getKey().getName().substring(metadata.getKey().getName().indexOf(Mapper.DOT) + 1));
        });
        return response;
    }

    private static Map<String, Object> generateJsonWithArrayNumberType(Map<String, List<MetadataDTO>> listValues,
            String key) {
        HashMap<String, Object> response = new HashMap<>();

        JSONObject jsonToReturn = new JSONObject();
        Stream<MetadataDTO> filteredList = listValues.get(key).stream()
                .filter(metadata -> metadata.getKey().getKeyType().equalsIgnoreCase(Mapper.TYPE_ARRAY_OF_NUMBER));
        filteredList.forEach(metadata -> {
            jsonToReturn.put(metadata.getKey().getName().substring(metadata.getKey().getName().indexOf(Mapper.DOT) + 1),
                    generateJsonArrayWithNumberValues(metadata.getValue()));
            response.put(Mapper.JSON_VALUE, jsonToReturn
                    .get(metadata.getKey().getName().substring(metadata.getKey().getName().indexOf(Mapper.DOT) + 1)));
            response.put(Mapper.KEY_VALUE,
                    metadata.getKey().getName().substring(metadata.getKey().getName().indexOf(Mapper.DOT) + 1));
        });
        return response;
    }

    private static Map<String, Object> generateJsonWithArrayDateType(Map<String, List<MetadataDTO>> listValues,
            String key) {
        HashMap<String, Object> response = new HashMap<>();

        JSONObject jsonToReturn = new JSONObject();
        Stream<MetadataDTO> filteredList = listValues.get(key).stream()
                .filter(metadata -> metadata.getKey().getKeyType().equalsIgnoreCase(Mapper.TYPE_ARRAY_OF_DATE));
        filteredList.forEach(metadata -> {
            jsonToReturn.put(metadata.getKey().getName().substring(metadata.getKey().getName().indexOf(Mapper.DOT) + 1),
                    generateJsonArrayWithStringValues(metadata.getValue()));
            response.put(Mapper.JSON_VALUE, jsonToReturn
                    .get(metadata.getKey().getName().substring(metadata.getKey().getName().indexOf(Mapper.DOT) + 1)));
            response.put(Mapper.KEY_VALUE,
                    metadata.getKey().getName().substring(metadata.getKey().getName().indexOf(Mapper.DOT) + 1));
        });
        return response;
    }

    private static JSONArray parseArray(String arrayValue, String type) {
        if (type.equals(Mapper.TYPE_ARRAY_OF_NUMBER)) {
            return generateJsonArrayWithNumberValues(arrayValue);
        } else {
            return generateJsonArrayWithStringValues(arrayValue);
        }
    }

    private static JSONArray generateJsonArrayWithStringValues(String arrayValue) {
        ArrayList<String> values = new ArrayList<>();
        String[] arraystring = (arrayValue).split("\\|");
        for (int i = 0; i < arraystring.length; i++) {
            values.add(arraystring[i]);
        }
        return new JSONArray(values);
    }

    private static JSONArray generateJsonArrayWithNumberValues(String arrayValue) {
        ArrayList<Integer> values = new ArrayList<>();
        String[] arraystring1 = (arrayValue).split("\\|");
        for (int i = 0; i < arraystring1.length; i++) {
            values.add(Integer.parseInt(arraystring1[i]));
        }
        return new JSONArray(values);
    }

    private static Object parseValue(String value, String type) {
        if (type.equalsIgnoreCase(Mapper.TYPE_BOOLEAN)) {
            if (value.equalsIgnoreCase(Mapper.TRUE)) {
                return true;
            } else if (value.equalsIgnoreCase(Mapper.FALSE)) {
                return false;
            }
        } else if (type.equalsIgnoreCase(Mapper.TYPE_NUMBER)) {
            return Integer.parseInt(value);
        } else if (type.equalsIgnoreCase(Mapper.TYPE_DATE)) {
            return value;
        }
        return value;
    }

    private static JSONObject generateJsonProduct(JSONObject jsonProduct, ProductDTO productDTO,
            Map<String, Object> mapProduct, Map<String, Object> mapNumberProduct, Map<String, Object> mapDateProduct) {
        jsonProduct.put(Mapper.ID, productDTO.getId());

        if (mapProduct.get(Mapper.JSON_VALUE) != null) {
            jsonProduct.put((String) mapProduct.get(Mapper.KEY_VALUE), mapProduct.get(Mapper.JSON_VALUE));
        }

        if (mapNumberProduct.get(Mapper.JSON_VALUE) != null) {
            jsonProduct.put((String) mapNumberProduct.get(Mapper.KEY_VALUE), mapNumberProduct.get(Mapper.JSON_VALUE));
        }

        if (mapDateProduct.get(Mapper.JSON_VALUE) != null) {
            jsonProduct.put((String) mapDateProduct.get(Mapper.KEY_VALUE), mapDateProduct.get(Mapper.JSON_VALUE));
        }
        return jsonProduct;
    }

    private static JSONObject generateJsonBranch(JSONObject jsonBranch, BranchDTO branchDTO,
            Map<String, Object> mapBranch, Map<String, Object> mapNumberBranch, Map<String, Object> mapDateBranch) {
        jsonBranch.put(Mapper.ID, branchDTO.getId());
        if (mapBranch.get(Mapper.JSON_VALUE) != null) {
            jsonBranch.put((String) mapBranch.get(Mapper.KEY_VALUE), mapBranch.get(Mapper.JSON_VALUE));
        }

        if (mapNumberBranch.get(Mapper.JSON_VALUE) != null) {
            jsonBranch.put((String) mapNumberBranch.get(Mapper.KEY_VALUE), mapNumberBranch.get(Mapper.JSON_VALUE));
        }

        if (mapDateBranch.get(Mapper.JSON_VALUE) != null) {
            jsonBranch.put((String) mapDateBranch.get(Mapper.KEY_VALUE), mapDateBranch.get(Mapper.JSON_VALUE));
        }
        return jsonBranch;
    }

    private static JSONObject generateJsonChannel(JSONObject jsonChannel, ChannelDTO channelDTO,
            Map<String, Object> mapChannel, Map<String, Object> mapNumberChannel, Map<String, Object> mapDateChannel) {
        jsonChannel.put(Mapper.ID, channelDTO.getId());
        if (mapChannel.get(Mapper.JSON_VALUE) != null) {
            jsonChannel.put((String) mapChannel.get(Mapper.KEY_VALUE), mapChannel.get(Mapper.JSON_VALUE));
        }

        if (mapNumberChannel.get(Mapper.JSON_VALUE) != null) {
            jsonChannel.put((String) mapNumberChannel.get(Mapper.KEY_VALUE), mapNumberChannel.get(Mapper.JSON_VALUE));
        }

        if (mapDateChannel.get(Mapper.JSON_VALUE) != null) {
            jsonChannel.put((String) mapDateChannel.get(Mapper.KEY_VALUE), mapDateChannel.get(Mapper.JSON_VALUE));
        }
        return jsonChannel;
    }

    private static JSONObject generateJsonOtherObjects(JSONObject jsonOtherObjects, Map<String, Object> mapOtherObjects,
            Map<String, Object> mapNumberOtherObjects, Map<String, Object> mapDateOtherObjects) {

        if (mapOtherObjects.get(Mapper.JSON_VALUE) != null) {
            jsonOtherObjects.put((String) mapOtherObjects.get(Mapper.KEY_VALUE),
                    mapOtherObjects.get(Mapper.JSON_VALUE));
        }

        if (mapNumberOtherObjects.get(Mapper.JSON_VALUE) != null) {
            jsonOtherObjects.put((String) mapNumberOtherObjects.get(Mapper.KEY_VALUE),
                    mapNumberOtherObjects.get(Mapper.JSON_VALUE));
        }

        if (mapDateOtherObjects.get(Mapper.JSON_VALUE) != null) {
            jsonOtherObjects.put((String) mapDateOtherObjects.get(Mapper.KEY_VALUE),
                    mapDateOtherObjects.get(Mapper.JSON_VALUE));
        }

        return jsonOtherObjects;
    }
}