package pe.com.jdmm21.checklistdocumental.dto.common;

public class MetadataDTO {
    private KeyDTO key;
    private String value;

    /**
     * @return KeyDTO return the key
     */
    public KeyDTO getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(KeyDTO key) {
        this.key = key;
    }

    /**
     * @return String return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

}