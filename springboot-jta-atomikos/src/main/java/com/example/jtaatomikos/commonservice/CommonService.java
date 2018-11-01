package com.example.jtaatomikos.commonservice;

import com.example.jtaatomikos.datasource1.mapper.DataSource1Mapper;
import com.example.jtaatomikos.datasource2.mapper.DataSource2Mapper;
import com.example.jtaatomikos.domain.Book;
import com.example.jtaatomikos.domain.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 多数据源操作类
 *
 * @author kevin
 **/
@Service
public class CommonService {

    @Autowired
    private DataSource1Mapper mapper1;

    @Autowired
    private DataSource2Mapper mapper2;

    /**
     * 分布式事务管理
     * @param player
     * @param book
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public int InserTData(Player player, Book book){
        int i = mapper1.insert(player.getName(), player.getGender());

        // int a=1/0;

        int j = mapper2.insert(book.getId(), book.getName(), book.getPrice(), book.getPnum(), book.getCategory());
        return i+j;
    }
}
