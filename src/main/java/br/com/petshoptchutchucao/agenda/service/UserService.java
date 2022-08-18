package br.com.petshoptchutchucao.agenda.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.petshoptchutchucao.agenda.dto.UserDetailedOutputDto;
import br.com.petshoptchutchucao.agenda.dto.UserFormDto;
import br.com.petshoptchutchucao.agenda.dto.UserOutputDto;
import br.com.petshoptchutchucao.agenda.dto.UserUpdateFormDto;
import br.com.petshoptchutchucao.agenda.infra.BusinessRulesException;
import br.com.petshoptchutchucao.agenda.infra.PasswordGeneratorPassay;
import br.com.petshoptchutchucao.agenda.model.Activity;
import br.com.petshoptchutchucao.agenda.model.Profile;
import br.com.petshoptchutchucao.agenda.model.Status;
import br.com.petshoptchutchucao.agenda.model.SystemModule;
import br.com.petshoptchutchucao.agenda.model.User;
import br.com.petshoptchutchucao.agenda.repository.ProfileRepository;
import br.com.petshoptchutchucao.agenda.repository.UserRepository;

@Service
public class UserService implements UserDetailsService{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ProfileRepository profileRepository;
	
	@Autowired
	private LogsService logsService;
	
	public Page<UserOutputDto> list(Pageable pagination){
		
		Page<User> users = userRepository.findAll(pagination);
		return users.map(u -> modelMapper.map(u, UserOutputDto.class));
	}

	@Transactional
	public UserOutputDto register(UserFormDto userForm, Authentication authentication) {
		
		User user = modelMapper.map(userForm, User.class);
		user.setPassword(bCryptPasswordEncoder.encode(new PasswordGeneratorPassay().generatePassword()));
		

		user.setProfiles(findProfiles(userForm.getProfiles()));
		user.setStatus(Status.ATIVO);
		if(verifyUniqueUserEmail(userForm.getEmail())) {
			throw new BusinessRulesException("E-mail já está cadastrado no banco.");
		}
		userRepository.save(user);
		
		logsService.registerLog(authentication, Activity.REGISTRO, SystemModule.USUÁRIOS, "Novo Usuário: "+user.getName()+" E-mail: "+user.getEmail());
				
		return modelMapper.map(user, UserOutputDto.class);
	}

	@Transactional
	public UserOutputDto update(UserUpdateFormDto userForm, Authentication authentication) {
		User user = userRepository.findById(userForm.getId()).orElseThrow(() -> new BusinessRulesException("ID do usuário não encontrado."));
		List<String> profilesDescription = new ArrayList<>(); 
		user.getProfiles().stream().forEach(p -> profilesDescription.add(p.getDescription()));
		
		String oldUser = String.format("Usuário: %s"
									+ " E-mail: %s"
									+ " Perfils: %s"
									+ " Status: %s", user.getName(), user.getEmail(), profilesDescription, user.getStatus().toString());
		String newUser;
		
		if(user.getEmail().equals(userForm.getEmail()) || !verifyUniqueUserEmail(userForm.getEmail())) {
			user.updateInfo(userForm.getEmail(),
					userForm.getName(),
					bCryptPasswordEncoder.encode(userForm.getPassword()),
					findProfiles(userForm.getProfiles()),
					userForm.getStatus());
			
			profilesDescription.clear();
			user.getProfiles().stream().forEach(p -> profilesDescription.add(p.getDescription()));
			newUser = String.format(" Usuário: %s"
										+ " E-mail: %s"
										+ " Perfils: %s"
										+ " Status: %s", user.getName(), user.getEmail(), profilesDescription, user.getStatus().toString());
		}
		else {
			throw new BusinessRulesException("E-mail já está cadastrado no banco para outro usuário.");
		}
		
		userRepository.save(user);
		
		logsService.registerLog(authentication, Activity.ATUALIZAÇÃO, SystemModule.USUÁRIOS, oldUser + " //PARA// " + newUser);
		
		return modelMapper.map(user, UserOutputDto.class);
	}
	
	@Transactional
	public void inactivate(String id, Authentication authentication) {
		User user = userRepository.findById(id).orElseThrow(() -> new BusinessRulesException("ID do usuário não encontrado."));
		
		user.setStatus(Status.INATIVO);
		
		userRepository.save(user);
		
		logsService.registerLog(authentication, Activity.INATIVAÇÃO, SystemModule.USUÁRIOS, "Usuário: "+user.getName());
	}

	@Transactional
	public UserDetailedOutputDto details(String id) {
		User user = userRepository.findById(id).orElseThrow(() -> new BusinessRulesException("ID do usuários não encontrado."));
		return modelMapper.map(user, UserDetailedOutputDto.class);
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
	
	private boolean verifyUniqueUserEmail(String email) {
		if(userRepository.existsByEmail(email)) {
			return true;
		}
		return false;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuário inválido."));
	}
	
}
