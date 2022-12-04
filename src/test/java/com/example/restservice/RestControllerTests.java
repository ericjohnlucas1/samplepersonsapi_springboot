package com.example.restservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.ws.rs.core.MediaType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc
public class RestControllerTests {

	@Autowired
	private MockMvc mockMvc;



	public static String asJsonString(final Object obj) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(obj);
			return json;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}  

	@Test
	public void noParamGreetingShouldReturnDefaultMessage() throws Exception {
		Person testPerson = new Person(1,"eric",25);
		//First create new person with post and verify that person is created successfully
		this.mockMvc.perform(post("/persons").content(asJsonString(testPerson)).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isCreated())
		.andExpect(jsonPath("$.name").value("eric"))
		.andExpect(jsonPath("$.age").value(25))
		.andDo(
			//Retrieve that person by ID
			result -> this.mockMvc.perform(get("/persons/1"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.name").value("eric"))
			.andExpect(jsonPath("$.age").value(25))
			.andDo(
				//User the list endpoint to get list of persons and verify that the list has a length of one
				result2 -> this.mockMvc.perform(get("/persons"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andDo(
					//User the list endpoint to get list of persons younger than 25 and verify that the list has a length of 0
					result3 -> this.mockMvc.perform(get("/persons?youngerthan=25"))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(jsonPath("$").isArray())
					.andExpect(jsonPath("$", Matchers.hasSize(0)))
				)
			)
		);


	}

}