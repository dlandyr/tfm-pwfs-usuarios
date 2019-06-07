package es.upm.miw.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserMinimumDto {

    @NotNull
    @Pattern(regexp = es.upm.miw.dtos.validations.Pattern.NINE_DIGITS)
    private String mobile;

    @NotNull
    private String username;

    public UserMinimumDto() {
    }

    public UserMinimumDto(String mobile, String username) {
        this.mobile = mobile;
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserMinimumDto [mobile=" + mobile + ", username=" + username + "]";
    }
}
