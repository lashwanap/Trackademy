package com.elpzang.trackademy.service;

import com.elpzang.trackademy.entite.Devoir;
import com.elpzang.trackademy.repository.DevoirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DevoirService {

    @Autowired
    private DevoirRepository devoirRepository;

    public List<Devoir> findAll() {
        return devoirRepository.findAll();
    }

    public List<Devoir> findPending() {
        return devoirRepository.findByStatutNot("TERMINE");
    }

    public long countPending() {
        return devoirRepository.countByStatutNot("TERMINE");
    }

    public Devoir save(Devoir devoir) {
        return devoirRepository.save(devoir);
    }

    public void deleteById(Long id) {
        devoirRepository.deleteById(id);
    }

    public Devoir findById(Long id) {
        return devoirRepository.findById(id).orElse(null);
    }

    public void changerStatut(Long id, String statut) {
        Devoir d = findById(id);
        if (d != null) {
            d.setStatut(statut);
            devoirRepository.save(d);
        }
    }
}
