package com.example.jtaatomikos.datasource2.mapper;

import com.example.jtaatomikos.domain.Book;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * DataSource2Mapper
 *
 * @author kevin
 **/
public interface DataSource2Mapper {

    /**
     * 根据name查询数据
     * @param name
     * @return
     */
    @Select("SELECT name,price,pnum,category FROM book WHERE name = #{name}")
    Book selectByName(@Param("name") String name);

    /**
     * 添加数据
     * @param name
     * @param price
     * @param pnum
     * @param category
     * @return
     */
    @Insert("INSERT INTO book(id, name, price, pnum, category) VALUE(#{id}, #{name}, #{price}, #{pnum}, #{category})")
    int insert(@Param("id") String id, @Param("name") String name, @Param("price") Integer price, @Param("pnum") Integer pnum, @Param("category") String category);
}
