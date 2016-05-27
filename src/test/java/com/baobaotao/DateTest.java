package com.baobaotao;

import org.junit.Test;

import java.util.Date;

/**
 * Created by machangsheng on 16/5/12.
 */
public class DateTest {

    @Test
    public void testDate(){
        System.out.println(new Date());

        long time = new Date().getTime() - 7 * 86400;

        System.out.println(new Date(time));

        time = new Date().getTime() - 7 * 86400 * 1000;

        System.out.println(new Date(time));
    }
}
