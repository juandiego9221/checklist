package pe.com.jdmm21.checklistdocumental.lib.lib001.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Iterator;

import org.json.JSONObject;
import org.json.JSONArray;

import pe.com.jdmm21.checklistdocumental.dto.common.MetadataDTO;
import pe.com.jdmm21.checklistdocumental.dto.response.rules.DocumentType;
import pe.com.jdmm21.checklistdocumental.lib.lib001.mapper.Mapper;

public class Utils {
    private Utils() {

    }

    static List<String> listOfKeys;
    static List<String> listOfValues;

    public static boolean containBadTypeFormat(List<MetadataDTO> metadataDTO) {
        long containBadTypeFormat = metadataDTO.stream()
                .filter(x -> !x.getKey().getKeyType().equalsIgnoreCase(Mapper.TYPE_STRING))
                .filter(x -> !x.getKey().getKeyType().equalsIgnoreCase(Mapper.TYPE_BOOLEAN))
                .filter(x -> !x.getKey().getKeyType().equalsIgnoreCase(Mapper.TYPE_NUMBER))
                .filter(x -> !x.getKey().getKeyType().equalsIgnoreCase(Mapper.TYPE_ARRAY_OF_DATE))
                .filter(x -> !x.getKey().getKeyType().equalsIgnoreCase(Mapper.TYPE_ARRAY_OF_NUMBER))
                .filter(x -> !x.getKey().getKeyType().equalsIgnoreCase(Mapper.TYPE_ARRAY_OF_STRING)).count();
        return containBadTypeFormat > 0 ? true : false;
    }

    public static boolean containBadBooleanFormat(List<MetadataDTO> metadataDTO) {
        long containBadBooleanFormat = metadataDTO.stream()
                .filter(x -> x.getKey().getKeyType().equalsIgnoreCase(Mapper.TYPE_BOOLEAN))
                .filter(x -> !x.getValue().equalsIgnoreCase(Mapper.TRUE_VALUE))
                .filter(x -> !x.getValue().equalsIgnoreCase(Mapper.FALSE_VALUE)).count();
        return containBadBooleanFormat > 0 ? true : false;
    }

    public static boolean containBadNameFieldFormat(List<MetadataDTO> metadatas) {
        long containBadFormat = metadatas.stream().filter(x -> !x.getKey().getName().contains(Mapper.DOT)).count();
        return containBadFormat > 0 ? true : false;
    }

    public static boolean containBadDateFormat(List<MetadataDTO> metadatas) {
        Pattern datePattern = Pattern.compile(Mapper.DATE_MATCHER);
        Stream<MetadataDTO> streamDate = metadatas.stream()
                .filter(x -> x.getKey().getKeyType().equalsIgnoreCase(Mapper.TYPE_DATE));
        List<MetadataDTO> listDate = streamDate.collect(Collectors.toList());
        Matcher matcher;
        for (MetadataDTO metadataDTOs : listDate) {
            matcher = datePattern.matcher(metadataDTOs.getValue());
            if (!matcher.matches()) {
                return true;
            }
        }
        return false;
    }

    public static boolean containBadNumberFormat(List<MetadataDTO> metadatas) {
        Stream<MetadataDTO> streamNumber = metadatas.stream()
                .filter(x -> x.getKey().getKeyType().equalsIgnoreCase(Mapper.TYPE_NUMBER));
        List<MetadataDTO> listNumber = streamNumber.collect(Collectors.toList());
        for (MetadataDTO metadataDTOs : listNumber) {
            try {
                Integer.parseInt(metadataDTOs.getValue());
            } catch (NumberFormatException e) {
                return true;
            }
        }
        return false;
    }

    public static String getType(String value) {
        if (value.startsWith("[") && value.endsWith("]")) {
            String formatedValue = value.substring(1).substring(0, value.length() - 2).replace(',', '|');
            String[] arraystring = (formatedValue).split("\\|");
            return generateArrayType(arraystring[0]);
        } else {
            return generateType(value);
        }

    }

    public static String generateArrayType(String value) {
        Pattern datePattern = Pattern.compile(Mapper.DATE_MATCHER);
        try {
            if (Integer.parseInt(value) >= 0) {
                return Mapper.TYPE_ARRAY_OF_NUMBER;
            }
        } catch (NumberFormatException e) {
            value = value.replace(Mapper.QUOTATION, Mapper.EMPTY_STRING);
            if (datePattern.matcher(value).matches()) {
                return Mapper.TYPE_ARRAY_OF_DATE;
            }
            return Mapper.TYPE_ARRAY_OF_STRING;
        }
        return Mapper.TYPE_STRING;
    }

