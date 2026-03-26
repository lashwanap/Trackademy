package com.elpzang.trackademy.repository;

import com.elpzang.trackademy.entite.Devoir;
import com.elpzang.trackademy.entite.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DevoirRepository extends JpaRepository<Devoir, Long> {

    List<Devoir> findByStatutNot(String statut);

    long countByStatutNot(String statut);

    List<Devoir> findByCoursEtudiantAndStatutNot(Etudiant etudiant, String statut);

    long countByCoursEtudiantAndStatutNot(Etudiant etudiant, String statut);

    List<Devoir> findByCoursEtudiant(Etudiant etudiant);
}
