package br.com.petshoptchutchucao.agenda.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import br.com.petshoptchutchucao.agenda.dto.CustomerFormDto;
import br.com.petshoptchutchucao.agenda.dto.CustomerOutputDto;
import br.com.petshoptchutchucao.agenda.model.Customer;
import br.com.petshoptchutchucao.agenda.model.Status;
import br.com.petshoptchutchucao.agenda.repository.CustomerRepository;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	private List<String> contactNumbers = new ArrayList<>();
	
	@Mock
	private ModelMapper modelMapper;
	
	@Mock
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private CustomerService service;
	
	@Test
	void couldRegisterACustomerWithCompleteData() {
		contactNumbers.add("00 00000-0000");
		CustomerFormDto customerForm = new CustomerFormDto("Cliente Teste","Rua Teste, 00 - Bairro Teste. Teste/TE",contactNumbers);
		
		Customer customer = new Customer(customerForm.getName(), customerForm.getAddress(), customerForm.getContactNumbers(), Status.ATIVO);
		
		Mockito.when(modelMapper.map(customerForm, Customer.class)).thenReturn(customer);
		
		Mockito.when(modelMapper.map(customer, CustomerOutputDto.class)).thenReturn(new CustomerOutputDto(null,customer.getName(),customer.getAddress(), customer.getContactNumbers()));
		
		CustomerOutputDto customerOutput = service.register(customerForm);
		
		Mockito.verify(customerRepository).save(Mockito.any());

		assertEquals(customerForm.getName(), customerOutput.getName());
		assertEquals(customerForm.getAddress(), customerOutput.getAddress());
		assertEquals(customerForm.getContactNumbers(), customerOutput.getContactNumbers());
	}

}
