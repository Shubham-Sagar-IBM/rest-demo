package com.example.restdemo;

import com.example.restdemo.model.UserRegistration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
class RestDemoApplicationTests {

	@Autowired
	private WebApplicationContext webApplicationContext;
	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void testCheckUserCredential() throws Exception {
		UserRegistration userRegistration = new UserRegistration("testuser", "Test@123", "24.48.0.1");

		String responseContent  = mockMvc.perform(MockMvcRequestBuilders
						.post("/user")
						.contentType(MediaType.APPLICATION_JSON)
						.content(asJsonString(userRegistration)))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();
		String[] responseParts = responseContent.split(" ");
		String uniqueId = responseParts[0];
		String expectedMessage = uniqueId+" Welcome testuser from Montreal";
		assertEquals(expectedMessage, responseContent);	}

	// Helper method to convert an object to JSON string
	private String asJsonString(Object obj) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

}
