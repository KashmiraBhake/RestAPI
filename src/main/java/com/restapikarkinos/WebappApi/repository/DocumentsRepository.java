package com.restapikarkinos.WebappApi.repository;

import java.util.List;

import com.restapikarkinos.WebappApi.model.Documents;
import com.restapikarkinos.WebappApi.model.Patient;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentsRepository extends JpaRepository<Documents, Long> {

    List<Documents> findByPatients(Long patientId);

    List<Documents> findByPatients(Patient patientId);

    List<Documents> findByPatients_PatientId(Long patientId);

}
