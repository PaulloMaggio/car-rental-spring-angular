package com.paulo_motors.demo.services;

import com.paulo_motors.demo.entities.Car;
import com.paulo_motors.demo.entities.Rental;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendRentalConfirmation(String toEmail, String clientName, Rental rental, Car car) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("contato@paulomotors.com");
            message.setTo(toEmail);
            message.setSubject("✅ Confirmação de Reserva - Paulo Motor's");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

            String corpo = String.format("""
                Olá %s!
                
                ✅ SUA RESERVA FOI CONFIRMADA!
                
                🚗 Veículo: %s %s
                📅 Data de retirada: %s
                📅 Data de devolução: %s
                💰 Valor total: R$ %.2f
                🎨 Cor: %s
                🔧 Motor: %s
                
                ---
                Meu nome é Paulo Maggio, sou desenvolvedor Full Stack e este sistema faz parte do meu portfólio.
                
                Para conhecer mais trabalhos:
                LinkedIn: www.linkedin.com/in/paulo-maggio-1738491a7
                GitHub: https://github.com/paulomaggio
                
                Atenciosamente,
                Paulo Maggio
                """,
                    clientName,
                    car.getBrand(), car.getModel(),
                    rental.getStartDate().atZone(ZoneId.systemDefault()).format(formatter),
                    rental.getEndDate().atZone(ZoneId.systemDefault()).format(formatter),
                    rental.getTotalValue(),
                    car.getColor(),
                    car.getMotor()
            );

            message.setText(corpo);
            mailSender.send(message);
            System.out.println("E-mail enviado com sucesso para: " + toEmail);

        } catch (Exception e) {
            System.err.println("ERRO AO ENVIAR E-MAIL: " + e.getMessage());
            
        }
    }
}