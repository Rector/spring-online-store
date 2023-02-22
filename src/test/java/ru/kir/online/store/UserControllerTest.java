package ru.kir.online.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.kir.online.store.models.User;
import ru.kir.online.store.services.UserService;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    public void registerTest(){
        User user = new User();
        user.setId(3L);
        user.setUsername("TestUser");
        user.setPassword("200");
        user.setEmail("test_user@mail.ru");

        Optional<User> optionalUser = Optional.of(user);

        given(userService.findByUsername("TestUser")).willReturn(optionalUser);

        String jsonRequest = "{\n" +
                "\t\"username\": \"TestUser\",\n" +
                "\t\"password\": \"200\",\n" +
                "\t\"email\": \"test_user@mail.ru\"\n" +
                "}";

        try {
            mockMvc.perform(post("/api/v1/users/register")
                    .content(jsonRequest)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.username", is(user.getUsername())))
                    .andExpect(jsonPath("$.email", is(user.getEmail())));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}