package com.elpzang.trackademy.repository;

import com.elpzang.trackademy.entite.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {
}
