package com.multi.jpa.primarydatasource.repository;

import com.multi.jpa.primarydatasource.domain.Book;
import org.hibernate.annotations.SQLInsert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * BookRepository
 *
 * @author kevin
 **/
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Transactional(transactionManager = "transactionManagerPrimary")
    @Query(value = "INSERT INTO book(name, category) VALUE(?1,?2)",nativeQuery = true)
    @Modifying
    int insert(String name,String category);

    @Transactional(transactionManager = "transactionManagerPrimary")
    @Query(value = "UPDATE book SET name=:name,category=:category WHERE id=:id",nativeQuery = true)
    @Modifying
    int updateById(@Param("name") String name, @Param("category") String category, @Param("id") Integer id);

}
