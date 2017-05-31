package com.codecool.volunti.service;

import com.codecool.volunti.model.Organisation;
import com.codecool.volunti.model.User;
import com.codecool.volunti.model.Volunteer;
import com.codecool.volunti.service.email.EmailService;
import com.icegreen.greenmail.util.GreenMail;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import static org.junit.Assert.assertEquals;

public class EmailServiceTest extends AbstractServiceTest {

    private GreenMail smtpServer;
    private Organisation organisation;
    private User user;
    private Volunteer volunteer;

    @Autowired
    private EmailService emailService;

    @Before
    public void setUp() throws Exception {
        // TODO: finalise greenmail test
        /*
        smtpServer = new GreenMail(new ServerSetup(25, null, "smtp"));
        smtpServer.start();

        volunteer = new Volunteer();
        organisation = new Organisation();
        organisation.setName("TestName");
        organisation.setCategory(Category.ADVERTISING_AGENCY);
        organisation.setCountry(Country.Hungary);
        organisation.setZipcode("ZIPCODE");
        organisation.setCity("TestCity");
        organisation.setAddress("Address");
        organisation.setMission("mission");
        organisation.setDescription1("Desc1");
        organisation.setDescription2("Desc2");

        user = new User("Test", "USer", "test.user@gmail.com", "testPassword", organisation, volunteer );
        */
    }

    @After
    public void tearDown() throws Exception {
        /* smtpServer.stop(); */
    }

    @Test
    public void shouldSendMail() throws Exception {
        /*
        user.signupSuccess(emailService, EmailType.CONFIRMATION);
        assertReceivedMessageContains();
        */
    }

    // Email sending logic is @async, and that cause us a big problem....in future, we should solve it
    private void assertReceivedMessageContains() throws IOException, MessagingException {
        MimeMessage[] receivedMessages = smtpServer.getReceivedMessages();
        System.out.println(receivedMessages.length);
        //assertEquals(1, receivedMessages.length);
        //String content = (String) receivedMessages[0].getContent();
        //assertTrue(content.contains(expected));
    }

}
