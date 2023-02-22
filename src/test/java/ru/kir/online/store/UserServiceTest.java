package ru.kir.online.store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.kir.online.store.dtos.UserRegisterDto;
import ru.kir.online.store.models.*;
import ru.kir.online.store.repositories.UserRepository;
import ru.kir.online.store.services.UserService;

import java.util.*;

@SpringBootTest
public class UserServiceTest {
    private UserService userService;
    private User testUser;

    @Autowired
    private void setUserService(UserService userService) {
        this.userService = userService;
    }

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        User user = new User();
        user.setId(3L);
        user.setUsername("TestUser");
        user.setPassword("200");
        user.setEmail("test_user@mail.ru");

        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");
        user.setRoles(Collections.singletonList(role));
        testUser = user;
    }

    @Test
    public void findByUsernameTest() {
        Mockito.doReturn(Optional.of(testUser))
                .when(userRepository)
                .findByUsername("TestUser");

        Optional<User> optionalUser = userService.findByUsername("TestUser");
        Assertions.assertTrue(optionalUser.isPresent());
        Assertions.assertEquals("TestUser", optionalUser.get().getUsername());
        Assertions.assertEquals("200", optionalUser.get().getPassword());
        Assertions.assertEquals("test_user@mail.ru", optionalUser.get().getEmail());

        Assertions.assertEquals("ROLE_USER", ((List<Role>)optionalUser.get()
                .getRoles())
                .get(0)
                .getName());


        Mockito.verify(userRepository).findByUsername(ArgumentMatchers.eq("TestUser"));
    }

    @Test
    public void createNewUserWithRoleTest() {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setUsername("TestUser");
        userRegisterDto.setPassword("200");
        userRegisterDto.setEmail("test_user@mail.ru");

        userService.createNewUserWithRole(userRegisterDto);

        ArgumentCaptor<User> argumentCaptor = ArgumentCaptor.forClass(User.class);
        Mockito.verify(userRepository).save(argumentCaptor.capture());

        Assertions.assertEquals("TestUser", argumentCaptor.getValue().getUsername());
        Assertions.assertEquals("200", argumentCaptor.getValue().getPassword());
        Assertions.assertEquals("test_user@mail.ru", argumentCaptor.getValue().getEmail());

        Assertions.assertEquals("ROLE_USER", ((List<Role>) argumentCaptor.getValue()
                .getRoles())
                .get(0)
                .getName());
    }

    @Test
    public void loadUserByUsernameTest() {
        Mockito.doReturn(Optional.of(testUser))
                .when(userRepository)
                .findByUsername("TestUser");

        UserDetails userDetails = userService.loadUserByUsername("TestUser");
        Assertions.assertTrue(userDetails.isEnabled());
        Assertions.assertEquals("TestUser", userDetails.getUsername());
        Assertions.assertEquals("200", userDetails.getPassword());
        Assertions.assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER")));

        Mockito.verify(userRepository).findByUsername(ArgumentMatchers.eq("TestUser"));
    }

}
