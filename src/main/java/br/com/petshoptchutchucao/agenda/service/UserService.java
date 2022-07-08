package br.com.petshoptchutchucao.agenda.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.petshoptchutchucao.agenda.dto.UserFormDto;
import br.com.petshoptchutchucao.agenda.dto.UserOutputDto;
import br.com.petshoptchutchucao.agenda.dto.UserUpdateFormDto;
import br.com.petshoptchutchucao.agenda.infra.BusinessRulesException;
import br.com.petshoptchutchucao.agenda.infra.PasswordGeneratorPassay;
import br.com.petshoptchutchucao.agenda.model.Profile;
import br.com.petshoptchutchucao.agenda.model.Status;
import br.com.petshoptchutchucao.agenda.model.User;
import br.com.petshoptchutchucao.agenda.repository.ProfileRepository;
import br.com.petshoptchutchucao.agenda.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	public Page<UserOutputDto> list(Pageable pagination){
		
		Page<User> users = userRepository.findAll(pagination);
		return users.map(u -> modelMapper.map(u, UserOutputDto.class));
	}

	@Transactional
	public UserOutputDto register(UserFormDto userForm) {
		
		User user = modelMapper.map(userForm, User.class);
		user.setPassword(new PasswordGeneratorPassay().generatePassword());
		

		user.setProfiles(findProfiles(userForm.getProfiles()));
		user.setStatus(Status.ATIVO);
		
		userRepository.save(user);
				
		return modelMapper.map(user, UserOutputDto.class);
	}

	@Transactional
	public UserOutputDto update(UserUpdateFormDto userForm) {
		User user = userRepository.findById(userForm.getId()).get();
		
		user.updateInfo(userForm.getEmail(),
						userForm.getName(),
						userForm.getPassword(),
						findProfiles(userForm.getProfiles()),
						userForm.getStatus());
		
		userRepository.save(user);
		
		return modelMapper.map(user, UserOutputDto.class);
	}
	
	@Transactional
	public void remove(String id) {
		User user = userRepository.findById(id).get();
		
		user.setStatus(Status.INATIVO);
		
		userRepository.save(user);
	}
	
	private List<Profile> findProfiles (Integer[] profilesVetor){
		List<Profile> profiles = new ArrayList<>();
		
		for (int i = 0;  i < profilesVetor.length; i++) {
			try {
				Profile profile = profileRepository.getById(profilesVetor[i]).get();
				profiles.add(profile);
			}catch(NoSuchElementException ex) {
				throw new BusinessRulesException("Código de Perfil: "
													+profilesVetor[i]
													+", não encontrado");
			}
		}
		
		return profiles;
	}


}
