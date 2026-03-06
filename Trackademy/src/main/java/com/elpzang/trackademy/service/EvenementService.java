package com.elpzang.trackademy.service;

import com.elpzang.trackademy.entite.Evenement;
import com.elpzang.trackademy.repository.EvenementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EvenementService {

    @Autowired
    private EvenementRepository evenementRepository;

    public List<Evenement> findAll() {
        return evenementRepository.findAll();
    }

    public List<Evenement> findByMois(int annee, int mois) {
        LocalDate debut = LocalDate.of(annee, mois, 1);
        LocalDate fin = debut.withDayOfMonth(debut.lengthOfMonth());
        return evenementRepository.findByDateBetween(debut, fin);
    }

    public Evenement save(Evenement evenement) {
        return evenementRepository.save(evenement);
    }

    public void deleteById(Long id) {
        evenementRepository.deleteById(id);
    }
}
