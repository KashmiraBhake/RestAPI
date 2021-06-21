package com.restapikarkinos.WebappApi.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.io.FileReader;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.google.gson.Gson;
import com.restapikarkinos.WebappApi.model.Documents;
import com.restapikarkinos.WebappApi.model.Patient;
import com.restapikarkinos.WebappApi.repository.DocumentsRepository;
import com.restapikarkinos.WebappApi.repository.PatientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FileUtils;

@RestController
@RequestMapping("/api")
public class PatientController {

  @Autowired
  PatientRepository patientRepository;

  @Autowired
  DocumentsRepository documentsRepository;

  // @GetMapping("/patients")
  // public ResponseEntity<List<Patient>> getAllPatient() {
  // try {
  // List<Patient> patients = new ArrayList<Patient>();

  // patientRepository.findAll().forEach(patients::add);

  // if (patients.isEmpty()) {
  // return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  // }
  // return new ResponseEntity<>(patients, HttpStatus.OK);
  // } catch (Exception e) {
  // return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
  // }
  // }

  @GetMapping("/patients")
  public Page<Patient> getAllPatient(Pageable pageable) {
    Page<Patient> patients = patientRepository.findAll(pageable);
    return patients;
  }

  @PostMapping("/patients")
  public ResponseEntity<Patient> createPatient(@Valid @RequestBody Patient patient) {
    try {
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

  @GetMapping("/findpatients/{patientId}")
  public ResponseEntity<Patient> findByPatientId(@ModelAttribute Patient patient,
      @PathVariable("patientId") Long patientId) {
    try {
      Optional<Patient> patientData = patientRepository.findById(patient.getPatientId());
      if (patientData.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      Patient _patient = patientData.get();
      return new ResponseEntity<>(patientRepository.save(_patient), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PutMapping("/patients/{patientId}")
  public ResponseEntity<Patient> updatePatient(@PathVariable("patientId") Long patientId,
      @RequestBody Patient patient) {
    Optional<Patient> patientData = patientRepository.findById(patientId);

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

  @DeleteMapping("/patients/{patientId}")
  public ResponseEntity<HttpStatus> deletePatient(@PathVariable("patientId") Long patientId) {
    try {
      patientRepository.deleteById(patientId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(value = "/photos/{patientId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public String savePatientpic(@RequestParam("file") MultipartFile multipartFile,
      @PathVariable(name = "patientId") Long patientId) throws IOException {

    System.out.println(patientId);
    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    // if(multipartFile.isEmpty()) {
    // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Request
    // must contain file");
    // }
    Optional<Patient> patientData = patientRepository.findById(patientId);
    Patient _patient = patientData.get();
    _patient.setPhotos(fileName);

    Patient savedPatient = patientRepository.save(_patient);
    String uploadDir = "./patient-photos/" + savedPatient.getPatientId();
    Path uploadPath = Paths.get(uploadDir);
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }
    try (InputStream inputStream = multipartFile.getInputStream()) {
      Path filePath = uploadPath.resolve(fileName);
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException ioe) {
      throw new IOException("Could not save image file: " + fileName, ioe);
    }
    // return new ResponseEntity<>(fileName, HttpStatus.OK);
    return fileName;

  }

  // @RequestMapping(value = "/image/{patientId}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)

  // public void getImage(HttpServletResponse response,  @PathVariable(name = "patientId") Long patientId,
  // @ModelAttribute Patient patient) throws IOException {
  //   System.out.println("****************1");
  //   System.out.println(patientId);
  //   var imgFile = new ClassPathResource("/workspace/RestAPI/patient-photos/" + patientId + "/" + "images.png");
  //   System.out.println(patient.getPhotos());
  //   response.setContentType(MediaType.IMAGE_JPEG_VALUE);
  //   StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
  // }

  // @GetMapping("/{filename}")
  //   public ResponseEntity<byte[]> getImage(@PathVariable("filename") String filename) {
  //       byte[] image = new byte[0];
  //       try {
  //           image = FileUtils.readFileToByteArray(new File("/workspace/RestAPI/patient-photos"+filename));
  //       } catch (IOException e) {
  //           throw new ImageNotFoundException();
  //       }
  //       return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
  //   }

  @PostMapping("/docs/{patientId}")
  public String savedoc(@RequestParam("document") MultipartFile multipartFile,
      @PathVariable(name = "patientId") Patient patientId) throws IOException {

    String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
    documentsRepository.save(new Documents(fileName, patientId));
    List<Documents> documentsData = documentsRepository.findByPatients(patientId);

    Documents _documents = documentsData.get(0);
    Documents savedDocuments = documentsRepository.save(_documents);
    String uploadDir = "./patient-docs/" + savedDocuments.getPatients().getPatientId();
    Path uploadPath = Paths.get(uploadDir);
    if (!Files.exists(uploadPath)) {
      Files.createDirectories(uploadPath);
    }

    try (InputStream inputStream = multipartFile.getInputStream()) {
      Path filePath = uploadPath.resolve(fileName);
      Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
    } catch (IOException ioe) {
      throw new IOException("Could not save file: " + fileName, ioe);
    }
    return "working";

  }

  @GetMapping("/documents/{patientId}")
  public ResponseEntity<Documents> findByDocId(@ModelAttribute Documents documents,
      @PathVariable("patientId") Long patientId) {
    try {
      Optional<Documents> documentsData = documentsRepository.findByDocId(patientId);
      if (documentsData.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      Documents _documents = documentsData.get();
      return new ResponseEntity<>(documentsRepository.save(_documents), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/document/patient/{patientId}")
  public ResponseEntity<List<Documents>> findByPatients(@PathVariable("patientId") Long patientId) {

    try {
      List<Documents> documentsData = documentsRepository.findByPatients_PatientId(patientId);

      System.out.println("********************************" + documentsData.size());
      if (documentsData.size() == 0) {

        return new ResponseEntity<>(documentsData, HttpStatus.OK);
      }

      return new ResponseEntity<>(documentsData, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping("/docs/{patientId}/{doc}")
  public void downloadDoc(HttpServletResponse response, @PathVariable Long patientId, @PathVariable String doc,
      @ModelAttribute("patient") Patient patient) throws Exception {
    System.out.println("*************1");
    List<Documents> result = documentsRepository.findByPatients_PatientId(patientId);
    Documents documents = result.get(0);
    System.out.println("***********************************2");
    System.out.println("--------------" + patientId);
    File file = new File("/workspace/RestAPI/patient-docs" + "/" + patientId + "/" + doc);
    // Path path = Paths.get("/workspace/RestAPI/patient-docs" + "/" + patientId +
    // "/" + doc);
    System.out.println("***********************************3");
    response.setContentType("application/octet-stream");
    String headerKey = "Content-Disposition";
    String headerValue = "inline; filename=" + doc;
    response.setHeader(headerKey, headerValue);
    ServletOutputStream outputStream = response.getOutputStream();
    BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
    System.out.println("***********************************4");

    byte[] buffer = new byte[8192];
    System.out.println("***********************************5");
    int bytesRead = -1;
    System.out.println("***********************************6");
    while ((bytesRead = inputStream.read(buffer)) != -1) {
      outputStream.write(buffer, 0, bytesRead);
    }
    System.out.println("***********************************7");
    inputStream.close();
    outputStream.close();

    // return ResponseEntity.ok()
    // .contentType(MediaType.parseMediaType(contentType))
    // .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
    // resource.getFilename() + "\"")
    // .body(resource);
    // } else {
    // return ResponseEntity.notFound().build();
    // }

  }

  @RequestMapping("/upload")
  public void upload() throws IOException {
    FileReader reader = new FileReader("/workspace/RestAPI/src/main/resources/Patient.json");
    BufferedReader br = new BufferedReader(reader);
    StringBuffer sbr = new StringBuffer();
    String line;

    while ((line = br.readLine()) != null) {
      Gson gson = new Gson();
      Patient patient = gson.fromJson(line, Patient.class);
      patientRepository.save(patient);
    }
  }

}