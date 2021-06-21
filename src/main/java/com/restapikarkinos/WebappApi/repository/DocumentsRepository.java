package com.restapikarkinos.WebappApi.repository;

import java.util.List;
import java.util.Optional;

import com.restapikarkinos.WebappApi.model.Documents;
import com.restapikarkinos.WebappApi.model.Patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DocumentsRepository extends JpaRepository<Documents, Long> {

    List<Documents> findByPatients(Long patientId);
 
 Optional<Documents> findByDocId(Long patientId);

//Optional<Documents> findByDocId(Object id);

//@Query("select * from document where patient_id = ?0")
// List<Documents> findByPatientId(Patient id);

List<Documents> findByPatients(Patient patientId);

// Optional<Documents> findByPatientId(Long id);

List<Documents> findByPatients_PatientId(Long patientId);
    
 }
