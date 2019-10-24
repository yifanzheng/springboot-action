package com.example.jpa.service.mapper;

import com.example.jpa.entity.People;
import com.example.jpa.service.dto.PeopleDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * PeopleMapper
 *
 * @author star
 */
@Service
public class PeopleMapper {

    public People convertToPeople(PeopleDTO dto) {
        People people = new People();
        BeanUtils.copyProperties(dto, people);

        return people;
    }
}
