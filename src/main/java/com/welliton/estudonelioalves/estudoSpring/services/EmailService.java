package com.welliton.estudonelioalves.estudoSpring.services;

import org.springframework.mail.SimpleMailMessage;

import com.welliton.estudonelioalves.estudoSpring.domain.Pedido;

public interface EmailService {
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);
}