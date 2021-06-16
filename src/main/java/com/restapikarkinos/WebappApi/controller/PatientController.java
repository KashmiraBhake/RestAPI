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

import com.restapikarkinos.WebappApi.model.Documents;
import com.restapikarkinos.WebappApi.model.Patient;
import com.restapikarkinos.WebappApi.repository.DocumentsRepository;
import com.restapikarkinos.WebappApi.repository.PatientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
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

  @Autowired
  DocumentsRepository documentsRepository;

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

  @GetMapping("/findpatients/{id}")
  public ResponseEntity<Patient> findById(@ModelAttribute Patient patient,@PathVariable("id") String id) {
    try {
        Optional<Patient> patientData = patientRepository.findById(patient.getId());
      if (patientData.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      Patient _patient = patientData.get();
      return new ResponseEntity<>(patientRepository.save(_patient), HttpStatus.OK);
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

  @DeleteMapping("/patients/{id}")
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
    if(multipartFile.isEmpty()) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request must contain file");
    }
    Optional<Patient> patientData = patientRepository.findById(id);
    Patient _patient = patientData.get();
    _patient.setPhotos(fileName);
    System.out.println(fileName);

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

   @PostMapping("/docs/{id}")
    public ResponseEntity <String> savedoc(@RequestParam("document") MultipartFile multipartFile,
    @PathVariable (name = "id") Patient id) 
    throws IOException{
    
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        documentsRepository.save(new Documents(fileName, id));
       List<Documents> documentsData = documentsRepository.findByPatients(id);
     
        Documents _documents = documentsData.get(0);
        Documents savedDocuments = documentsRepository.save(_documents);

        String uploadDir = "./patient-docs/" + savedDocuments.getPatients().getId();

        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);}

        try(InputStream inputStream = multipartFile.getInputStream()){
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {        
            throw new IOException("Could not save file: " + fileName, ioe);
        }


            
        return ResponseEntity.ok("working");
    }

}
