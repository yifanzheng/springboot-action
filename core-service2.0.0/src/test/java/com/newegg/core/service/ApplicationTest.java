package com.newegg.core.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext
public class ApplicationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testApp() {
        ResponseEntity<String> entity = this.restTemplate.getForEntity("/faq.htm", String.class);

        Assert.assertEquals(entity.getStatusCodeValue(), 200);
        Assert.assertEquals(entity.getBody(), "<!--Newegg-->");
    }

    @Test
    public void testVersion() {
        ResponseEntity<String> entity = this.restTemplate.getForEntity("/version", String.class);

        Assert.assertEquals(entity.getStatusCodeValue(), 200);
        Assert.assertEquals(entity.getBody(), "default - 1.0.0");
    }
}