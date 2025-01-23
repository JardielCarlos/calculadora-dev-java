package com.calculadora.udemy.calculadora.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import com.calculadora.udemy.calculadora.Exceptions.RequiredIsNullException;
import com.calculadora.udemy.calculadora.Exceptions.ResourceNotFoundException;
import com.calculadora.udemy.calculadora.controller.PersonController;
import com.calculadora.udemy.calculadora.data.vo.v1.PersonVO;
import com.calculadora.udemy.calculadora.data.vo.v2.PersonVOV2;
import com.calculadora.udemy.calculadora.mapper.DozerMapper;
import com.calculadora.udemy.calculadora.mapper.custom.PersonMapper;
import com.calculadora.udemy.calculadora.models.Person;
import com.calculadora.udemy.calculadora.repositories.PersonRepository;

@Service
public class PersonServices {

  private Logger logger = Logger.getLogger(PersonServices.class.getName());

  @Autowired
  PersonRepository repository;

  @Autowired
  PersonMapper mapper;

  public List<PersonVO> findAll() {

    logger.info("Finding all people!");
    var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
    persons
      .stream()
      .forEach(p -> {
        try {
          p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel());
        } catch (Exception e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      });

    return persons;
  }

  public PersonVO findById(Long id) throws Exception {

    logger.info("Finding one person!");

    var entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

    var vo = DozerMapper.parseObject(entity, PersonVO.class);
    vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
    return vo;
  }

  public PersonVO create(PersonVO person) throws Exception {

    logger.info("Creating one person!");

    if (person == null) throw new RequiredIsNullException();
    
    var entity = DozerMapper.parseObject(person, Person.class);
    var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
    vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
    return vo;
  }

  public PersonVOV2 createV2(PersonVOV2 person) {

    logger.info("Creating one person with v2!");

    var entity = mapper.convertVoToEntity(person);
    var vo =  mapper.convertEntityToVo(repository.save(entity));
    return vo;
  }

  public PersonVO update(PersonVO person) throws Exception {
    logger.info("Updating one person!");
    
    if (person == null) throw new RequiredIsNullException();

    var entity = repository.findById(person.getKey())
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

    entity.setFirstName(person.getFirstName());
    entity.setLastName(person.getLastName());
    entity.setAddress(person.getAddress());
    entity.setGender(person.getGender());

    var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
    vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
    return vo;
  }

  public void delete(Long id) {

    logger.info("delete one person!");
    var entity = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));

    repository.delete(entity);
  }
}
