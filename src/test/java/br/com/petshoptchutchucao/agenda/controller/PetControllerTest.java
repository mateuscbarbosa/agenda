package br.com.petshoptchutchucao.agenda.controller;

import java.time.LocalDate;
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

import br.com.petshoptchutchucao.agenda.model.request.PetFormDto;
import br.com.petshoptchutchucao.agenda.model.request.PetUpdateFormDto;
import br.com.petshoptchutchucao.agenda.infra.security.TokenService;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Customer;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Gender;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Pet;
import br.com.petshoptchutchucao.agenda.model.entities.user.Profile;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Size;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Species;
import br.com.petshoptchutchucao.agenda.model.entities.user.Status;
import br.com.petshoptchutchucao.agenda.model.entities.user.User;
import br.com.petshoptchutchucao.agenda.model.repository.CustomerRepository;
import br.com.petshoptchutchucao.agenda.model.repository.PetRepository;
import br.com.petshoptchutchucao.agenda.model.repository.ProfileRepository;
import br.com.petshoptchutchucao.agenda.model.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PetControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private PetRepository petRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenService tokenService;
	
	private String token;
	
	@BeforeAll
	void generateToken() {
		Profile admin = profileRepository.getById(0).get();
		List<Profile> profilesList = new ArrayList<>();
		profilesList.add(admin);
		User loged = new User("teste@admin.com.br","123456","Teste", profilesList, Status.ATIVO);
		userRepository.save(loged);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(loged, loged.getEmail());
		this.token = tokenService.generateToken(authentication);
	}
	
	@AfterAll
	void deleteAllTestsInserts() {
		petRepository.deleteAllByName("Teste");
		customerRepository.deleteAllByName("Teste");
		userRepository.deleteAllByEmail("teste");
	}
	
	@Test
	void couldNotPermitAnyInteractionWithIncorrectUserPermition() throws Exception{
		Profile admin = profileRepository.getById(60).get();
		List<Profile> profilesList = new ArrayList<>();
		profilesList.add(admin);
		User loged = new User("teste@admin.com.br","123456","Teste", profilesList, Status.ATIVO);
		userRepository.save(loged);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(loged, loged.getEmail());
		String token = tokenService.generateToken(authentication);
		
		mvc.perform(MockMvcRequestBuilders
				.get("/pets")
				.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void couldNotRegisterAPetWithIncompleteData() throws Exception {
		String json = "{}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/pets")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void couldNotRegisterAPetWithWrongCustomerId() throws Exception {
		PetFormDto petForm = new PetFormDto("Pet Teste",
											Species.CACHORRO,
											Gender.MACHO,
											"Pastor Alemão",
											LocalDate.now(),
											Size.GRANDE,
											null,
											"123456");
		
		String json = objectMapper.writeValueAsString(petForm);
		
		mvc.perform(MockMvcRequestBuilders
				.post("/pets")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	@Test
	void couldRegisterAPatWithCompleteDataAndExistenteCustomer() throws Exception {
		Customer customer = createCustomerInBD("Cliente1 Teste");
		
		PetFormDto petForm = new PetFormDto("Pet Teste",
											Species.CACHORRO,
											Gender.MACHO,
											"Pastor Alemão",
											LocalDate.now(),
											Size.GRANDE,
											null,
											customer.getId());
		
		String json = objectMapper.writeValueAsString(petForm);
		
		String jsonWanted = "{\"name\":\"Pet Teste\",\"spicies\":\"CACHORRO\",\"breed\":\"Pastor Alemão\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/pets")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.content().json(jsonWanted));
	}
	
	@Test
	void couldNotUpdateAPetWithWrongId() throws Exception {
		PetUpdateFormDto petUpdate = new PetUpdateFormDto("123456",
														"Pet Teste",
														Species.CACHORRO,
														Gender.MACHO,
														"Vira Lata",
														LocalDate.now(),
														Size.GRANDE,
														null,
														"123465u");
		
		String json = objectMapper.writeValueAsString(petUpdate);
		
		mvc.perform(MockMvcRequestBuilders
				.put("/pets")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void couldUpdateAPetWithCorrectId() throws Exception {
		Pet pet = createPetInBD("Pett Teste","Cliente3 Teste");
		
		PetUpdateFormDto petUpdate = new PetUpdateFormDto(pet.getId(),
														pet.getName(),
														pet.getSpicies(),
														Gender.FÊMEA,
														"Husky",
														pet.getBirth(),
														Size.GRANDE,
														"Observação",
														pet.getCustomerId());
		
		String json = objectMapper.writeValueAsString(petUpdate);
		
		String jsonWanted = "{\"name\":\"Pett Teste\",\"spicies\":\"CACHORRO\",\"breed\":\"Husky\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.put("/pets")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(jsonWanted));
	}
	
	@Test
	void couldDeleteAPetWithCorretId() throws Exception {
		Pet pet = createPetInBD("Pettt Teste","Cliente4 Teste");
		
		mvc.perform(MockMvcRequestBuilders
				.delete("/pets/"+pet.getId())
				.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
		
	}
	
	private Customer createCustomerInBD(String name) {
		Customer customer = new Customer(name, "Rua Teste", null, Status.ATIVO);
		customerRepository.save(customer);
		
		Customer registred = customerRepository.findByName(customer.getName());
		
		return registred;
	}
	
	private Pet createPetInBD(String petName, String customerName) {
		Pet pet = new Pet(petName, Species.CACHORRO, Gender.MACHO, "Vira Lata", LocalDate.now(), Size.MÉDIO, null, createCustomerInBD(customerName).getId());
		
		petRepository.save(pet);
		
		Pet registred = petRepository.findByName(pet.getName());
		
		return registred;
	}

}
