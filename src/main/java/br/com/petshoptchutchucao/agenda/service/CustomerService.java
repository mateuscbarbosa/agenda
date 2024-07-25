package br.com.petshoptchutchucao.agenda.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.petshoptchutchucao.agenda.model.response.CustomerDetailedOutputDto;
import br.com.petshoptchutchucao.agenda.model.request.CustomerFormDto;
import br.com.petshoptchutchucao.agenda.model.response.CustomerOutputDto;
import br.com.petshoptchutchucao.agenda.model.request.CustomerUpdateFormDto;
import br.com.petshoptchutchucao.agenda.model.response.PetDetailedOutputDto;
import br.com.petshoptchutchucao.agenda.infra.BusinessRulesException;
import br.com.petshoptchutchucao.agenda.model.entities.logs.Activity;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Customer;
import br.com.petshoptchutchucao.agenda.model.entities.customer.Pet;
import br.com.petshoptchutchucao.agenda.model.entities.user.Status;
import br.com.petshoptchutchucao.agenda.model.entities.logs.SystemModule;
import br.com.petshoptchutchucao.agenda.model.repository.CustomerRepository;
import br.com.petshoptchutchucao.agenda.model.repository.PetRepository;

@Service
public class CustomerService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PetRepository petRepository;
	
	@Autowired
	private LogsService logsService;

	public Page<CustomerOutputDto> list(Pageable pagination) {
		Page<Customer> customers = customerRepository.findAllActive(pagination);
		return customers.map(c -> modelMapper.map(c, CustomerOutputDto.class));
	}

	@Transactional
	public CustomerOutputDto register(CustomerFormDto customerForm, Authentication authentication) {
		Customer customer = modelMapper.map(customerForm, Customer.class);
		
		customer.setStatus(Status.ATIVO);
		
		customerRepository.save(customer);
		
		logsService.registerLog(authentication, Activity.REGISTRO, SystemModule.CLIENTES, "Cliente: "+customer.getName());
		
		return modelMapper.map(customer, CustomerOutputDto.class);
	}

	@Transactional
	public CustomerOutputDto update(CustomerUpdateFormDto customerForm, Authentication authentication) {
		Customer customer = customerRepository.findById(customerForm.getId()).orElseThrow(() -> new BusinessRulesException("ID do Cliente não encontrado."));
		String oldCustomer = String.format("Cliente: %s"
										+ " Endereço: %s"
										+ " Números Telefônicos: %s"
										+ " Status: %s", customer.getName(), customer.getAddress(), customer.getContactNumbers(), customer.getStatus().toString());
		
		customer.updateInfo(customerForm.getId(),
							customerForm.getName(),
							customerForm.getAddress(),
							customerForm.getContactNumbers(),
							customerForm.getStatus());
		
		String newCustomer = String.format(" Cliente: %s"
										+ " Endereço: %s"
										+ " Números Telefônicos: %s"
										+ " Status: %s", customer.getName(), customer.getAddress(), customer.getContactNumbers(), customer.getStatus().toString());
		
		customerRepository.save(customer);
		
		logsService.registerLog(authentication, Activity.ATUALIZAÇÃO, SystemModule.CLIENTES, oldCustomer + " //PARA// " + newCustomer);
		
		return modelMapper.map(customer, CustomerOutputDto.class);
	}

	@Transactional
	public void inactivate(String id, Authentication authentication) {
		Customer customer = customerRepository.findById(id).orElseThrow(() -> new BusinessRulesException("ID do Cliente não encontrado."));
		
		customer.setStatus(Status.INATIVO);
		
		logsService.registerLog(authentication, Activity.INATIVAÇÃO, SystemModule.CLIENTES, "Cliente: " + customer.getName());
		
		customerRepository.save(customer);
	}

	public CustomerDetailedOutputDto details(String id) {
		Customer customer = customerRepository.findById(id).orElseThrow(() -> new BusinessRulesException("ID do Cliente não encontrado."));
		
		CustomerDetailedOutputDto customerDetalied = modelMapper.map(customer, CustomerDetailedOutputDto.class);
		
		customerDetalied.setPets(petsDetaileds(customer.getId()));
		
		return customerDetalied;
	}
	
	private List<PetDetailedOutputDto> petsDetaileds(String customerId){
		List<Pet> pets = petRepository.findAllbyCustomerId(customerId);
		
		return pets.stream().map(p -> modelMapper.map(p, PetDetailedOutputDto.class))
							.collect(Collectors.toList());
	}
	
}
