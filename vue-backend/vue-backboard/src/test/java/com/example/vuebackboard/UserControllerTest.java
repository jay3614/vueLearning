package com.example.vuebackboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.vuebackboard.controller.UserController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.json.JSONObject;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

//    @Test
//    void test_1() throws Exception {
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("user_id", "test_userr");
//        jsonObject.put("user_pw", "test_passwordd");
//
//        ResultActions result = mockMvc.perform(post("/user/login")
//                .content(jsonObject.toString())
//                .contentType(MediaType.APPLICATION_JSON));
//
//        MvcResult mvcResult = result.andDo(print())
//                .andExpect(status().isOk())
//                .andReturn();
//
//        System.out.println(mvcResult.getResponse().getContentAsString());
//    }

    @Test
    void test_2() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user_id", "jay3614");
        jsonObject.put("user_pw", "1234");

        ResultActions result = mockMvc.perform(post("/user/login")
                .content(jsonObject.toString())
                .contentType(MediaType.APPLICATION_JSON));

        MvcResult mvcResult = result.andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}