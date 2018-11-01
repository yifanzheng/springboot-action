package com.example.jtaatomikos.datasource1.mapper;

import com.example.jtaatomikos.domain.Player;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * DataSource2Mapper
 *
 * @author kevin
 **/
public interface DataSource1Mapper {

    @Select("SELECT name,gender FROM player WHERE name = #{name}")
    Player selectByName(@Param("name") String name);

    @Insert("INSERT INTO player(name, gender) VALUE(#{name}, #{gender})")
    int insert(@Param("name") String name, @Param("gender") String gender);
}
