package dev.jeffersonfreitas.myfinance.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.jeffersonfreitas.myfinance.model.entity.Person;

public interface PersonRepository extends JpaRepository<Person, Long>{

	Optional<Person> findByNameIgnoreCase(String name);

}
