package pe.com.jdmm21.checklistdocumental.transaction;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pe.com.jdmm21.checklistdocumental.dto.request.RequestDTO;
import pe.com.jdmm21.checklistdocumental.dto.response.DocumentTypeDTO;
import pe.com.jdmm21.checklistdocumental.lib.lib001.ChecklistInventory;

@RestController
public class Transaction001 {
    @Autowired
    ChecklistInventory checklistInventory;

    @PostMapping("/t001")
    public List<DocumentTypeDTO> execute(@RequestBody final RequestDTO requestDTO) {

        List<DocumentTypeDTO> list = checklistInventory.executeGetDocuments(requestDTO);
        return list;
    }

}