package com.elpzang.trackademy.service;

import com.elpzang.trackademy.entite.Etudiant;
import com.elpzang.trackademy.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TrackademyUserDetailsService implements UserDetailsService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Etudiant etudiant = etudiantRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Aucun compte trouvé pour : " + email));

        return User.builder()
                .username(etudiant.getEmail())
                .password(etudiant.getMotDePasse())
                .roles("ETUDIANT")
                .build();
    }
}
