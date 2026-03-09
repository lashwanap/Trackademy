package com.elpzang.trackademy.config;

import com.elpzang.trackademy.entite.Etudiant;
import com.elpzang.trackademy.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Encodage automatique des mots de passe en clair au démarrage.
 * S'exécute une seule fois : les hachages BCrypt (commençant par $2a$)
 * sont détectés et ignorés, donc la migration est idempotente.
 */
@Component
public class PasswordMigrationRunner implements CommandLineRunner {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        List<Etudiant> etudiants = etudiantRepository.findAll();
        int count = 0;

        for (Etudiant etudiant : etudiants) {
            String motDePasse = etudiant.getMotDePasse();

            // Ignorer les mots de passe déjà encodés en BCrypt ($2a$ ou $2b$)
            if (motDePasse != null && !motDePasse.startsWith("$2")) {
                etudiant.setMotDePasse(passwordEncoder.encode(motDePasse));
                etudiantRepository.save(etudiant);
                count++;
            }
        }

        if (count > 0) {
            System.out.println("✔ [Trackademy] Migration BCrypt : " + count
                    + " mot(s) de passe encodé(s) avec succès.");
        } else {
            System.out.println("✔ [Trackademy] Migration BCrypt : tous les mots de passe sont déjà encodés.");
        }
    }
}
