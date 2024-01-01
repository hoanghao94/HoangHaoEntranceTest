package com.nexle.test.entrance;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nexle.test.entrance.controller.UsersController;
import com.nexle.test.entrance.domain.Users;
import com.nexle.test.entrance.dto.SignUpInfoDTO;
import com.nexle.test.entrance.service.UsersService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
@WebMvcTest(UsersController.class)
public class UsersApiControllerTests {

    private static final String SIGN_UP = "/sign-up";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsersService service;

    @Test
    public void testSignUpValidEmailReturn400BadRequest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        SignUpInfoDTO newUser = new SignUpInfoDTO();
        newUser.setEmail("");
        String requestBody = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(SIGN_UP).contentType("application/json")
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void testSignUpValidPasswordReturn400BadRequest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        SignUpInfoDTO newUser = new SignUpInfoDTO();
        newUser.setPassword("");
        String requestBody = objectMapper.writeValueAsString(newUser);
        mockMvc.perform(post(SIGN_UP).contentType("application/json")
                .content(requestBody))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}
