package com.restapikarkinos.WebappApi.repository;

import java.util.List;

import com.restapikarkinos.WebappApi.model.Doctor;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DoctorRepository extends MongoRepository<Doctor, String> {

    List<Doctor> findBySpecializationAndCity(String specialization, String city);

    List<Doctor> findAll();

}