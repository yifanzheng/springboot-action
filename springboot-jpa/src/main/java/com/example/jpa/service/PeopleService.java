package com.example.jpa.service;

import com.example.jpa.entity.People;
import com.example.jpa.repository.PeopleRepository;
import com.example.jpa.service.dto.PeopleDTO;
import com.example.jpa.service.mapper.PeopleMapper;
import org.springframework.stereotype.Service;

/**
 * PeopleService
 *
 * @author star
 */
@Service
public class PeopleService {

    private PeopleRepository peopleRepository;

    private PeopleMapper peopleMapper;

    public PeopleService(PeopleRepository peopleRepository, PeopleMapper peopleMapper) {
        this.peopleRepository = peopleRepository;
        this.peopleMapper = peopleMapper;
    }

    public void savePeople(PeopleDTO dto) {
        People people = peopleMapper.convertToPeople(dto);
        peopleRepository.save(people);
    }
}
