package Moon.ports;

import Moon.domain.models.User;

public interface UserPort {
	
	boolean existByEmail(String email); 
    void saveUser(User user);
    User findByPersonDocument(long document);
    User findByEmail(String email);
    
}
/*USER REPOSITORY:
public interface JpaUserRepository extends JpaRepository<User, Long> {
boolean existsByEmail(String email); // Verifica si existe un usuario por correo.
Optional<User> findByDocument(long document); // Encuentra un usuario por documento.
Optional<User> findByEmail(String email); // Encuentra un usuario por correo.
}
*/