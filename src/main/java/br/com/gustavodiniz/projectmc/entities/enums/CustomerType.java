package br.com.gustavodiniz.projectmc.entities.enums;

public enum CustomerType {

    PHYSICAL_PERSON(1, "Physical Person"),
    LEGAL_PERSON(2, "Legal Person");

    private Integer code;
    private String description;

    private CustomerType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static CustomerType toEnum(Integer code) {
        if (code == null) {
            return null;
        }

        for (CustomerType x : CustomerType.values()) {
            if (code.equals(x.getCode())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Invalid id: " + code);
    }
}
