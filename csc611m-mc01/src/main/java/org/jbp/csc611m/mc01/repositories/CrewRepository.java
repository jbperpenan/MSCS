package org.jbp.csc611m.mc01.repositories;


import org.jbp.csc611m.mc01.entities.Crew;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrewRepository extends CrudRepository<Crew, Long> {
}