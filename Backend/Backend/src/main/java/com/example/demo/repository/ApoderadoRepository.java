package com.example.demo.repository;

import com.example.demo.model.Apoderado;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface ApoderadoRepository extends JpaRepository<Apoderado, Long> {
    Optional<Apoderado> findByDni(String dni);
    List<Apoderado> findAll();
}
