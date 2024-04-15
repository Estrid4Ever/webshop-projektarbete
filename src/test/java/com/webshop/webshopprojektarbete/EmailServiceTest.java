package com.webshop.webshopprojektarbete;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

import com.webshop.webshopprojektarbete.entity.Products;
import com.webshop.webshopprojektarbete.service.EmailServiceImpl;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Hashtable;
import java.util.Objects;



public class EmailServiceTest {

    private EmailServiceImpl emailService;

    @Mock
    private JavaMailSender emailSender;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> messageCaptor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        emailService = new EmailServiceImpl(emailSender);
    }

    @Test
    public void sendVerificationTokenTest() {
        String to = "test@example.com";
        String token = "123456";

        emailService.sendVerificationToken(to, token);

        verify(emailSender, times(1)).send(messageCaptor.capture());
        SimpleMailMessage capturedMessage = messageCaptor.getValue();

        // Verify captured message properties
        assertEquals("New User Account Verification", capturedMessage.getSubject());
        assertEquals(to, capturedMessage.getTo()[0]);
        assertEquals("Your verification key: " + token, capturedMessage.getText());
    }
    @Test
    public void testSendOrderVerification() {
        // Mock input data
        String to = "example@example.com";
        Hashtable<Products, Integer> items = new Hashtable<>();


        MimeMessage mimeMessage = mock(MimeMessage.class);
        when(emailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Call the method under test
        emailService.sendOrderVerification(to, items);

        // Verify that the necessary methods were called with the correct parameters
        verify(emailSender).createMimeMessage();
        // Verify other method calls on emailSender as needed...
    }
    // Test for sendOrderVerificationTest remains unchanged
}
