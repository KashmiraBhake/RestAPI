// package com.restapikarkinos.WebappApi.controller;

// import java.util.ArrayList;
// import java.util.List;
// import java.util.Optional;

// import com.restapikarkinos.WebappApi.model.Patient;
// import com.restapikarkinos.WebappApi.repository.PatientRepository;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.CrossOrigin;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// @CrossOrigin(origins = "https://kumquat-narwhal-6msaatvi.ws-us09.gitpod.io")
// @RestController
// //@EnableAutoConfiguration
// @RequestMapping("/api")
// public class PatientController {
    
//   @Autowired
//   PatientRepository patientRepository;

//   @GetMapping("/patients")
//   public ResponseEntity<List<Patient>> getAllPatient() {
//   try {
//       List<Patient> patients = new ArrayList<Patient>();
    
//       patientRepository.findAll().forEach(patients::add);
      
//       if (patients.isEmpty()) {
//         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//       }
//       return new ResponseEntity<>(patients, HttpStatus.OK);
//     } catch (Exception e) {
//         return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//     }
//   }

//   @PostMapping("/patients")
//   public ResponseEntity<Patient> createPatient(@RequestBody Patient patient) {
//   try {
//       System.out.println("hello");
//       Patient _patient = patientRepository.save(patient);
//       return new ResponseEntity<>(_patient, HttpStatus.CREATED);
//     } catch (Exception e) {
//         return new ResponseEntity<>(patient, HttpStatus.INTERNAL_SERVER_ERROR);
//     }
//   }

//   @GetMapping("/finddoctors")
//   public ResponseEntity<List<Patient>> findByFirstName(@ModelAttribute Patient patient) {
//     try {
//         List<Patient> patients = patientRepository.findByFirstName(patient.getFirstName());
    
//         if (patients.isEmpty()) {
//           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//         }
//         return new ResponseEntity<>(patients, HttpStatus.OK);
//       } catch (Exception e) {
//         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//       }
//   }

//   @PutMapping("/patients/{id}")
//   public ResponseEntity<Patient> updatePatient(@PathVariable("id") String id, @RequestBody Patient patient) {
//     Optional<Patient> patientData = patientRepository.findById(id);

//     if (patientData.isPresent()) {
//       Patient _patient = patientData.get();
//       _patient.setFirstName(patient.getFirstName());
//       _patient.setLastName(patient.getLastName());
//       _patient.setAge(patient.getAge());
//       _patient.setGender(doctor.getGender());
//       _patient.setCity(patient.getCity());
//       _patient.setPincode(patient.getPincode());
//       return new ResponseEntity<>(patientRepository.save(_patient), HttpStatus.OK);
//     } else {
//       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//     }
//   }

// //   @GetMapping("/patients/{id}")
// //   public ResponseEntity<HttpStatus> deletePatient(@PathVariable("id") String id) {
// //     try {
// //         patientRepository.deleteById(id);
// //         return new ResponseEntity<>(HttpStatus.NO_CONTENT);
// //       } catch (Exception e) {
// //         return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
// //       }
// //   }

// }
