package pe.com.jdmm21.checklistdocumental.dto.request;

import pe.com.jdmm21.checklistdocumental.dto.common.CommonDTO;

public class ProductDTO implements CommonDTO {
	private String id;
	private SubproductDTO subproduct;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SubproductDTO getSubproduct() {
		return subproduct;
	}

	public void setSubproduct(SubproductDTO subproduct) {
		this.subproduct = subproduct;
	}
}