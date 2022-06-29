package br.com.petshoptchutchucao.agenda.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.petshoptchutchucao.agenda.dto.UserFormDto;
import br.com.petshoptchutchucao.agenda.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControlerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private UserRepository userRepository;
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	void couldNotRegisterUserWithIncompleteData() throws Exception {
		String json = "{}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void couldRegisterUserWithCompleteData() throws Exception {
		UserFormDto newUser = new UserFormDto("teste@teste.com.br","Teste");
		
		String json = objectMapper.writeValueAsString(newUser);
		String jsonWanted = "{\"email\": \"teste@teste.com.br\", \"name\": \"Teste\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.content().json(jsonWanted));
	}

}
