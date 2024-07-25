package br.com.petshoptchutchucao.agenda.application.usercases;

import br.com.petshoptchutchucao.agenda.model.entities.user.User;
import br.com.petshoptchutchucao.agenda.model.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GetAllUsersUseCase {

    private final UserRepository userRepository;

    public GetAllUsersUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<User> execute(Pageable pageable){
        return userRepository.findAll(pageable);
    }
}
