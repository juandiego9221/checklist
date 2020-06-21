package pe.com.jdmm21.checklistdocumental.dto.request.rules;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import pe.com.jdmm21.checklistdocumental.dto.common.CommonDTO;

@JsonPropertyOrder({ "id" })
public class Branch implements CommonDTO {
    @JsonProperty("id")
    private String id;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @Override
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @Override
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Branch [id=" + id + ", additionalProperties=" + additionalProperties + "]";
    }

}