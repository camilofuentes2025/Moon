package Moon.ports;

import Moon.domain.models.Person;
import Moon.domain.models.User;

public interface UserPort {
    
    boolean existEmail(String email); 
    void saveUser(User user);
    void savePerson(Person person);
    User findByPersonDocument(Person person);
    User findByEmail(String email);
    void updatePassword(String email, String newPassword); // 🔥 NUEVO MÉTODO
}