package pe.com.jdmm21.checklistdocumental.lib.lib002.mapper;

public class Mapper {
    private Mapper() {

    }

    public static final String RULES_PACKAGE_NAME = "RULES_PACKAGE_NAME";
    public static final String RULE_NAME = "RULE_NAME";
    public static final String RULE_VERSION_NAME = "RULE_VERSION_NAME";
    public static final String START_VALIDITY_RULE_DATE = "START_VALIDITY_RULE_DATE";
    public static final String END_VALIDITY_RULE_DATE = "END_VALIDITY_RULE_DATE";
    public static final String PROCESS_ID = "PROCESS_ID";
    public static final String PCLD_GET_DATA_VERSION = "SELECT RULES_PACKAGE_NAME,RULE_NAME,RULE_VERSION_NAME,CASE_BUS_RULE_SERVICE_NAME,START_VALIDITY_RULE_DATE,END_VALIDITY_RULE_DATE,PROCESS_ID,CREATION_USER_ID,CREATION_DATE,USER_AUDIT_ID,AUDIT_DATE FROM T_PCLD_RULES_VERSION";

}