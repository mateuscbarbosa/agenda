package br.com.petshoptchutchucao.agenda.controller;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.petshoptchutchucao.agenda.model.request.UserFormDto;
import br.com.petshoptchutchucao.agenda.model.request.UserUpdateFormDto;
import br.com.petshoptchutchucao.agenda.infra.BusinessRulesException;
import br.com.petshoptchutchucao.agenda.infra.security.TokenService;
import br.com.petshoptchutchucao.agenda.model.entities.user.Profile;
import br.com.petshoptchutchucao.agenda.model.entities.user.Status;
import br.com.petshoptchutchucao.agenda.model.entities.user.User;
import br.com.petshoptchutchucao.agenda.model.repository.ProfileRepository;
import br.com.petshoptchutchucao.agenda.model.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private TokenService tokenService;
	
	private Integer profilesVetor[] = new Integer[1];
	private List<Profile> profilesList = new ArrayList<>();
	private String token;
	
	@BeforeAll
	private void generateToken() {
		Profile admin = profileRepository.getById(0).get();
		profilesList.add(admin);
		User loged = new User("teste@admin.com.br","123456","Teste", profilesList, Status.ATIVO);
		loged.addProfile(admin);
		userRepository.save(loged);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(loged, loged.getEmail());
		this.token = tokenService.generateToken(authentication);
		profilesList.clear();
	}
	
	@BeforeAll
	private void generateGenericUserInBD() {
		createNewUserInBD("testee@email.com.br");
	}
	
	@AfterAll
	public void removeTestsUsers() {
		userRepository.deleteAllByEmail("teste");
	}
	
	@Test
	void couldNotPermitAnyInterationWithIncorrectUserPermition() throws Exception{
		profilesList.clear();
		Profile admin = profileRepository.getById(11).get();
		profilesList.add(admin);
		User loged = new User("teste@adminn.com.br","123456","Teste", profilesList, Status.ATIVO);
		loged.addProfile(admin);
		userRepository.save(loged);
		Authentication authentication = new UsernamePasswordAuthenticationToken(loged, loged.getEmail());
		String token = tokenService.generateToken(authentication);
		profilesList.clear();
		
		mvc.perform(MockMvcRequestBuilders
				.get("/users")
				.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void couldNotRegisterUserWithIncompleteData() throws Exception {
		String json = "{}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
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
				.content(json)
				.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void couldNotRegisterUserWithANonUniqueEmail() throws Exception {
		profilesVetor[0] = 10;
		
		UserFormDto userForm = new UserFormDto("testee@email.com.br","Teste",profilesVetor);
		
		String json = objectMapper.writeValueAsString(userForm);
		
		mvc.perform(MockMvcRequestBuilders
					.post("/users")
					.contentType(MediaType.APPLICATION_JSON)
					.content(json)
					.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void couldRegisterUserWithCompleteDataAndUniqueEmail() throws Exception {
		profilesVetor[0] = 10;
		UserFormDto newUser = new UserFormDto("teste@teste.com.br","Teste",profilesVetor);
		
		String json = objectMapper.writeValueAsString(newUser);
		String jsonWanted = "{\"email\": \"teste@teste.com.br\", \"name\": \"Teste\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
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
				.content(json)
				.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	@Test
	void couldNotUpdateUserWithDifferentEmailAlreadyExistent() throws Exception {
		profilesVetor[0] = 10;
		
		User user = createNewUserInBD("testeU@email.com.br");
		
		UserUpdateFormDto userUpdate = new UserUpdateFormDto(user.getId(),
															"testee@email.com",
															user.getName(),
															profilesVetor,
															user.getStatus(),
															user.getPassword());
		
		String json = objectMapper.writeValueAsString(userUpdate);
		
		mvc.perform(MockMvcRequestBuilders
				.put("/users")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
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
				.content(json)
				.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(jsonWanted));
	}

	@Test
	void couldInactiveAnUserWithCorrectId() throws Exception {
		User user = createNewUserInBD("teste@emaill.com.br");
		
		
		mvc.perform(MockMvcRequestBuilders
				.delete("/users/"+user.getId())
				.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
	}
	
	private User createNewUserInBD(String email) {
		profilesVetor[0]=0;
		Profile profile = profileRepository.getById(profilesVetor[0]).get();
		profilesList.add(profile);
		
		User newUser = new User(email, "Teste Controller", profilesList, Status.ATIVO);
		userRepository.save(newUser);
		
		User registred = userRepository.findByEmail(newUser.getEmail()).orElseThrow(() -> new BusinessRulesException("Usuário não encontrado."));
		
		return registred;
	}
}
