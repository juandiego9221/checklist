package pe.com.jdmm21.checklistdocumental.lib.lib001;

import java.util.List;

import pe.com.jdmm21.checklistdocumental.dto.request.RequestDTO;
import pe.com.jdmm21.checklistdocumental.dto.response.DocumentTypeDTO;

public interface ChecklistInventory {

    List<DocumentTypeDTO> executeGetDocuments(RequestDTO requestDTO);

}