package com.restapikarkinos.WebappApi.repository;


import com.restapikarkinos.WebappApi.model.NewPatient;

import org.springframework.data.jpa.repository.JpaRepository;
 
public interface NewPatientRepository extends JpaRepository<NewPatient, Long> {
   
}
