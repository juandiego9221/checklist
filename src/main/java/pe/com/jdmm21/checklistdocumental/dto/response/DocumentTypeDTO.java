package pe.com.jdmm21.checklistdocumental.dto.response;

import java.util.List;

import pe.com.jdmm21.checklistdocumental.dto.common.MetadataDTO;

public class DocumentTypeDTO {
    private String id;
    private String name;
    private String description;
    private Boolean isRequired;
    private List<MetadataDTO> metadata;

    /**
     * @return String return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return String return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return String return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Boolean return the isRequired
     */
    public Boolean isIsRequired() {
        return isRequired;
    }

    /**
     * @param isRequired the isRequired to set
     */
    public void setIsRequired(Boolean isRequired) {
        this.isRequired = isRequired;
    }

    /**
     * @return List<MetadataDTO> return the metadata
     */
    public List<MetadataDTO> getMetadata() {
        return metadata;
    }

    /**
     * @param metadata the metadata to set
     */
    public void setMetadata(List<MetadataDTO> metadata) {
        this.metadata = metadata;
    }

}