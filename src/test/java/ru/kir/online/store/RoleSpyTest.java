package ru.kir.online.store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import ru.kir.online.store.models.Role;

@SpringBootTest
public class RoleSpyTest {
    @Spy
    private Role roleSpy = new Role();

    @Test
    public void roleSpyTest(){
        roleSpy.setId(5L);
        roleSpy.setName("USER");

        Mockito.verify(roleSpy).setId(5L);
        Mockito.verify(roleSpy).setName("USER");
        Assertions.assertEquals("USER", roleSpy.getName());

        Mockito.doReturn("SUPERADMIN").when(roleSpy).getName();
        Assertions.assertEquals("SUPERADMIN", roleSpy.getName());
    }

}