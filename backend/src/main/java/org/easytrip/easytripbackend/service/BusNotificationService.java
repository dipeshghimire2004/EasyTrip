package org.easytrip.easytripbackend.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class BusNotificationService {

    private static final Logger logger = LoggerFactory.getLogger(BusNotificationService.class);

    @Autowired
    private JavaMailSender mailSender;

    public void sendBookingNotification(
            @NotBlank @Email String passengerEmail,
            @NotBlank @Email String ownerEmail,
            String busName,
            String passengerName,
            String source,
            String destination,
            String departureTime,
            String arrivalTime,
            int numberOfSeats,
            double totalFare) {
        // Validate emails
        if (!isValidEmail(passengerEmail)) {
            logger.warn("Invalid passenger email address: {}. Skipping booking notification.", passengerEmail);
            return;
        }
        if (!isValidEmail(ownerEmail)) {
            logger.warn("Invalid owner email address: {}. Skipping booking notification.", ownerEmail);
            return;
        }

        // Send notification to passenger
        sendBookingEmailToPassenger(passengerEmail, busName, passengerName, source, destination,
                departureTime, arrivalTime, numberOfSeats, totalFare);

        // Send notification to bus owner
        sendBookingEmailToOwner(ownerEmail, busName, passengerName, source, destination,
                departureTime, arrivalTime, numberOfSeats, totalFare);
    }

    private void sendBookingEmailToPassenger(
            String passengerEmail,
            String busName,
            String passengerName,
            String source,
            String destination,
            String departureTime,
            String arrivalTime,
            int numberOfSeats,
            double totalFare) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(passengerEmail);
            helper.setSubject("Your Booking Confirmation for " + busName);
            helper.setText(
                    "<h3>Booking Confirmation</h3>" +
                            "<p>Dear <b>" + passengerName + "</b>,</p>" +
                            "<p>Your booking for the bus <b>" + busName + "</b> has been confirmed.</p>" +
                            "<p><b>Details:</b></p>" +
                            "<p><b>Source:</b> " + source + "</p>" +
                            "<p><b>Destination:</b> " + destination + "</p>" +
                            "<p><b>Departure Time:</b> " + departureTime + "</p>" +
                            "<p><b>Arrival Time:</b> " + arrivalTime + "</p>" +
                            "<p><b>Number of Seats:</b> " + numberOfSeats + "</p>" +
                            "<p><b>Total Fare:</b> $" + totalFare + "</p>" +
                            "<p>Thank you for choosing EasyTrip!</p>",
                    true // Enable HTML
            );

            mailSender.send(message);
            logger.info("Booking notification sent to passenger {} for bus {}", passengerEmail, busName);
        } catch (MessagingException e) {
            logger.error("Failed to send booking notification to passenger {}: {}", passengerEmail, e.getMessage());
        }
    }

    private void sendBookingEmailToOwner(
            String ownerEmail,
            String busName,
            String passengerName,
            String source,
            String destination,
            String departureTime,
            String arrivalTime,
            int numberOfSeats,
            double totalFare) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(ownerEmail);
            helper.setSubject("New Booking for Your Bus: " + busName);
            helper.setText(
                    "<h3>New Booking Notification</h3>" +
                            "<p>A new booking has been made for your bus: <b>" + busName + "</b></p>" +
                            "<p><b>Passenger:</b> " + passengerName + "</p>" +
                            "<p><b>Source:</b> " + source + "</p>" +
                            "<p><b>Destination:</b> " + destination + "</p>" +
                            "<p><b>Departure Time:</b> " + departureTime + "</p>" +
                            "<p><b>Arrival Time:</b> " + arrivalTime + "</p>" +
                            "<p><b>Number of Seats:</b> " + numberOfSeats + "</p>" +
                            "<p><b>Total Fare:</b> $" + totalFare + "</p>" +
                            "<p>Please ensure the bus is ready for the journey.</p>",
                    true // Enable HTML
            );

            mailSender.send(message);
            logger.info("Booking notification sent to owner {} for bus {}", ownerEmail, busName);
        } catch (MessagingException e) {
            logger.error("Failed to send booking notification to owner {}: {}", ownerEmail, e.getMessage());
        }
    }

    public void sendCancellationNotification(
            @NotBlank @Email String passengerEmail,
            @NotBlank @Email String ownerEmail,
            String busName,
            String passengerName,
            String source,
            String destination,
            String departureTime,
            String arrivalTime) {
        // Validate emails
        if (!isValidEmail(passengerEmail)) {
            logger.warn("Invalid passenger email address: {}. Skipping cancellation notification.", passengerEmail);
            return;
        }
        if (!isValidEmail(ownerEmail)) {
            logger.warn("Invalid owner email address: {}. Skipping cancellation notification.", ownerEmail);
            return;
        }

        // Send notification to passenger
        sendCancellationEmailToPassenger(passengerEmail, busName, passengerName, source, destination,
                departureTime, arrivalTime);

        // Send notification to bus owner
        sendCancellationEmailToOwner(ownerEmail, busName, passengerName, source, destination,
                departureTime, arrivalTime);
    }

    private void sendCancellationEmailToPassenger(
            String passengerEmail,
            String busName,
            String passengerName,
            String source,
            String destination,
            String departureTime,
            String arrivalTime) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(passengerEmail);
            helper.setSubject("Your Booking Cancellation for " + busName);
            helper.setText(
                    "<h3>Booking Cancellation Confirmation</h3>" +
                            "<p>Dear <b>" + passengerName + "</b>,</p>" +
                            "<p>Your booking for the bus <b>" + busName + "</b> has been cancelled.</p>" +
                            "<p><b>Details:</b></p>" +
                            "<p><b>Source:</b> " + source + "</p>" +
                            "<p><b>Destination:</b> " + destination + "</p>" +
                            "<p><b>Departure Time:</b> " + departureTime + "</p>" +
                            "<p><b>Arrival Time:</b> " + arrivalTime + "</p>" +
                            "<p>If you have any questions, please contact our support team.</p>",
                    true // Enable HTML
            );

            mailSender.send(message);
            logger.info("Cancellation notification sent to passenger {} for bus {}", passengerEmail, busName);
        } catch (MessagingException e) {
            logger.error("Failed to send cancellation notification to passenger {}: {}", passengerEmail, e.getMessage());
        }
    }

    private void sendCancellationEmailToOwner(
            String ownerEmail,
            String busName,
            String passengerName,
            String source,
            String destination,
            String departureTime,
            String arrivalTime) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(ownerEmail);
            helper.setSubject("Booking Cancelled for Your Bus: " + busName);
            helper.setText(
                    "<h3>Booking Cancellation Notification</h3>" +
                            "<p>A booking for your bus: <b>" + busName + "</b> has been cancelled.</p>" +
                            "<p><b>Passenger:</b> " + passengerName + "</p>" +
                            "<p><b>Source:</b> " + source + "</p>" +
                            "<p><b>Destination:</b> " + destination + "</p>" +
                            "<p><b>Departure Time:</b> " + departureTime + "</p>" +
                            "<p><b>Arrival Time:</b> " + arrivalTime + "</p>",
                    true // Enable HTML
            );

            mailSender.send(message);
            logger.info("Cancellation notification sent to owner {} for bus {}", ownerEmail, busName);
        } catch (MessagingException e) {
            logger.error("Failed to send cancellation notification to owner {}: {}", ownerEmail, e.getMessage());
        }
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}