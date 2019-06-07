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

    public UserDto(User user) {
        super(user.getMobile(), user.getUsername());
        this.email = user.getEmail();
        this.dni = user.getDni();
        this.address = user.getAddress();
        this.active = user.isActive();
        this.roles = user.getRoles();
        this.registrationDate = user.getRegistrationDate();
    }

    public String getEmail() {
        return email;
    }

    public String getDni() {
        return dni;
    }

    public String getAddress() {
        return address;
    }

    public Boolean isActive() {
        return active;
    }

    public LocalDateTime getRegistrationDate() {
        return this.registrationDate;
    }

    public Role[] getRoles() {
        return this.roles;
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
