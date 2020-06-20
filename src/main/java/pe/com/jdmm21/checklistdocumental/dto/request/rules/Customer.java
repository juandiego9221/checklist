package pe.com.jdmm21.checklistdocumental.dto.request.rules;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "pep", "fatca", "new", "newDebitCard", "jointAccount", "digitalAccountStatement" })
public class Customer {
    @JsonProperty("pep")
    private Boolean pep;
    @JsonProperty("fatca")
    private Boolean fatca;
    @JsonProperty("new")
    private Boolean _new;
    @JsonProperty("newDebitCard")
    private Boolean newDebitCard;
    @JsonProperty("jointAccount")
    private Boolean jointAccount;
    @JsonProperty("digitalAccountStatement")
    private Boolean digitalAccountStatement;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("pep")
    public Boolean getPep() {
        return pep;
    }

    @JsonProperty("pep")
    public void setPep(Boolean pep) {
        this.pep = pep;
    }

    @JsonProperty("fatca")
    public Boolean getFatca() {
        return fatca;
    }

    @JsonProperty("fatca")
    public void setFatca(Boolean fatca) {
        this.fatca = fatca;
    }

    @JsonProperty("new")
    public Boolean getNew() {
        return _new;
    }

    @JsonProperty("new")
    public void setNew(Boolean _new) {
        this._new = _new;
    }

    @JsonProperty("newDebitCard")
    public Boolean getNewDebitCard() {
        return newDebitCard;
    }

    @JsonProperty("newDebitCard")
    public void setNewDebitCard(Boolean newDebitCard) {
        this.newDebitCard = newDebitCard;
    }

    @JsonProperty("jointAccount")
    public Boolean getJointAccount() {
        return jointAccount;
    }

    @JsonProperty("jointAccount")
    public void setJointAccount(Boolean jointAccount) {
        this.jointAccount = jointAccount;
    }

    @JsonProperty("digitalAccountStatement")
    public Boolean getDigitalAccountStatement() {
        return digitalAccountStatement;
    }

    @JsonProperty("digitalAccountStatement")
    public void setDigitalAccountStatement(Boolean digitalAccountStatement) {
        this.digitalAccountStatement = digitalAccountStatement;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}