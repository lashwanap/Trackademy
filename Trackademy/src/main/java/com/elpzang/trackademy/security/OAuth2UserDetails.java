package com.elpzang.trackademy.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Fait pont entre OAuth2User et UserDetails : les contrôleurs existants
 * lisent tous @AuthenticationPrincipal UserDetails pour l'isolation des
 * données par étudiant, ce pont leur permet de fonctionner identiquement
 * que la connexion vienne du formulaire ou de Google.
 */
public class OAuth2UserDetails implements OAuth2User, UserDetails {

    private final Map<String, Object> attributes;
    private final String email;

    public OAuth2UserDetails(Map<String, Object> attributes, String email) {
        this.attributes = attributes;
        this.email = email;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ETUDIANT"));
    }

    @Override
    public String getName() {
        return email;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }
}
