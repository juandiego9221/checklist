package pe.com.jdmm21.checklistdocumental.lib.lib002;

import java.util.Map;

import pe.com.jdmm21.checklistdocumental.dto.request.DocumentProcessDTO;

public interface VersionInventory {
    public Map<String, String> executeGetVersionInformation(DocumentProcessDTO documentProcessDTO);

}