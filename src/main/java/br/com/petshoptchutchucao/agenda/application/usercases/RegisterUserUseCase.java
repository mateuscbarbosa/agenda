package br.com.petshoptchutchucao.agenda.application.usercases;

import br.com.petshoptchutchucao.agenda.infra.BusinessRulesException;
import br.com.petshoptchutchucao.agenda.infra.PasswordGeneratorPassay;
import br.com.petshoptchutchucao.agenda.model.entities.user.Profile;
import br.com.petshoptchutchucao.agenda.model.entities.user.Status;
import br.com.petshoptchutchucao.agenda.model.entities.user.User;
import br.com.petshoptchutchucao.agenda.model.repository.ProfileRepository;
import br.com.petshoptchutchucao.agenda.model.repository.UserRepository;
import br.com.petshoptchutchucao.agenda.model.request.UserFormDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class RegisterUserUseCase {

    private final UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    public RegisterUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(UserFormDto request, Authentication authentication){
        if(userRepository.existsByEmail(request.getEmail()))
            throw new BusinessRulesException("E-mail já está cadastrado no banco.");

        ModelMapper modelMapper = new ModelMapper();
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        var user = modelMapper.map(request, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(new PasswordGeneratorPassay().generatePassword()));
        user.setProfiles(findProfiles(request.getProfiles()));
        user.setStatus(Status.ATIVO);

        return userRepository.save(user);
    }

    private List<Profile> findProfiles (Integer[] profilesVector){
        List<Profile> profiles = new ArrayList<>();

        for (int i = 0;  i < profilesVector.length; i++) {
            try {
                Profile profile = profileRepository.getById(profilesVector[i]).get();
                profiles.add(profile);
            }catch(NoSuchElementException ex) {
                throw new BusinessRulesException("Código de Perfil: "
                        +profilesVector[i]
                        +", não encontrado");
            }
        }

        return profiles;
    }
}
