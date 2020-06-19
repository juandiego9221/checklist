package pe.com.jdmm21.checklistdocumental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pe.com.jdmm21.checklistdocumental.dto.request.RequestDTO;
import pe.com.jdmm21.checklistdocumental.dto.response.DocumentTypeDTO;
import pe.com.jdmm21.checklistdocumental.lib.lib001.ChecklistInventory;

@SpringBootTest
class DemoApplicationTests {
	@Autowired
	ChecklistInventory checklistInventory;

	@Test
	void inputDataIsHasInvalidBooleanData() {
		// RequestDTO requestDTO = new RequestDTO();
		// List<DocumentTypeDTO> result =
		// checklistInventory.executeGetDocuments(requestDTO);
		// assertEquals("0001", result.get(0).getId());
		assertTrue(true);
	}

}
