package com.calculadora.udemy.calculadora.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calculadora.udemy.calculadora.models.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
