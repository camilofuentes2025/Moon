package Moon.ports;

import Moon.domain.models.Person;

public interface PersonPort {
	
	boolean existPerson(long document);
    void savePerson(Person person);
    Person findByDocument(long document);

}
