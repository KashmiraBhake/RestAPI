package com.restapikarkinos.WebappApi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.restapikarkinos.WebappApi.model.Patient;
import com.restapikarkinos.WebappApi.repository.PatientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class PatientController {
    
  @Autowired
  PatientRepository patientRepository;

  @GetMapping("/patients")
  public ResponseEntity<List<Patient>> getAllPatient() {
  try {
      List<Patient> patients = new ArrayList<Patient>();
    
      patientRepository.findAll().forEach(patients::add);
      
      if (patients.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(patients, HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping("/patients")
  public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient) {
  try {
      System.out.println("hello");
      Patient _patient = patientRepository.save(patient);
      return new ResponseEntity<>(_patient, HttpStatus.CREATED);
    } catch (Exception e) {
        return new ResponseEntity<>(patient, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/findpatients")
  public ResponseEntity<List<Patient>> findByFirstName(@ModelAttribute Patient patient) {
    try {
        List<Patient> patients = patientRepository.findByFirstName(patient.getFirstName());
    
        if (patients.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(patients, HttpStatus.OK);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }

  @PutMapping("/patients/{id}")
  public ResponseEntity<Patient> updatePatient(@PathVariable("id") Long id, @RequestBody Patient patient) {
    Optional<Patient> patientData = patientRepository.findById(id);

    if (patientData.isPresent()) {
      Patient _patient = patientData.get();
      _patient.setFirstName(patient.getFirstName());
      _patient.setLastName(patient.getLastName());
      _patient.setAge(patient.getAge());
      _patient.setGender(patient.getGender());
      _patient.setCity(patient.getCity());
      _patient.setPincode(patient.getPincode());
      return new ResponseEntity<>(patientRepository.save(_patient), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("/patients/{id}")
  public ResponseEntity<HttpStatus> deletePatient(@PathVariable("id") Long id) {
    try {
        patientRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
      }
  }

  @PostMapping("/photos/{id}")
  public ResponseEntity<String> savePatientpic(@RequestParam("image") MultipartFile multipartFile, 
  @PathVariable(name = "id") Long id)
  throws IOException {
  
    System.out.println(id);
    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    System.out.println(fileName);
    if(multipartFile.isEmpty()) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request must contain file");
    }
    Optional<Patient> patientData = patientRepository.findById(id);
    Patient _patient = patientData.get();
    _patient.setPhotos(fileName);

    _patient.setFirstName(_patient.getFirstName());
    _patient.setLastName(_patient.getLastName());
    _patient.setAge(_patient.getAge());
    _patient.setGender(_patient.getGender());
    _patient.setCity(_patient.getCity());
    _patient.setPincode(_patient.getPincode());

    Patient savedPatient = patientRepository.save(_patient);

    String uploadDir = "./patient-photos/" + savedPatient.getId();
      
    Path uploadPath = Paths.get(uploadDir);
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }
    try(InputStream inputStream = multipartFile.getInputStream()){
      Path filePath = uploadPath.resolve(fileName);
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException ioe) {        
        throw new IOException("Could not save image file: " + fileName, ioe);
    }
  
    return ResponseEntity.ok("working");
   }

}
