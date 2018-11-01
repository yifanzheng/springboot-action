package com.example.resource.datasource01.service;

import com.example.resource.datasource01.mapper.DataSource1Mapper;
import com.example.resource.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author kevin
 **/
@Service
public class DataSourceService1 {

    @Autowired
    private DataSource1Mapper mapper;

    public Player selectPlayer(String name) {
        Player player = mapper.selectByName(name);

        return player;
    }

    @Transactional(rollbackFor = Exception.class, transactionManager = "transactionManager1")
    public int insertPlayer(Player player) {

        if (player==null) {
            return 0;
        }

        int insert = mapper.insert(player.getName(), player.getGender());
        return insert;
    }
}
