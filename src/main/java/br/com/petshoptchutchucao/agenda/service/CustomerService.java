package br.com.petshoptchutchucao.agenda.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.petshoptchutchucao.agenda.dto.CustomerDetaliedOutputDto;
import br.com.petshoptchutchucao.agenda.dto.CustomerFormDto;
import br.com.petshoptchutchucao.agenda.dto.CustomerOutputDto;
import br.com.petshoptchutchucao.agenda.dto.CustomerUpdateFormDto;
import br.com.petshoptchutchucao.agenda.dto.PetDetaliedOutputDto;
import br.com.petshoptchutchucao.agenda.infra.BusinessRulesException;
import br.com.petshoptchutchucao.agenda.model.Customer;
import br.com.petshoptchutchucao.agenda.model.Pet;
import br.com.petshoptchutchucao.agenda.model.Status;
import br.com.petshoptchutchucao.agenda.repository.CustomerRepository;
import br.com.petshoptchutchucao.agenda.repository.PetRepository;

@Service
public class CustomerService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private PetRepository petRepository;

	public Page<CustomerOutputDto> list(Pageable pagination) {
		Page<Customer> customers = customerRepository.findAllActive(pagination);
		return customers.map(c -> modelMapper.map(c, CustomerOutputDto.class));
	}

	@Transactional
	public CustomerOutputDto register(CustomerFormDto customerForm) {
		Customer customer = modelMapper.map(customerForm, Customer.class);
		
		customer.setStatus(Status.ATIVO);
		
		customerRepository.save(customer);
		
		return modelMapper.map(customer, CustomerOutputDto.class);
	}

	@Transactional
	public CustomerOutputDto update(CustomerUpdateFormDto customerForm) {
		Customer customer = customerRepository.findById(customerForm.getId()).orElseThrow(() -> new BusinessRulesException("ID do Cliente não encontrado."));
		
		customer.updateInfo(customerForm.getId(),
							customerForm.getName(),
							customerForm.getAddress(),
							customerForm.getContactNumbers(),
							customerForm.getStatus());
		
		customerRepository.save(customer);
		
		return modelMapper.map(customer, CustomerOutputDto.class);
	}

	@Transactional
	public void inactivate(String id) {
		Customer customer = customerRepository.findById(id).orElseThrow(() -> new BusinessRulesException("ID do Cliente não encontrado."));
		
		customer.setStatus(Status.INATIVO);
		
		customerRepository.save(customer);
	}

	public CustomerDetaliedOutputDto details(String id) {
		Customer customer = customerRepository.findById(id).orElseThrow(() -> new BusinessRulesException("ID do Cliente não encontrado."));
		
		CustomerDetaliedOutputDto customerDetalied = modelMapper.map(customer, CustomerDetaliedOutputDto.class);
		
		customerDetalied.setPets(petsDetaileds(customer.getId()));
		
		return customerDetalied;
	}
	
	private List<PetDetaliedOutputDto> petsDetaileds(String customerId){
		List<Pet> pets = petRepository.findAllbyCustomerId(customerId);
		
		return pets.stream().map(p -> modelMapper.map(p, PetDetaliedOutputDto.class))
							.collect(Collectors.toList());
	}
	
}
