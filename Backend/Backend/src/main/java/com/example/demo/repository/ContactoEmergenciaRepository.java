package com.example.demo.repository;

import com.example.demo.model.ContactoEmergencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactoEmergenciaRepository extends JpaRepository<ContactoEmergencia, Long> {
}
