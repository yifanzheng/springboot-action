package com.newegg.core.service.qin;

import com.newegg.core.service.cache.CacheKey;
import com.newegg.core.service.domain.ClockRecord;
import com.newegg.core.service.domain.GeneralRecord;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class a {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private RedisTemplate<Object,Object> template;

    @Test
    public void test() throws Exception {

        // 保存字符串
        stringRedisTemplate.opsForValue().set("aaa", "111");
        Assert.assertEquals("111", stringRedisTemplate.opsForValue().get("aaa"));
        Assert.assertEquals("ClockRecord", CacheKey.ClockRecord.toString());

    }
    @Test
    public void fun1() {
        ArrayList<Integer> oldcache=new ArrayList<>();
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=1;i<6;i++)
            oldcache.add(i);
        for(int i=1;i<7;i++){
            if(i!=3)
                list.add(i);
        }

        oldcache.removeAll(list);
        oldcache.forEach(e-> System.out.println(e));
        Assert.assertEquals(Integer.valueOf(3),oldcache.get(0));
    }

    @Test
    public void savereids() {
        GeneralRecord generalRecord=new GeneralRecord();
        generalRecord.setDealType("通过");
        generalRecord.setProblemReason("运气不好");
        generalRecord.setApplicant("王伟");
        generalRecord.setId("2");
        template.opsForValue().set(generalRecord.getId()+"",generalRecord);
        GeneralRecord result = (GeneralRecord) template.opsForValue().get(generalRecord.getId()+"");
        System.out.println(result.toString());
    }

    @Test
    public void getredis(){
        GeneralRecord generalRecord= (GeneralRecord) template.opsForValue().get(1+"");
        System.out.println(generalRecord);
    }

    @Test
    public void saveHashReids(){
        for(int i=1;i<10;i++){
            GeneralRecord generalRecord=new GeneralRecord();
            generalRecord.setDealType("通过");
            generalRecord.setProblemReason("运气不好");
            generalRecord.setApplicant("王伟");
            generalRecord.setId(i+"");
            template.opsForHash().put(CacheKey.GeneralRecord.toString(),generalRecord.getId(),generalRecord);
        }
    }

    @Test
    public void getHashRedis(){
        List<Object> list=template.opsForHash().values(CacheKey.GeneralRecord.toString());
        int a=1;
        System.out.println(a);
    }
}
