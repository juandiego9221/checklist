package pe.com.jdmm21.checklistdocumental.dto.request;

import java.util.Date;

public class DocumentProcessDTO {
    private String id;
	private String number;
	private Date effectiveDate;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Date getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
}