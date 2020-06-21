package pe.com.jdmm21.checklistdocumental;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.joda.time.DateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import pe.com.jdmm21.checklistdocumental.dto.request.DocumentProcessDTO;
import pe.com.jdmm21.checklistdocumental.lib.lib002.VersionInventory;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private VersionInventory versionInventory;

	@Test
	void inputDataIsHasInvalidBooleanData() {
		DocumentProcessDTO documentProcessDTO = new DocumentProcessDTO();
		DateTime dateTime = new DateTime("2020-03-02");
		documentProcessDTO.setEffectiveDate(dateTime.toDate());
		documentProcessDTO.setId("4.3.1.8_PE");
		Map<String, String> result = versionInventory.executeGetVersionInformation(documentProcessDTO);
		assertTrue(result.get("RULE_NAME").equalsIgnoreCase("rulename"));
	}

}
