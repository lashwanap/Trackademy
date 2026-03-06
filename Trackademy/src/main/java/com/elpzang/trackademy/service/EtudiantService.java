package com.elpzang.trackademy.service;

import com.elpzang.trackademy.entite.Etudiant;
import com.elpzang.trackademy.repository.EtudiantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EtudiantService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    public List<Etudiant> findAll() {
        return etudiantRepository.findAll();
    }

    public Etudiant save(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    public void deleteById(int id) {
        etudiantRepository.deleteById(id);
    }
}
