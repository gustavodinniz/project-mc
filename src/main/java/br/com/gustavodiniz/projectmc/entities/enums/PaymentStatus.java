package br.com.gustavodiniz.projectmc.entities.enums;

public enum PaymentStatus {

    PENDING(1, "Pendente"),
    SETTLED(2, "Quitado"),
    CANCEL(3, "Cancelado");

    private Integer code;
    private String description;

    private PaymentStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static PaymentStatus toEnum(Integer code) {
        if (code == null) {
            return null;
        }

        for (PaymentStatus x : PaymentStatus.values()) {
            if (code.equals(x.getCode())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Invalid id: " + code);
    }
}
