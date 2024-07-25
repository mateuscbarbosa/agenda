package br.com.petshoptchutchucao.agenda.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;

import br.com.petshoptchutchucao.agenda.model.request.CustomerFormDto;
import br.com.petshoptchutchucao.agenda.model.response.CustomerOutputDto;
import br.com.petshoptchutchucao.agenda.model.request.CustomerUpdateFormDto;
import br.com.petshoptchutchucao.agenda.infra.BusinessRulesException;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Customer;
import br.com.petshoptchutchucao.agenda.model.entities.user.Status;
import br.com.petshoptchutchucao.agenda.model.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CustomerServiceTest {

	private List<String> contactNumbers = new ArrayList<>();
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@Mock
	private Authentication authentication;
	
	@Mock
	private LogsService logsService;
	
	@InjectMocks
	private CustomerService service;
	
	@BeforeAll
	private void insertContactNumber() {
		contactNumbers.add("00 00000-0000");
	}
	
	@Test
	void couldRegisterACustomerWithCompleteData() {
		CustomerFormDto customerForm = new CustomerFormDto("Cliente Teste","Rua Teste, 00 - Bairro Teste. Teste/TE",contactNumbers);
		
		Customer customer = new Customer(customerForm.getName(), customerForm.getAddress(), customerForm.getContactNumbers(), Status.ATIVO);
		
		Mockito.when(modelMapper.map(customerForm, Customer.class)).thenReturn(customer);
		
		Mockito.when(modelMapper.map(customer, CustomerOutputDto.class)).thenReturn(new CustomerOutputDto(null,customer.getName(),customer.getAddress(), customer.getContactNumbers()));
		
		CustomerOutputDto customerOutput = service.register(customerForm, authentication);
		
		Mockito.verify(customerRepository).save(Mockito.any());

		assertEquals(customerForm.getName(), customerOutput.getName());
		assertEquals(customerForm.getAddress(), customerOutput.getAddress());
		assertEquals(customerForm.getContactNumbers(), customerOutput.getContactNumbers());
	}
	
	@Test
	void couldNotUpdateACustomerWithInexistentId() {
		CustomerUpdateFormDto customerUpdate = new CustomerUpdateFormDto("123456", "Cliente Teste", "Alguma Rua", contactNumbers, Status.ATIVO);
		
		Mockito.when(customerRepository.findById(customerUpdate.getId())).thenThrow(BusinessRulesException.class);
		
		assertThrows(BusinessRulesException.class, () -> service.update(customerUpdate, authentication));
	}
	
	@Test
	void couldUpdateACustomerWithCorrectId() {
		CustomerUpdateFormDto customerUpdate = new CustomerUpdateFormDto("123456", "Cliente Teste", "Alguma Rua", contactNumbers, Status.ATIVO);
		
		Customer customer = new Customer(customerUpdate.getId(),
										customerUpdate.getName(),
										customerUpdate.getAddress(),
										customerUpdate.getContactNumbers(),
										customerUpdate.getStatus());
		
		Mockito.when(customerRepository.findById(customerUpdate.getId())).thenReturn(Optional.of(customer));
		
		Mockito.when(modelMapper.map(customer, CustomerOutputDto.class)).thenReturn(new CustomerOutputDto(customer.getId(),
																										customer.getName(),
																										customer.getAddress(),
																										customer.getContactNumbers()));
		
		CustomerOutputDto customerDto = service.update(customerUpdate, authentication);
		
		Mockito.verify(customerRepository).save(Mockito.any());
		
		assertEquals(customerUpdate.getId(), customerDto.getId());
		assertEquals(customerUpdate.getName(), customerDto.getName());
		assertEquals(customerUpdate.getContactNumbers(), customerDto.getContactNumbers());
	}

	@Test
	void couldInactivateACustomerWithCorrectId() {
		Customer customer = new Customer("123456", "Cliente Teste", "Endereço",contactNumbers, Status.ATIVO);
		
		Mockito.when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
		
		service.inactivate(customer.getId(), authentication);
		
		Mockito.verify(customerRepository).save(Mockito.any());
	}
}
