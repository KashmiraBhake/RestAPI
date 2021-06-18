package com.restapikarkinos.WebappApi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import com.restapikarkinos.WebappApi.model.Doctor;
import com.restapikarkinos.WebappApi.repository.DoctorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class DoctorController {
    
  @Autowired
  DoctorRepository doctorRepository;

  // @GetMapping("/doctors")
  // public ResponseEntity<List<Doctor>> getAllDoctors() {
  // try {
  //     List<Doctor> doctors = new ArrayList<Doctor>();
    
  //     doctorRepository.findAll().forEach(doctors::add);
      
  //     if (doctors.isEmpty()) {
  //       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  //     }
  //     return new ResponseEntity<>(doctors, HttpStatus.OK);
  //   } catch (Exception e) {
  //       return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  //   }
  // }
  @GetMapping("/doctors/{page}")
  public ResponseEntity<Map<String, Object>> getAllDoctors(@PathVariable("page") int page) {
    try {
     // List<Patient> pa = new ArrayList<Patient>();
      Pageable pageable = PageRequest.of(page, 10);
      Page<Doctor> doctors = doctorRepository.findAll(pageable);
      Map<String, Object> response = new HashMap<>();
      response.put("patients", doctors.getContent());
      response.put("number", doctors.getNumber()+1);
      response.put("totalPages", doctors.getTotalPages());
      response.put("currentPage", page);
      
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/doctors")
  public ResponseEntity<Doctor> createDoctor(@Valid @RequestBody Doctor doctor) {
  try {
      System.out.println("hello");
      Doctor _doctor = doctorRepository.save(doctor);
      return new ResponseEntity<>(_doctor, HttpStatus.CREATED);
    } catch (Exception e) {
        return new ResponseEntity<>(doctor, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/finddoctors/{id}")
  public ResponseEntity<Doctor> findById(@ModelAttribute Doctor doctor,@PathVariable("id") String id) {
    try {
        Optional<Doctor> doctorData = doctorRepository.findById(doctor.getId());
      if (doctorData.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      Doctor _doctor = doctorData.get();
      return new ResponseEntity<>(doctorRepository.save(_doctor), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/finddoctors")
  public ResponseEntity<List<Doctor>> findBySpecialization(@ModelAttribute Doctor doctor) {
    try {
        List<Doctor> doctors = doctorRepository.findBySpecializationAndCity(doctor.getSpecialization(), doctor.getCity());
    
        if (doctors.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(doctors, HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }

  @PutMapping("/doctors/{id}")
  public ResponseEntity<Doctor> updateDoctor(@PathVariable("id") String id, @RequestBody Doctor doctor) {
    Optional<Doctor> doctorData = doctorRepository.findById(id);

    if (doctorData.isPresent()) {
      Doctor _doctor = doctorData.get();
      _doctor.setFirstName(doctor.getFirstName());
      _doctor.setLastName(doctor.getLastName());
      _doctor.setSpecialization(doctor.getSpecialization());
      _doctor.setPhoneNumber(doctor.getPhoneNumber());
      _doctor.setAddress(doctor.getAddress());
      _doctor.setCity(doctor.getCity());
      _doctor.setPincode(doctor.getPincode());
      return new ResponseEntity<>(doctorRepository.save(_doctor), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping("/doctors/{id}")
  public ResponseEntity<HttpStatus> deleteDoctor(@PathVariable("id") String id) {
    try {
        doctorRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }

}
