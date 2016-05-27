package com.baobaotao.dao;

import com.baobaotao.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static org.junit.Assert.*;

/**
 * Created by machangsheng on 16/3/31.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UserDaoTest{

    @Autowired
    private UserDao userDao;

    @Before
    public void init(){

    }

    @Test
    public void findUserByUserName(){
        User user = userDao.findUserByUserName("tony");
        assertNull("不存在用户名为tony的用户!", user);
        user = userDao.findUserByUserName("jan");
        assertNotNull("用户jan存在", user);

        assertEquals("jan", user.getUserName());
        assertEquals("123456", user.getPassword());
        assertEquals(10, user.getCredits());
    }

}
