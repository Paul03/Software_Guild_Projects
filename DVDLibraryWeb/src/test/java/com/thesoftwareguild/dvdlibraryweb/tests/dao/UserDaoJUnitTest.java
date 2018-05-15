package com.thesoftwareguild.dvdlibraryweb.tests.dao;

import com.thesoftwareguild.dvdlibraryweb.dao.UserDao;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class UserDaoJUnitTest {

    private UserDao userDao;
    private static final String applicationContextFileName = "spring-persistence.xml";
    private static final String userDaoBeanName = "userDao";

    @Before
    public void setup() {

        ApplicationContext context = new ClassPathXmlApplicationContext(applicationContextFileName);
        userDao = context.getBean(userDaoBeanName, UserDao.class);

    }

    @After
    public void tearDown() {

    }

    @Test
    public void insertTest() {

        // Arrange

        // Act

        // Assert

    }

    @Test
    public void retrieveTest() {

        // Arrange

        // Act

        // Assert

    }

    @Test
    public void updateTest() {

        // Arrange

        // Act

        // Assert

    }

    @Test
    public void deleteTest() {

        // Arrange

        // Act

        // Assert

    }

}
