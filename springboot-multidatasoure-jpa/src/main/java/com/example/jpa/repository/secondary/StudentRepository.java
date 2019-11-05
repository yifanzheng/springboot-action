package com.example.jpa.repository.secondary;

import com.example.jpa.entity.secondary.Student;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * StudentRepository
 *
 * @author star
 **/
public interface StudentRepository extends JpaRepository<Student, Integer> {

}
