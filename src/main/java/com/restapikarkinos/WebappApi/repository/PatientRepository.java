package com.restapikarkinos.WebappApi.repository;

import java.util.List;
import java.util.Optional;

import com.restapikarkinos.WebappApi.model.Patient;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findByFirstName(String firstName);

    List<Patient> findAll();

    Optional<Patient> findById(Long patientId);
}
