package br.com.petshoptchutchucao.agenda.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
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
import br.com.petshoptchutchucao.agenda.dto.UserUpdateFormDto;
import br.com.petshoptchutchucao.agenda.model.Profile;
import br.com.petshoptchutchucao.agenda.model.Status;
import br.com.petshoptchutchucao.agenda.model.User;
import br.com.petshoptchutchucao.agenda.repository.ProfileRepository;
import br.com.petshoptchutchucao.agenda.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Integer profilesVetor[] = new Integer[1];
	private List<Profile> profilesList = new ArrayList<>();
	
	private User createNewUserInBD(String email) {
		profilesVetor[0]=0;
		Profile profile = profileRepository.getById(profilesVetor[0]).get();
		profilesList.add(profile);
		
		User newUser = new User(email, "Teste Controller", profilesList, Status.ATIVO);
		userRepository.save(newUser);
		
		User registred = userRepository.findByEmail(newUser.getEmail());
		
		return registred;
	}
	
	@AfterAll
	public void removeTestsUsers() {
		userRepository.deleteAllByEmail("teste");
	}
	
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
	void couldNotRegisterUserWithWrongProfiles() throws Exception {
		profilesVetor[0] = 18;
		UserFormDto newUser = new UserFormDto("teste@teste.com.br","Teste", profilesVetor);
		
		String json = objectMapper.writeValueAsString(newUser);
		
		mvc.perform(MockMvcRequestBuilders
				.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void couldRegisterUserWithCompleteData() throws Exception {
		profilesVetor[0] = 10;
		UserFormDto newUser = new UserFormDto("teste@teste.com.br","Teste",profilesVetor);
		
		String json = objectMapper.writeValueAsString(newUser);
		String jsonWanted = "{\"email\": \"teste@teste.com.br\", \"name\": \"Teste\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isCreated())
			.andExpect(MockMvcResultMatchers.content().json(jsonWanted));
	}
	
	@Test
	void couldNotUpdateUserWithWrongId() throws Exception {
		profilesVetor[0]=0;
		
		UserUpdateFormDto userUpdate = new UserUpdateFormDto("123456",
															"umemail@email.com.br",
															"Não Existe",
															profilesVetor,
															Status.ATIVO,
															"p@ssword1");
		
		String json = objectMapper.writeValueAsString(userUpdate);
		
		mvc.perform(MockMvcRequestBuilders
				.put("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	@Test
	void couldUpdateUserWithCorrectId() throws Exception {
		profilesVetor[0] = 10;
		User user = createNewUserInBD("teste@email.com.br");
		
		UserUpdateFormDto userUpdate = new UserUpdateFormDto(user.getId(),
															user.getEmail(),
															"Novo Nome",
															profilesVetor,
															user.getStatus(),
															"$&nh4");
		
		String json = objectMapper.writeValueAsString(userUpdate);
		String jsonWanted = "{\"email\": \"teste@email.com.br\", \"name\": \"Novo Nome\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.put("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(jsonWanted));
	}

	@Test
	void couldInactiveAnUserWithCorrectId() throws Exception {
		User user = createNewUserInBD("teste@emaill.com.br");
		
		
		mvc.perform(MockMvcRequestBuilders
				.delete("/users/"+user.getId()))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
}
