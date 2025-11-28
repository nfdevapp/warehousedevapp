package org.example.backend.utils.enums;

public enum Status {
    NEW ("Neue Ware eingeliefert"),
    PENDING ("Bestellung angelegt"),
    SHIPPED ("Paket wurde versendet"),
    CANCELLED ("Bestellung storniert"),
    RETURNED ("Bestellung wurde zurückgesendet"),
    DELETED ("Ware gelöscht");

    private String status;
    Status(String status) {
        this.status = status;
    }

    public String getStatus() {return status;}
}
