package Moon.adapters.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import Moon.adapters.users.entity.UserEntity;
import Moon.adapters.persons.entity.PersonEntity;
import Moon.adapters.users.repository.UserRepository;
import Moon.adapters.persons.repository.PersonRepository;
import Moon.domain.models.Person;
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

    @Autowired
    private PersonRepository personRepository; // Se agregó el repositorio de Person

    @Override
    public boolean existEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void savePerson(Person person) {
        PersonEntity personEntity = personAdapter(person);
        personRepository.save(personEntity); // Guardar primero la persona en la base de datos
    }

    @Override
    public void saveUser(User user) {
        UserEntity userEntity = userAdapter(user);
        userRepository.save(userEntity);
        user.setEmail(userEntity.getEmail());
    }

    @Override
    public User findByPersonDocument(Person person) {
        PersonEntity personEntity = personAdapter(person);
        UserEntity userEntity = userRepository.findByPerson(personEntity);
        return userAdapter(userEntity);
    }

 // Agregar este método en tu UserAdapter.java

    @Override
    public void updatePassword(String email, String newPassword) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity != null) {
            userEntity.setPassword(newPassword);
            userRepository.save(userEntity); // Solo actualiza el usuario existente
            System.out.println("🔄 Contraseña actualizada correctamente en la base de datos.");
        } else {
            System.out.println("❌ No se pudo actualizar: usuario no encontrado.");
        }
    }
    
    private Person personAdapter(PersonEntity personEntity) {
        if (personEntity == null) {
            return null;
        }
        Person person = new Person();
        person.setDocument(personEntity.getDocument());
        person.setName(personEntity.getName());
        person.setPhone(personEntity.getPhone());
        person.setAge(personEntity.getAge());
        return person;
    }

    private PersonEntity personAdapter(Person person) {
        if (person == null) {
            return null;
        }
        PersonEntity personEntity = new PersonEntity();
        personEntity.setDocument(person.getDocument());
        personEntity.setName(person.getName());
        personEntity.setPhone(person.getPhone());
        personEntity.setAge(person.getAge());
        return personEntity;
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
        
        // 🔥 CAMBIO CLAVE: Verificar si la persona ya existe
        PersonEntity existingPerson = personRepository.findByDocument(user.getDocument());
        
        PersonEntity personEntity;
        if (existingPerson != null) {
            // Si existe, usar la entidad existente y actualizarla si es necesario
            personEntity = existingPerson;
            personEntity.setName(user.getName());
            personEntity.setPhone(user.getPhone());
            personEntity.setAge(user.getAge());
            // No llamamos save() aquí, se guardará en cascada
        } else {
            // Si no existe, crear nueva
            personEntity = personAdapter(user);
            personRepository.save(personEntity);
        }
        
        // 🔥 CAMBIO CLAVE: Verificar si el usuario ya existe
        UserEntity existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null) {
            // Si el usuario existe, actualizar el existente
            userEntity = existingUser;
            userEntity.setPassword(user.getPassword());
            userEntity.setRol(user.getRol());
            // La persona ya está asociada, no cambiarla
        } else {
            // Si es nuevo usuario, configurar todo
            userEntity.setPerson(personEntity);
            userEntity.setEmail(user.getEmail());
            userEntity.setPassword(user.getPassword());
            userEntity.setRol(user.getRol());
        }
        
        return userEntity;
    }
}