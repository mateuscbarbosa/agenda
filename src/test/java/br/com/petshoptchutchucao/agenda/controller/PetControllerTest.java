package br.com.petshoptchutchucao.agenda.controller;

import java.time.LocalDate;

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

import br.com.petshoptchutchucao.agenda.dto.PetFormDto;
import br.com.petshoptchutchucao.agenda.dto.PetUpdateFormDto;
import br.com.petshoptchutchucao.agenda.model.Customer;
import br.com.petshoptchutchucao.agenda.model.Gender;
import br.com.petshoptchutchucao.agenda.model.Pet;
import br.com.petshoptchutchucao.agenda.model.Size;
import br.com.petshoptchutchucao.agenda.model.Spicies;
import br.com.petshoptchutchucao.agenda.model.Status;
import br.com.petshoptchutchucao.agenda.repository.CustomerRepository;
import br.com.petshoptchutchucao.agenda.repository.PetRepository;

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
	
	@AfterAll
	void deleteAllTestsInserts() {
		petRepository.deleteAllByName("Teste");
		customerRepository.deleteAllByName("Teste");
	}
	
	@Test
	void couldNotRegisterAPetWithIncompleteData() throws Exception {
		String json = "{}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/pets")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void couldNotRegisterAPetWithWrongCustomerId() throws Exception {
		PetFormDto petForm = new PetFormDto("Pet Teste",
											Spicies.CACHORRO,
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
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isBadRequest());
		
	}
	
	@Test
	void couldRegisterAPatWithCompleteDataAndExistenteCustomer() throws Exception {
		Customer customer = createCustomerInBD("Cliente1 Teste");
		
		PetFormDto petForm = new PetFormDto("Pet Teste",
											Spicies.CACHORRO,
											Gender.MACHO,
											"Pastor Alemão",
											LocalDate.now(),
											Size.GRANDE,
											null,
											customer.getId());
		
		String json = objectMapper.writeValueAsString(petForm);
		
		String jsonWanted = "{\"name\":\"Pet Teste\",\"spicies\":\"CACHORRO\",\"gender\":\"MACHO\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.post("/pets")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
		.andExpect(MockMvcResultMatchers.status().isCreated())
		.andExpect(MockMvcResultMatchers.content().json(jsonWanted));
	}
	
	@Test
	void couldNotUpdateAPetWithWrongId() throws Exception {
		PetUpdateFormDto petUpdate = new PetUpdateFormDto("123456",
														"Pet Teste",
														Spicies.CACHORRO,
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
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}
	
	@Test
	void couldUpdateAPetWithCorrectId() throws Exception {
		Pet pet = createPetInBD();
		
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
		
		String jsonWanted = "{\"name\":\"Pet Teste\",\"spicies\":\"CACHORRO\",\"gender\":\"FÊMEA\"}";
		
		mvc.perform(MockMvcRequestBuilders
				.put("/pets")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json))
			.andExpect(MockMvcResultMatchers.status().isOk())
			.andExpect(MockMvcResultMatchers.content().json(jsonWanted));
	}
	
	private Customer createCustomerInBD(String name) {
		Customer customer = new Customer(name, "Rua Teste", null, Status.ATIVO);
		customerRepository.save(customer);
		
		Customer registred = customerRepository.findByName(customer.getName());
		
		return registred;
	}
	
	private Pet createPetInBD() {
		Pet pet = new Pet("Pet Teste", Spicies.CACHORRO, Gender.MACHO, "Vira Lata", LocalDate.now(), Size.MÉDIO, null, createCustomerInBD("Cliente2 Teste").getId());
		
		petRepository.save(pet);
		
		Pet registred = petRepository.findByName(pet.getName());
		
		return registred;
	}

}
