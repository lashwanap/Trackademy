package com.elpzang.trackademy.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private static final Logger log = LoggerFactory.getLogger(EmailService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.host:}")
    private String mailHost;

    @Value("${spring.mail.username:}")
    private String mailFrom;

    public void envoyerLienReinitialisation(String destinataire, String lien) {
        if (mailHost.isBlank()) {
            log.info("SMTP non configuré — lien de réinitialisation pour {} : {}", destinataire, lien);
            return;
        }

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            if (!mailFrom.isBlank()) {
                message.setFrom(mailFrom);
            }
            message.setTo(destinataire);
            message.setSubject("Trackademy — Réinitialisation de votre mot de passe");
            message.setText("""
                    Bonjour,

                    Une demande de réinitialisation de mot de passe a été faite pour votre compte Trackademy.

                    Cliquez sur le lien suivant pour choisir un nouveau mot de passe (valide 1 heure) :
                    %s

                    Si vous n'êtes pas à l'origine de cette demande, ignorez ce courriel.
                    """.formatted(lien));
            mailSender.send(message);
        } catch (Exception e) {
            log.warn("Échec de l'envoi du courriel de réinitialisation à {} — lien : {}", destinataire, lien, e);
        }
    }
}
