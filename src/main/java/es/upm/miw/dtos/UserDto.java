package es.upm.miw.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import es.upm.miw.documents.Role;
import es.upm.miw.documents.User;

import java.time.LocalDateTime;
import java.util.Arrays;

@JsonInclude(Include.NON_NULL)
public class UserDto extends UserMinimumDto {

    private String email;
    private String dni;
    private String address;
    private Boolean active;
    private Role[] roles;

    private LocalDateTime registrationDate;

    public UserDto() {
        // Empty for framework
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "mobile='" + this.getMobile() + '\'' +
                ", username='" + this.getUsername() + '\'' +
                ", email='" + email + '\'' +
                ", dni='" + dni + '\'' +
                ", address='" + address + '\'' +
                ", active=" + active +
                ", roles=" + Arrays.toString(roles) +
                ", registrationDate=" + registrationDate +
                '}';
    }
}