    public static String generateType(String value) {
        Pattern datePattern = Pattern.compile(Mapper.DATE_MATCHER);
        if (value.equalsIgnoreCase(Mapper.TRUE_VALUE) || value.equalsIgnoreCase(Mapper.FALSE_VALUE)) {
            return Mapper.TYPE_BOOLEAN;
        }
        try {
            if (Integer.parseInt(value) >= 0) {
                return Mapper.TYPE_NUMBER;
            }
        } catch (NumberFormatException e) {
            if (datePattern.matcher(value).matches()) {
                return Mapper.TYPE_DATE;
            }
            return Mapper.TYPE_STRING;
        }
        return Mapper.TYPE_STRING;
    }

    public static HashMap<String, String> getMapContentById(String rulesResponseString) {
        JSONObject jsonToProcess = new JSONObject(rulesResponseString);
        List<String> idList = new ArrayList<>();
        List<String> contentList = new ArrayList<>();
        HashMap<String, String> mapContentById = new HashMap<>();
        JSONArray array = jsonToProcess.getJSONArray(Mapper.DCHECKLIST_OBJECT);
        for (int i = 0; i < array.length(); i++) {
            idList.add(array.getJSONObject(i).getString(Mapper.ID_FIELD));
            contentList.add(array.getJSONObject(i).toString());
        }
        Iterator<String> i1 = idList.iterator();
        Iterator<String> i2 = contentList.iterator();

        while (i1.hasNext() && i2.hasNext()) {
            mapContentById.put(i1.next(), i2.next());
        }
        return mapContentById;
    }

    public static HashMap<String, String> getResponseMap(Iterator<String> i3, Iterator<String> i4) {
        HashMap<String, String> responseMap = new HashMap<>();
        while (i3.hasNext() && i4.hasNext()) {
            responseMap.put(i3.next(), i4.next());
        }
        return responseMap;

    }

    public static HashMap<String, String> getJsonFinalData(String rulesResponseString, String idLocal) {

        HashMap<String, String> mapContentById = new HashMap<>();
        mapContentById = getMapContentById(rulesResponseString);

        String jsonValueById;
        for (String keyId : mapContentById.keySet()) {
            if (keyId.equals(idLocal)) {
                jsonValueById = mapContentById.get(keyId);
                JSONObject jsonObject = new JSONObject(jsonValueById);
                Iterator<String> keys = jsonObject.keys();
                generateLists(keys, jsonObject);
                Iterator<String> i3 = listOfKeys.iterator();
                Iterator<String> i4 = listOfValues.iterator();
                HashMap<String, String> responseMap = new HashMap<String, String>();
                responseMap = getResponseMap(i3, i4);
                return responseMap;
            }
        }
        return mapContentById;
    }

    public static void generateLists(Iterator<String> keys, JSONObject jsonObject) {
        listOfKeys = new ArrayList<>();
        listOfValues = new ArrayList<>();
        while (keys.hasNext()) {
            String key = keys.next();
            if (jsonObject.get(key) instanceof JSONObject) {
                JSONObject jsonObject2 = new JSONObject(jsonObject.get(key).toString());
                Iterator<String> secondLevelKeys = jsonObject2.keys();
                while (secondLevelKeys.hasNext()) {
                    String key2 = secondLevelKeys.next();
                    listOfKeys.add(Mapper.DCHECKLIST_OBJECT_DOT + key + Mapper.DOT + key2);
                    listOfValues.add(jsonObject.get(key).toString());
                }
            } else {
                listOfKeys.add(Mapper.DCHECKLIST_OBJECT_DOT + key);
                listOfValues.add(jsonObject.get(key).toString());
            }
        }
    }

    public static List<DocumentType> getInputCatalog(String rulesResponseString) {
        List<DocumentType> inputCatalogo = new ArrayList<>();
        DocumentType documentType;
        JSONObject jsonStringToObject = new JSONObject(rulesResponseString);
        JSONArray array = jsonStringToObject.getJSONArray(Mapper.DCHECKLIST_OBJECT);
        for (int i = 0; i < array.length(); i++) {
            documentType = new DocumentType();
            documentType.setId(array.getJSONObject(i).getString(Mapper.ID_FIELD));
            inputCatalogo.add(documentType);
        }
        return inputCatalogo;
    }

    public static String formatValue(String value) {
        String result = Mapper.EMPTY_STRING;
        if (value.startsWith("[") && value.endsWith("]")) {
            String formatedValue = value.substring(1).substring(0, value.length() - 2).replace(',', '|');
            String[] arraystring = (formatedValue).split("\\|");
            if (!arraystring[0].equals(Mapper.EMPTY_STRING)) {
                result = formatedValue.replace(Mapper.QUOTATION, Mapper.EMPTY_STRING);
            }
        } else {
            return value;
        }
        return result;
    }

}