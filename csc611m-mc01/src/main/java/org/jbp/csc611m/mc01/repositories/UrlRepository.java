package org.jbp.csc611m.mc01.repositories;


import org.jbp.csc611m.mc01.entities.Url;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends CrudRepository<Url, Long> {
    long countLongByStatus(String status);
}