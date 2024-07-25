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

import br.com.petshoptchutchucao.agenda.model.request.CustomerFormDto;
import br.com.petshoptchutchucao.agenda.model.request.CustomerUpdateFormDto;
import br.com.petshoptchutchucao.agenda.infra.security.TokenService;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Customer;
import br.com.petshoptchutchucao.agenda.model.entities.user.Profile;
import br.com.petshoptchutchucao.agenda.model.entities.user.Status;
import br.com.petshoptchutchucao.agenda.model.entities.user.User;
import br.com.petshoptchutchucao.agenda.model.repository.CustomerRepository;
import br.com.petshoptchutchucao.agenda.model.repository.ProfileRepository;
import br.com.petshoptchutchucao.agenda.model.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerControllerTest {

	@Autowired
	private MockMvc mvc;
	
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
	
	private List<String> contactNumbers = new ArrayList<>();
	private String token;

	@BeforeAll
	void insertContactNumber() {
		contactNumbers.add("00 00000-0000");
	}
	
	@BeforeAll
	void generateUserToken() {
		Profile admin = profileRepository.getById(0).get();
		List<Profile> profilesList = new ArrayList<>();
		profilesList.add(admin);
		User loged = new User("teste@admin.com.br","123456","Teste", profilesList, Status.ATIVO);
		loged.addProfile(admin);
		userRepository.save(loged);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(loged, loged.getEmail());
		this.token=tokenService.generateToken(authentication);
	}
	
	@AfterAll
	void deleteAllCustomersTest() {
		customerRepository.deleteAllByName("Teste");
		userRepository.deleteAllByEmail("teste");
	}
	
	@Test
	void couldNotPermitAnyInterationWithIncorrectUserPermition() throws Exception{
		Profile admin = profileRepository.getById(60).get();
		List<Profile> profilesList = new ArrayList<>();
		profilesList.add(admin);
		User loged = new User("teste@adminn.com.br","123456","Teste", profilesList, Status.ATIVO);
		loged.addProfile(admin);
		userRepository.save(loged);
		
		Authentication authentication = new UsernamePasswordAuthenticationToken(loged, loged.getEmail());
		String token=tokenService.generateToken(authentication);
		
		mvc.perform(MockMvcRequestBuilders
				.get("/customers")
				.header("Authorization", "Bearer "+token))
			.andExpect(MockMvcResultMatchers.status().isForbidden());
	}
	
	@Test
	void couldNotRegisterACustomerWithIncompleteData() throws Exception {
		String json = "{}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void couldRegisterACustomerWithCompleteData() throws Exception {
		CustomerFormDto customerForm = new CustomerFormDto("Cliente Teste", "Rua Teste, 00 - Bairro Teste. Teste/TE", contactNumbers);
		
		String json = objectMapper.writeValueAsString(customerForm);
		String jsonWanted = "{\"name\":\"Cliente Teste\",\"address\":\"Rua Teste, 00 - Bairro Teste. Teste/TE\",\"contactNumbers\":[\"00 00000-0000\"]}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.content().json(jsonWanted));		
	}

	@Test
	void couldNotUpdateACustomerWithIncorrectId() throws Exception {
		CustomerUpdateFormDto customerUpdate = new CustomerUpdateFormDto("123456", "Cliente Teste", "Rua Teste, 00 - Bairro Teste. Teste/TE", contactNumbers, Status.ATIVO);
		
		String json = objectMapper.writeValueAsString(customerUpdate);
		
		mvc.perform(MockMvcRequestBuilders
				.put("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void couldUpdateACustomerWithCorrectId() throws Exception {
		Customer customer = createCustomerInBD("Cliente Um Teste");
		
		CustomerUpdateFormDto customerUpdate = new CustomerUpdateFormDto(customer.getId(),
																		customer.getName(),
																		"Rua Teste, 11 - Bairro Teste 1. Teste/TE",
																		contactNumbers,
																		customer.getStatus());
		String json = objectMapper.writeValueAsString(customerUpdate);
		String jsonWanted = "{\"name\":\"Cliente Um Teste\",\"address\":\"Rua Teste, 11 - Bairro Teste 1. Teste/TE\",\"contactNumbers\":[\"00 00000-0000\"]}";
		
		mvc.perform(MockMvcRequestBuilders
				.put("/customers")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.header("Authorization", "Bearer " + token))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andExpect(MockMvcResultMatchers.content().json(jsonWanted));
		
	}
	
	@Test
	void couldInactivateACustomerWithCorrectId() throws Exception {
		Customer customer = createCustomerInBD("Cliente I Teste");
		
		mvc.perform(MockMvcRequestBuilders
				.delete("/customers/"+customer.getId())
				.header("Authorization", "Bearer " + token))
			.andExpect(MockMvcResultMatchers.status().isNoContent());
	}

	private Customer createCustomerInBD(String name) {
		Customer newCustomer = new Customer(name,"Rua Teste, 00 - Bairro Teste. Teste/TE", contactNumbers, Status.ATIVO);
		
		customerRepository.save(newCustomer);
		
		Customer registred = customerRepository.findByName(newCustomer.getName());
				
		return registred;
	}
}
