package com.elpzang.trackademy.repository;

import com.elpzang.trackademy.entite.Cours;
import com.elpzang.trackademy.entite.Etudiant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {

    List<Cours> findByEtudiant(Etudiant etudiant);

    long countByEtudiant(Etudiant etudiant);
}
