package com.webdrp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WebdrpApplicationTests {

	@Autowired
	RedisTemplate<String, Object> template;
	@Test
	public void contextLoads() {
		template.opsForValue().set("key1", "value1");
		System.out.println(template.opsForValue().get("key1"));
	}

}
