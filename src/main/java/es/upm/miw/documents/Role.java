package es.upm.miw.documents;

public enum Role {
    ADMIN, MANAGER, OPERATOR, CUSTOMER, ANONYMOUS, AUTHENTICATED;

    public String roleName() {
        return "ROLE_" + this.toString();
    }

}
