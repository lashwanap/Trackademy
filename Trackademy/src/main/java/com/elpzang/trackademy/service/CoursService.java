package com.elpzang.trackademy.service;

import com.elpzang.trackademy.entite.Cours;
import com.elpzang.trackademy.repository.CoursRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoursService {

    @Autowired
    private CoursRepository coursRepository;

    public List<Cours> findAll() {
        return coursRepository.findAll();
    }

    public long count() {
        return coursRepository.count();
    }

    public Cours save(Cours cours) {
        return coursRepository.save(cours);
    }

    public void deleteById(Long id) {
        coursRepository.deleteById(id);
    }

    public Cours findById(Long id) {
        return coursRepository.findById(id).orElse(null);
    }
}
