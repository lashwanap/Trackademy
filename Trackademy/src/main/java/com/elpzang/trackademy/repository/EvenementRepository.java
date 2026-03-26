package com.elpzang.trackademy.repository;

import com.elpzang.trackademy.entite.Etudiant;
import com.elpzang.trackademy.entite.Evenement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EvenementRepository extends JpaRepository<Evenement, Long> {

    List<Evenement> findByDateBetween(LocalDate debut, LocalDate fin);

    List<Evenement> findByEtudiantAndDateBetween(Etudiant etudiant, LocalDate debut, LocalDate fin);
}
