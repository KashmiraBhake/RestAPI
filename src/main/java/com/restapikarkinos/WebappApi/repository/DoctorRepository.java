package com.restapikarkinos.WebappApi.repository;

import java.util.List;
import java.util.Optional;

import com.restapikarkinos.WebappApi.model.Doctor;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface DoctorRepository extends MongoRepository<Doctor, String> {
    // @Query(value = "{$or:[{specialization:{$regex:?0,$options:'i'}},{city:{$regex:?0,$options:'i'}}]}")
     List<Doctor> findBySpecializationAndCity(String specialization, String city);
    List<Doctor> findAll();
    //List<Doctor> findBySpecialization(String specialization);
  
}