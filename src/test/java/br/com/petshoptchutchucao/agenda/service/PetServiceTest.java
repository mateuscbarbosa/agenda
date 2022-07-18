package br.com.petshoptchutchucao.agenda.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.com.petshoptchutchucao.agenda.dto.PetFormDto;
import br.com.petshoptchutchucao.agenda.dto.PetOutputDto;
import br.com.petshoptchutchucao.agenda.infra.BusinessRulesException;
import br.com.petshoptchutchucao.agenda.model.Customer;
import br.com.petshoptchutchucao.agenda.model.Gender;
import br.com.petshoptchutchucao.agenda.model.Pet;
import br.com.petshoptchutchucao.agenda.model.Size;
import br.com.petshoptchutchucao.agenda.model.Spicies;
import br.com.petshoptchutchucao.agenda.repository.CustomerRepository;
import br.com.petshoptchutchucao.agenda.repository.PetRepository;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private PetRepository petRepository;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private PetService service;
	
	@Test
	void couldNotRegisterAPetWithIncorrectCustomerId() {
		PetFormDto petForm = new PetFormDto("Bicho Teste", Spicies.GATO, Gender.FÊMEA, "Vira Lata", LocalDate.now(), Size.PELO_CURTO, null, "123456");
		
		Pet pet = new Pet(petForm.getName(),
						petForm.getSpicies(),
						petForm.getGender(),
						petForm.getBreed(),
						petForm.getBirth(),
						petForm.getSize(),
						petForm.getObservation(),
						petForm.getCustomerId());
		
		Mockito.when(modelMapper.map(petForm, Pet.class)).thenReturn(pet);
		
		Mockito.when(customerRepository.findById(petForm.getCustomerId())).thenThrow(BusinessRulesException.class);
				
		assertThrows(BusinessRulesException.class, () -> service.register(petForm));
	}
	
	@Test
	void couldRegisterAPetWithCorrectCustomerId() {
		PetFormDto petForm = new PetFormDto("Bicho Teste", Spicies.GATO, Gender.FÊMEA, "Vira Lata", LocalDate.now(), Size.PELO_CURTO, null, "123456");
		Customer customer = new Customer();
		
		Pet pet = new Pet(petForm.getName(),
				petForm.getSpicies(),
				petForm.getGender(),
				petForm.getBreed(),
				petForm.getBirth(),
				petForm.getSize(),
				petForm.getObservation(),
				petForm.getCustomerId());

		Mockito.when(modelMapper.map(petForm, Pet.class)).thenReturn(pet);
		
		Mockito.when(customerRepository.findById(pet.getCustomerId())).thenReturn(Optional.of(customer));
		
		Mockito.when(modelMapper.map(pet, PetOutputDto.class)).thenReturn(new PetOutputDto(null, pet.getName(), pet.getSpicies(), pet.getGender()));
		
		PetOutputDto petDto = service.register(petForm);
		
		Mockito.verify(petRepository).save(Mockito.any());
		
		assertEquals(petDto.getName(), petForm.getName());
		assertEquals(petDto.getSpicies(), petForm.getSpicies());
		assertEquals(petDto.getGender(), petForm.getGender());
		
	}

}
