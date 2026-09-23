package com.elpzang.trackademy.service;

import com.elpzang.trackademy.entite.Etudiant;
import com.elpzang.trackademy.repository.EtudiantRepository;
import com.elpzang.trackademy.security.OAuth2UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        String email = oAuth2User.getAttribute("email");
        if (email == null) {
            throw new OAuth2AuthenticationException("Aucune adresse courriel fournie par le fournisseur OAuth2.");
        }

        String provider = userRequest.getClientRegistration().getRegistrationId().toUpperCase();

        etudiantRepository.findByEmail(email).orElseGet(() -> {
            Etudiant etudiant = new Etudiant();
            etudiant.setEmail(email);
            etudiant.setPrenom(oAuth2User.getAttribute("given_name"));
            etudiant.setNom(oAuth2User.getAttribute("family_name"));
            etudiant.setPhotoProfil(oAuth2User.getAttribute("picture"));
            etudiant.setProvider(provider);
            etudiant.setMotDePasse(null);
            return etudiantRepository.save(etudiant);
        });

        return new OAuth2UserDetails(oAuth2User.getAttributes(), email);
    }
}
