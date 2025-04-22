package org.easytrip.easytripbackend.service;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void sendBookingNotification(String toEmail, String guesthouseName, String travelerName,
                                        String checkInDate, String checkOutDate, double totalPrice) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(toEmail);
            helper.setSubject("New Booking for " + guesthouseName);
            helper.setText(
                    "<h3>New Booking Notification</h3>" +
                            "<p>A new booking has been made for your guesthouse: <b>" + guesthouseName + "</b></p>" +
                            "<p><b>Traveler:</b> " + travelerName + "</p>" +
                            "<p><b>Check-in Date:</b> " + checkInDate + "</p>" +
                            "<p><b>Check-out Date:</b> " + checkOutDate + "</p>" +
                            "<p><b>Total Price:</b> $" + totalPrice + "</p>" +
                            "<p>Please prepare for the guest's arrival.</p>",
                    true // Enable HTML
            );

            mailSender.send(message);
            logger.info("Notification sent to {} for booking at {}", toEmail, guesthouseName);
        } catch (MessagingException e) {
            logger.error("Failed to send notification to {}: {}", toEmail, e.getMessage());
            // Optionally, throw an exception or handle silently
        }
    }



    public void sendCancellationNotification(
            @NotBlank @Email String toEmail,
            String guesthouseName,
            String travelerName,
            String checkInDate,
            String checkOutDate) {
        if (!isValidEmail(toEmail)) {
            logger.warn("Invalid email address: {}. Skipping cancellation notification.", toEmail);
            return;
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(toEmail);
            helper.setSubject("Booking Cancelled for " + guesthouseName);
            helper.setText(
                    "<h3>Booking Cancellation Notification</h3>" +
                            "<p>A booking for your guesthouse: <b>" + guesthouseName + "</b> has been cancelled.</p>" +
                            "<p><b>Traveler:</b> " + travelerName + "</p>" +
                            "<p><b>Check-in Date:</b> " + checkInDate + "</p>" +
                            "<p><b>Check-out Date:</b> " + checkOutDate + "</p>",
                    true
            );
            mailSender.send(message);
            logger.info("Cancellation notification sent to {} for guesthouse {}", toEmail, guesthouseName);
        } catch (MessagingException e) {
            logger.error("Failed to send cancellation notification to {}: {}", toEmail, e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}
