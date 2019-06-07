package es.upm.miw.rest_controllers;

import es.upm.miw.business_controllers.UserController;
import es.upm.miw.dtos.*;
import es.upm.miw.dtos.TokenOutputDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('OPERATOR')")
@RestController
@RequestMapping(UserResource.USERS)
public class UserResource {

    public static final String USERS = "/users";
    public static final String TOKEN = "/token";
    public static final String MOBILE_ID = "/{mobile}";

    @Autowired
    private UserController userController;

    @PreAuthorize("authenticated")
    @PostMapping(value = TOKEN)
    public TokenOutputDto login(@AuthenticationPrincipal User activeUser) {
        return userController.login(activeUser.getUsername());
    }

    @GetMapping(value = MOBILE_ID)
    public UserDto read(@PathVariable String mobile, @AuthenticationPrincipal User activeUser) {
        return this.userController.readUser(mobile, SecurityContextHolder.getContext().getAuthentication().getName(),
                SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
    }

    @GetMapping
    public List<UserMinimumDto> readAll() {
        return this.userController.readAll();
    }
}