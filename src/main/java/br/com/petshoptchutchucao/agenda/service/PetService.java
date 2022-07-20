package br.com.petshoptchutchucao.agenda.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.petshoptchutchucao.agenda.dto.PetFormDto;
import br.com.petshoptchutchucao.agenda.dto.PetOutputDto;
import br.com.petshoptchutchucao.agenda.dto.PetSimplifiedOutputDto;
import br.com.petshoptchutchucao.agenda.dto.PetUpdateFormDto;
import br.com.petshoptchutchucao.agenda.infra.BusinessRulesException;
import br.com.petshoptchutchucao.agenda.model.Customer;
import br.com.petshoptchutchucao.agenda.model.Pet;
import br.com.petshoptchutchucao.agenda.repository.CustomerRepository;
import br.com.petshoptchutchucao.agenda.repository.PetRepository;

@Service
public class PetService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PetRepository petRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	public Page<PetOutputDto> list(Pageable pagination) {
		Page<Pet> pets = petRepository.findAll(pagination);
		return pets.map(p -> modelMapper.map(p, PetOutputDto.class));
	}

	@Transactional
	public PetOutputDto register(PetFormDto petForm) {
		Pet pet = modelMapper.map(petForm, Pet.class);
		
		Customer customer = findPetOwner(petForm.getCustomerId());	
		
		petRepository.save(pet);
		
		customer.addPet(pet.getId(),pet.getName());
		customerRepository.save(customer);
						
		return modelMapper.map(pet, PetOutputDto.class);
	}

	@Transactional
	public PetOutputDto update(PetUpdateFormDto petUpdate) {
		Pet pet = petRepository.findById(petUpdate.getId()).orElseThrow(() -> new BusinessRulesException("ID do Pet não encontrado."));
		Customer customer = findPetOwner(petUpdate.getCustomerId());
		
		pet.updateInfo(petUpdate.getName(),
						petUpdate.getSpicies(),
						petUpdate.getGender(),
						petUpdate.getBreed(),
						petUpdate.getBirth(),
						petUpdate.getSize(),
						petUpdate.getObservation(),
						customer.getId());
		
		petRepository.save(pet);
		
		customer.getPets().stream()
							.filter(p -> p.getId().equals(pet.getId()))
							.findFirst()
							.ifPresent(p->p.setName(petUpdate.getName()));
		
		customerRepository.save(customer);
		
		return modelMapper.map(pet, PetOutputDto.class);
	}
	
	@Transactional
	public void delete(String id) {
		Pet pet = petRepository.findById(id).orElseThrow(() -> new BusinessRulesException("ID do Pet não encontrado."));
		
		Customer customer = findPetOwner(pet.getCustomerId());
		
		petRepository.delete(pet);
		
		customer.deletePet(new PetSimplifiedOutputDto(pet.getId(), pet.getName()));
		customerRepository.save(customer);
	}
	
	private Customer findPetOwner(String id) {
		var customer = customerRepository.findById(id).orElseThrow(() -> new BusinessRulesException("ID do Cliente não encontrado;"));
		
		return customer;
	}

}
