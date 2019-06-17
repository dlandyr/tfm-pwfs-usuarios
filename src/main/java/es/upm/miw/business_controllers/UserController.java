package es.upm.miw.business_controllers;

import es.upm.miw.business_services.JwtService;
import es.upm.miw.documents.Role;
import es.upm.miw.documents.User;
import es.upm.miw.dtos.TokenOutputDto;
import es.upm.miw.dtos.UserDto;
import es.upm.miw.dtos.UserMinimumDto;
import es.upm.miw.exceptions.ForbiddenException;
import es.upm.miw.exceptions.NotFoundException;
import es.upm.miw.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    public TokenOutputDto login(String mobile) {
        User user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("Unexpected!!. Mobile not found:" + mobile));
        String[] roles = Arrays.stream(user.getRoles()).map(Role::name).toArray(String[]::new);
        return new TokenOutputDto(jwtService.createToken(user.getMobile(), user.getUsername(), roles));
    }

    public UserDto readUser(String mobile, String claimMobile, List<String> claimRoles) {
        User user = this.userRepository.findByMobile(mobile)
                .orElseThrow(() -> new NotFoundException("User mobile:" + mobile));
        this.authorized(claimMobile, claimRoles, mobile, Arrays.stream(user.getRoles())
                .map(Role::roleName).collect(Collectors.toList()));
        return new UserDto(user);
    }

    private void authorized(String claimMobile, List<String> claimRoles, String userMobile, List<String> userRoles) {
        if (claimRoles.contains(Role.ADMIN.roleName()) || claimMobile.equals(userMobile)) {
            return;
        }
        if (claimRoles.contains(Role.CUSTOMER.roleName())) {
            return;
        }
        throw new ForbiddenException("User mobile (" + userMobile + ")");
    }

    public List<UserMinimumDto> readAll() {
        return this.userRepository.findAllUsers();
    }
}
