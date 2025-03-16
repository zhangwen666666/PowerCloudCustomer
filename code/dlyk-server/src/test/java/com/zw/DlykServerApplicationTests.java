package com.zw;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class DlykServerApplicationTests {

    public static final ThreadLocal<Map<String,Integer>> threadLocal = new ThreadLocal<>();
    @Test
    void contextLoads() {
        threadLocal.set(Map.of("a",1,"b",2));
        threadLocal.set(Map.of("a",3,"b",4));
        System.out.println(threadLocal.get());
    }

}
