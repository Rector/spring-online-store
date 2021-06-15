package ru.kir.online.store;


import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import ru.kir.online.store.dtos.UserDto;

import java.io.IOException;

@JsonTest
public class UserDtoJsonTest {
    private JacksonTester<UserDto> jacksonTester;

    @Autowired
    private void setJacksonTester(JacksonTester<UserDto> jacksonTester){
        this.jacksonTester = jacksonTester;
    }

    @Test
    public void userDtoSerializationTest(){
        UserDto userDto = new UserDto("Aristarch", "aristarch@gmail.com");
        try{
            AssertionsForInterfaceTypes.assertThat(jacksonTester.write(userDto))
                    .hasJsonPathStringValue("$.username")
                    .extractingJsonPathStringValue("$.email").isEqualTo("aristarch@gmail.com");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Test
    public void userDtoDeserializationTest(){
        String userDtoToString = "{\"username\": \"Aristarch\",\"email\": \"aristarch@gmail.com\"}";
        UserDto userDto = new UserDto("Aristarch", "aristarch@gmail.com");

        try{
            AssertionsForInterfaceTypes.assertThat(jacksonTester.parse(userDtoToString)).isEqualTo(userDto);
            AssertionsForInterfaceTypes.assertThat(jacksonTester.parseObject(userDtoToString).getEmail()).isEqualTo("aristarch@gmail.com");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}