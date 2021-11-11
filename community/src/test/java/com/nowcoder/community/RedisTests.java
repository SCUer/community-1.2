package com.nowcoder.community;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
public class RedisTests {

    @Resource
    private RedisTemplate redisTemplate;

    @Test
    public void testStrings(){
        String redisKey = "test:count";
        redisTemplate.opsForValue().set(redisKey, 1);

        System.out.println(redisTemplate.opsForValue().get(redisKey));
        System.out.println(redisTemplate.opsForValue().increment(redisKey));
        System.out.println(redisTemplate.opsForValue().decrement(redisKey));
    }

    @Test
    public void testHashes(){
        String redisKey = "test:user";
        redisTemplate.opsForHash().put(redisKey, "id",1);
        redisTemplate.opsForHash().put(redisKey, "username","zhangsan");

        System.out.println(redisTemplate.opsForHash().get(redisKey, "id"));
        System.out.println(redisTemplate.opsForHash().get(redisKey, "username"));

    }

    @Test
    public void testHyperLogLog(){
        String redisKey = "test:hll:01";

        for(int i=1;i<=100000;i++){
            redisTemplate.opsForHyperLogLog().add(redisKey, i);
        }

        for(int i=1;i<=100000;i++){
            int r = (int)(Math.random()*100000 + 1);
            redisTemplate.opsForHyperLogLog().add(redisKey, r);
        }

        Long size = redisTemplate.opsForHyperLogLog().size(redisKey);
        System.out.println(size);
    }

}
