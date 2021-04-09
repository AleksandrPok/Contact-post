package com.test.contactpost.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.test.contactpost.model.Contact;
import com.test.contactpost.repository.ContactRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@SpringBootTest
@AutoConfigureMockMvc
class ContactControllerTest {
    private static final String URL = "/contacts/";
    @Autowired
    private MockMvc mockMvc;

    @BeforeAll
    public static void init(@Autowired ContactRepository contactRepository) {
        contactRepository.save(new Contact("Nathan", "Christopher", "Fillion"));
        contactRepository.save(new Contact("Alan", "Wray", "Tudyk"));
    }

    @Test
    public void testClearRequestOk() throws Exception {
        String expected = "{\"fullName\":\"Alan Wray Tudyk\"}";
        String actual = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"2\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(expected, actual);
    }

    @Test
    public void testRequestWithSomeGarbageOk() throws Exception {
        String expected = "{\"fullName\":\"Nathan Christopher Fillion\"}";
        String actual = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"asdf\":\"af\", \"id\":\"1\"}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(expected, actual);
    }

    @Test
    public void testMethodArgumentNotValidOK() throws Exception {
        String expected = "{\"errorCode\":400,\"errorMessage\":\"Id must be at least 1\"}";
        String actual = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"-3\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(expected, actual);
    }

    @Test
    public void testHttpMessageNotReadable() throws Exception {
        String expected = "{\"errorCode\":400,\"errorMessage\":\"Id must be numeric\"}";
        String actual = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"ddsfgh\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(expected, actual);
    }

    @Test
    public void testContactNotFoundException() throws Exception {
        String expected = "{\"errorCode\":404,\"errorMessage\":\"Contact not found, id : 3\"}";
        String actual = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"3\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(expected, actual);
    }

    @Test
    public void testIllegalArgumentException() throws Exception {
        String expected = "{\"errorCode\":400,\"errorMessage\":\"Please, enter correct Id\"}";
        String actual = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"sdf\":\"ddsfgh\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertEquals(expected, actual);
    }
}
