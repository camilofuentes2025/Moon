package Moon.adapters.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Moon.adapters.users.entity.UserEntity;
import Moon.adapters.persons.entity.PersonEntity;
import Moon.adapters.users.repository.UserRepository;
import Moon.domain.models.User;
import Moon.ports.UserPort;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Service
public class UserAdapter implements UserPort {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean existEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void saveUser(User user) {
        UserEntity userEntity = userAdapter(user);
        userRepository.save(userEntity);
        user.setEmail(userEntity.getEmail());
        //user.setPassword(userEntity.getPassword());
    }

    @Override
    public User findByPersonDocument(long document) {
        UserEntity userEntity = userRepository.findByDocument(document);
        return userAdapter(userEntity);
    }

    @Override
    public User findByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        return userAdapter(userEntity);
    }

    private User userAdapter(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }
        User user = new User();
        user.setDocument(userEntity.getPerson().getDocument()); 
        user.setName(userEntity.getPerson().getName());
        user.setPhone(userEntity.getPerson().getPhone());
        user.setAge(userEntity.getPerson().getAge());
        user.setEmail(userEntity.getEmail());
        user.setPassword(userEntity.getPassword());
        user.setRol(userEntity.getRol());
        return user;
    }

    private UserEntity userAdapter(User user) {
    	if (user == null) {
            return null;
		}
        UserEntity userEntity = new UserEntity();
        PersonEntity personEntity = new PersonEntity();
        personEntity.setDocument(user.getDocument());
        personEntity.setName(user.getName());
        personEntity.setPhone(user.getPhone());
        personEntity.setAge(user.getAge());
        userEntity.setPerson(personEntity); 
        userEntity.setEmail(user.getEmail());
        userEntity.setPassword(user.getPassword());
        userEntity.setRol(user.getRol());
        return userEntity;
    }
}
