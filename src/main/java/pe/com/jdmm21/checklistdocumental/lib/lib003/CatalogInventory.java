package pe.com.jdmm21.checklistdocumental.lib.lib003;

import java.util.List;
import java.util.Map;

import pe.com.jdmm21.checklistdocumental.dto.response.rules.DocumentType;

public interface CatalogInventory {
    public List<Map<String, Object>> executeGetCatalogInformation(List<DocumentType> inputCatalogo);
}