package com.restapikarkinos.WebappApi.controller;

import java.util.List;

import com.restapikarkinos.WebappApi.model.Doctor;
import com.restapikarkinos.WebappApi.repository.DoctorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    
    @Autowired
    DoctorRepository doctorRepository;

    //get information from view to controller using a form
    @GetMapping("/new_doctor")
    public String new_doctor() {

       return "new_doctor";
    }

    @GetMapping("/view_all_doctor")
    public List<Doctor> getDoctors() {

      
      
    }

    @PostMapping("/create_new_doctor")
    public ResponseEntity<Doctor> createDoctor(@RequestBody Doctor doctor) {
    try {
        System.out.println("hello");
        Doctor _doctor = doctorRepository.save(new Doctor(doctor.getFirstName(), doctor.getLastName(), doctor.getSpecialization(), doctor.getPhoneNumber(), doctor.getAddress(), doctor.getCity(), doctor.getPincode()));
        return new ResponseEntity<>(_doctor, HttpStatus.CREATED);
      } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }


}
