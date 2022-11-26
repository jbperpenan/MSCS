package org.jbp.csc611m.mc01.repositories;


import org.jbp.csc611m.mc01.entities.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends CrudRepository<Email, Long> {
}