package com.paulo_motors.demo.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmailService {

    @Value("${BREVO_API_KEY:}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    @Async
    public void sendRentalConfirmation(
            String toEmail,
            String clientName,
            String carDetails,
            String startDateStr,
            String endDateStr,
            BigDecimal totalValue,
            String carColor,
            String carMotor
    ) {
        try {
            System.out.println("=== INICIANDO ENVIO DE EMAIL VIA API HTTP BREVO ===");
            System.out.println("Para: " + toEmail);
            System.out.println("Thread: " + Thread.currentThread().getName());

            if (apiKey == null || apiKey.isEmpty()) {
                System.err.println("❌ BREVO_API_KEY não foi configurada no Railway!");
                return;
            }

            String url = "https://api.brevo.com/v3/smtp/email";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("api-key", apiKey);

            String corpo = String.format("""
                Olá %s!
                
                ✅ SUA RESERVA FOI CONFIRMADA!
                
                🚗 Veículo: %s
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
                    clientName, carDetails, startDateStr, endDateStr, totalValue, carColor, carMotor
            );

            Map<String, Object> body = new HashMap<>();

            Map<String, String> sender = new HashMap<>();
            sender.put("name", "Paulo Motor's");
            sender.put("email", "paullomagio@gmail.com");
            body.put("sender", sender);

            Map<String, String> to = new HashMap<>();
            to.put("email", toEmail);
            to.put("name", clientName);
            body.put("to", List.of(to));

            body.put("subject", "✅ Confirmação de Reserva - Paulo Motor's");
            body.put("textContent", corpo);

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

            System.out.println("✅ EMAIL ENVIADO VIA API COM SUCESSO!");
            System.out.println("Resposta do Brevo: " + response.getBody());

        } catch (Exception e) {
            System.err.println("❌ ERRO AO ENVIAR EMAIL VIA API: " + e.getClass().getName());
            System.err.println("Mensagem: " + e.getMessage());
            e.printStackTrace();
        }
    }
}