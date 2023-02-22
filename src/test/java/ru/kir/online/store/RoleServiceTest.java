package ru.kir.online.store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.kir.online.store.models.Role;
import ru.kir.online.store.repositories.RoleRepository;
import ru.kir.online.store.services.RoleService;

import java.util.Optional;

@SpringBootTest
public class RoleServiceTest {
    private RoleService roleService;

    @Autowired
    private void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @MockBean
    private RoleRepository roleRepository;

    @Test
    public void findRoleByNameTest() {
        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");

        Mockito.doReturn(Optional.of(role))
                .when(roleRepository)
                .findByName("ROLE_USER");

        Role currentRole = roleService.findRoleByName("ROLE_USER");
        Assertions.assertEquals(1L, currentRole.getId());
        Assertions.assertEquals("ROLE_USER", currentRole.getName());

        Mockito.verify(roleRepository).findByName(ArgumentMatchers.eq("ROLE_USER"));
    }

}
