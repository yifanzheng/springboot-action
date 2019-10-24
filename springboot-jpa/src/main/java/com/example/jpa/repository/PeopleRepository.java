package com.example.jpa.repository;

import com.example.jpa.entity.People;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * PeopleRepository
 *
 * @author star
 */
public interface PeopleRepository extends JpaRepository<People, Long> {
}
