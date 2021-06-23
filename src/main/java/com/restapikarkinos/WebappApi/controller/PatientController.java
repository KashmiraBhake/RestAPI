package com.restapikarkinos.WebappApi.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapikarkinos.WebappApi.PaginatedResponse;
import com.restapikarkinos.WebappApi.model.Documents;
import com.restapikarkinos.WebappApi.model.Patient;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class PatientController {
  // private static final String GET_ALL_PATIENTS_API =
  // "https://8080-apricot-tiger-sjwjyfje.ws-us08.gitpod.io/api/patients";
  private static final String CREATE_PATIENT_API = "https://8080-apricot-tiger-sjwjyfje.ws-us08.gitpod.io/api/patients";
  private static final String GET_PATIENT_BY_FNAME_API = "https://8080-apricot-tiger-sjwjyfje.ws-us08.gitpod.io/api/findpatients/?firstName={firstName}";
  private static final String GET_PATIENT_BY_ID_API = "https://8080-apricot-tiger-sjwjyfje.ws-us08.gitpod.io/api/findpatients/{patientId}";
  private static final String UPDATE_PATIENT_API = "https://8080-apricot-tiger-sjwjyfje.ws-us08.gitpod.io/api/patients/{patientId}";
  private static final String UPDATE_PATIENT_IMG_API = "https://8080-apricot-tiger-sjwjyfje.ws-us08.gitpod.io/api/photos/";
  private static final String DELETE_PATIENT_API = "https://8080-apricot-tiger-sjwjyfje.ws-us08.gitpod.io/api/patients/";
  private static final String UPDATE_PATIENT_DOC_API = "https://8080-apricot-tiger-sjwjyfje.ws-us08.gitpod.io/api/docs/";
  private static final String GET_DOCUMENT_BY_ID_API = "https://8080-apricot-tiger-sjwjyfje.ws-us08.gitpod.io/api/documents/{patientId}";
  private static final String GET_DOCUMENT_BY_PATIENTS_API = "https://8080-apricot-tiger-sjwjyfje.ws-us08.gitpod.io/api/document/patient/{patientId}";
  private static final String PAGINATION_PATIENT_API = "https://8080-apricot-tiger-sjwjyfje.ws-us08.gitpod.io/api/patients/";
 // private static final String DOCUMENT_DOWNLOAD_API = "https://8080-apricot-tiger-sjwjyfje.ws-us08.gitpod.io/api/docs";

  static RestTemplate restTemplate = new RestTemplate();

  // ***************************HOME BUTTON************************************************* */

  @RequestMapping("/")
  public ModelAndView home() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("home");
    return modelAndView;
  }

  // ***************************NEW PATIENT FORM************************************************* */

  @RequestMapping("/new_patient")
  public ModelAndView new_patient() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("new_patient");

    return modelAndView;
  }

  // ***************************NEW PATIENT SUBMIT************************************************ */
  @RequestMapping(path = "/create_new_patient", method = RequestMethod.POST)
  private ModelAndView callCreatePatientsAPI(@ModelAttribute Patient patient, @RequestParam String firstName,
      @RequestParam String lastName, @RequestParam String age, @RequestParam String gender, @RequestParam String city,
      @RequestParam String pincode) {

    ResponseEntity<Patient> patient2 = restTemplate.postForEntity(CREATE_PATIENT_API, patient, Patient.class);
    System.out.println(patient2.getBody());
    ModelAndView modelAndView = new ModelAndView();

    modelAndView.setViewName("submit_patient");
    modelAndView.addObject("firstName", firstName);
    modelAndView.addObject("lastName", lastName);
    modelAndView.addObject("age", age);
    modelAndView.addObject("gender", gender);
    modelAndView.addObject("city", city);
    modelAndView.addObject("pincode", pincode);

    return modelAndView;
  }
  // ***************************VIEW ALL PATIENTS************************************************* */

  @RequestMapping(path = "/view_all_patient/{page}", method = RequestMethod.GET)
  public ModelAndView viewAllPatientapi(@PathVariable("page") Integer page) {
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    ParameterizedTypeReference<PaginatedResponse<Patient>> responseType = new ParameterizedTypeReference<PaginatedResponse<Patient>>() {
    };
    ResponseEntity<PaginatedResponse<Patient>> result = restTemplate
        .exchange(PAGINATION_PATIENT_API + "?page=" + (page - 1) + "&size=10", HttpMethod.GET, null, responseType);
    System.out.println(result.getBody());
    List<Patient> patients = result.getBody().getContent();
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("view_all_patient");
    modelAndView.addObject("patients", patients);
    modelAndView.addObject("number", result.getBody().getNumber() + 1);
    modelAndView.addObject("totalPages", result.getBody().getTotalPages());
    modelAndView.addObject("currentPage", page);

    return modelAndView;
  }

  // ***************************SEARCH PATIENT FORM FIRST NAME*********************************************************** */
  @RequestMapping(path = "/search_patient_form", method = RequestMethod.GET)
  public ModelAndView search_patient_form() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("search_patient_form");

    return modelAndView;
  }

  // ***************************PATIENTS FIRST NAME********************************************************************* */
  @RequestMapping(path = "/search_patient", method = RequestMethod.GET)
  private ModelAndView callGetPatientByFirstName(@RequestParam String firstName)
      throws JsonMappingException, JsonProcessingException, RestClientException {
    System.out.println("----->1");
    Map<String, String> param = new HashMap<>();
    System.out.println("----->2");
    param.put("firstName", firstName);
    System.out.println("----->3");
    ModelAndView modelAndView = new ModelAndView();
    System.out.println("----->4");
    ObjectMapper mapper = new ObjectMapper();
    System.out.println("----->5");
    List<Patient> patient = Arrays.asList(
        mapper.readValue(restTemplate.getForObject(GET_PATIENT_BY_FNAME_API, String.class, param), Patient[].class));
    System.out.println("----->6");
    System.out.println(patient.get(0).getFirstName());
    System.out.println("----->7");
    modelAndView.setViewName("search_patient_result");
    modelAndView.addObject("patients", patient);
    return modelAndView;
  }

  // ***************************EDIT PATIENT FORM************************************************* */
  @RequestMapping(value = "/edit/{patientId}", method = RequestMethod.GET)
  public ModelAndView showEditPatientPage(@PathVariable(name = "patientId") Long patientId,
      @ModelAttribute("patient") Patient patient)
      throws JsonMappingException, JsonProcessingException, RestClientException {

    Map<String, Long> param = new HashMap<>();
    param.put("patientId", patientId);

    Patient result = restTemplate.getForObject(GET_PATIENT_BY_ID_API, Patient.class, param);

    System.out.println(result.getFirstName());

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("edit_patient");
    modelAndView.addObject("patient", result);
    modelAndView.addObject("firstName", result.getFirstName());
    modelAndView.addObject("lastName", result.getLastName());
    modelAndView.addObject("age", result.getAge());
    modelAndView.addObject("gender", result.getGender());
    modelAndView.addObject("city", result.getCity());
    modelAndView.addObject("pincode", result.getPincode());
    modelAndView.addObject("id", patientId);

    return modelAndView;
  }

  // ***************************UPDATE PATIENT************************************************* */
  @RequestMapping(value = "/update/{patientId}", method = RequestMethod.POST)
  private ModelAndView callUpdatePatient(@PathVariable Long patientId, @ModelAttribute Patient patient,
      @RequestParam String firstName, @RequestParam String lastName, @RequestParam String age,
      @RequestParam String gender, @RequestParam String city, @RequestParam String pincode) {

    Map<String, Long> param = new HashMap<>();
    param.put("patientId", patientId);

    ModelAndView modelAndView = new ModelAndView();
    Patient _patient = new Patient(firstName, lastName, age, gender, city, pincode, patient.getPhotos());
    restTemplate.put(UPDATE_PATIENT_API, _patient, param);

    modelAndView.setViewName("submit_patient");
    modelAndView.addObject("firstName", firstName);
    modelAndView.addObject("lastName", lastName);
    modelAndView.addObject("age", age);
    modelAndView.addObject("gender", gender);
    modelAndView.addObject("city", city);
    modelAndView.addObject("pincode", pincode);

    return modelAndView;
  }

  // ***************************DELETE PATIENT ************************************************************ */
  @RequestMapping(value = "/delete/{patientId}", method = RequestMethod.GET)
  public String deletePatientApi(@PathVariable("patientId") Long patientId) {
    System.out.println(DELETE_PATIENT_API + patientId);
    restTemplate.delete(DELETE_PATIENT_API + patientId);

    return "redirect:/";
  }
  // ***************************UPLOAD PATIENT PHOTO FORM************************************************* */

  @RequestMapping(path = "/upload_image/{patientId}", method = RequestMethod.GET)
  public ModelAndView showupload_pic_page(@PathVariable(name = "patientId") Long patientId,
      @ModelAttribute Patient patient) {
    Map<String, Long> param = new HashMap<>();
    param.put("patientId", patientId);
    Patient result = restTemplate.getForObject(GET_PATIENT_BY_ID_API, Patient.class, param);

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("upload_image");
    modelAndView.addObject("patient", result);
    modelAndView.addObject("patientId", patientId);
    return modelAndView;
  }

  // ***************************UPLOAD PATIENT PHOTO ****************************************************** */

  @RequestMapping(path = "/photos/add/{patientId}", method = RequestMethod.POST)
  public ModelAndView savePatientpic(@ModelAttribute Patient patient, @RequestParam("file") MultipartFile file,
      @PathVariable Long patientId) throws IOException {

    Resource filebody = file.getResource();
    LinkedMultiValueMap<String, Object> fullfile = new LinkedMultiValueMap<>();
    fullfile.add("file", filebody);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
    HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(fullfile, httpHeaders);
    ResponseEntity<String> resp = restTemplate.postForEntity(UPDATE_PATIENT_IMG_API + patientId, httpEntity,
        String.class);
    System.out.println(resp.getBody());

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("image_upload_message");
    return modelAndView;
  }

  // ***************************UPLOAD PATIENT DOCUMENT FORM************************************************* */

  @RequestMapping(path = "/upload_document/{patientId}", method = RequestMethod.GET)
  public ModelAndView showupload_pdoc_page(@PathVariable(name = "patientId") Long patientId) {
    Map<String, Long> param = new HashMap<>();
    param.put("patientId", patientId);
    System.out.println(patientId);
    Patient result = restTemplate.getForObject(GET_PATIENT_BY_ID_API, Patient.class, param);

    System.out.println(result.getFirstName());
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("upload_document");
    modelAndView.addObject("patient", result);
    modelAndView.addObject("patientId", patientId);
    return modelAndView;
  }
  // ***************************UPLOAD PATIENT DOCUMENT ****************************************************** */

  @RequestMapping(path = "/docs/add/{patientId}", method = RequestMethod.POST)
  public ModelAndView savePatientdoc(@ModelAttribute Documents documents,
      @RequestParam("document") MultipartFile multipartFile, @PathVariable("patientId") Long patientId)
      throws IOException {
    Map<String, Long> param = new HashMap<>();
    param.put("patientId", patientId);
    System.out.println(param);
    ResponseEntity<Documents> result = restTemplate.getForEntity(GET_DOCUMENT_BY_ID_API, Documents.class, param);
    System.out.println(result.getBody());
    Resource filebody = multipartFile.getResource();
    LinkedMultiValueMap<String, Object> fullfile = new LinkedMultiValueMap<>();
    fullfile.add("document", filebody);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
    HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(fullfile, httpHeaders);
    ResponseEntity<String> resp = restTemplate.postForEntity(UPDATE_PATIENT_DOC_API + patientId, httpEntity,
        String.class);

    System.out.println(resp.getBody());

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("document_upload_message");
    return modelAndView;
  }

  // ***************************CHECK IF DOCUMENTS ARE PRESENT OR NOT************************************************* */
  @RequestMapping(path = "/preview/{patientId}", method = RequestMethod.GET)
  public String previewProfile(@ModelAttribute("documents") Documents documents,
      @PathVariable("patientId") Long patientId)
      throws JsonMappingException, JsonProcessingException, RestClientException {
    System.out.println("#############");
    Map<String, Long> param = new HashMap<>();
    System.out.println("#############1");
    param.put("patientId", patientId);
    System.out.println("#############2");
    System.out.println(param);
    System.out.println("#############3");
    ObjectMapper mapper = new ObjectMapper();
    System.out.println("----->5");
    // List<Patient> patient =
    // Arrays.asList(mapper.readValue(restTemplate.getForObject(GET_PATIENT_BY_FNAME_API,
    // String.class,param),Patient[].class));
    // System.out.println("----->6");
    List<Documents> result = Arrays.asList(mapper
        .readValue(restTemplate.getForObject(GET_DOCUMENT_BY_PATIENTS_API, String.class, param), Documents[].class));
    ;
    System.out.println("#############4");

    if (result.isEmpty()) {
      System.out.println("------======");
      System.out.println("------======------");
      return "redirect:/patient_details/{patientId}";
    } else {
      System.out.println(result.get(0).getDocName());
      return "redirect:/patient_details_doc/{patientId}";
    }

  }

  // ***************************VIEW WITH DOCUMENTS************************************************* */
  @RequestMapping(path = "/patient_details_doc/{patientId}", method = RequestMethod.GET)
  public ModelAndView viewProfileWithDoc(@ModelAttribute("documents") Documents documents, @PathVariable Long patientId,
      @ModelAttribute("patient") Patient patient)
      throws JsonMappingException, JsonProcessingException, RestClientException {
    ModelAndView modelAndView = new ModelAndView();
    HttpHeaders headers = new HttpHeaders();
    // System.out.println(documents.getPatients());
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

    System.out.println("1111");
    Map<String, Long> param = new HashMap<>();
    param.put("patientId", patientId);
    System.out.println("2222");
    System.out.println(param);
    ObjectMapper mapper = new ObjectMapper();

    System.out.println("3333");
    // List<Documents> documentsData =
    // restTemplate.getForObject(GET_DOCUMENT_BY_PATIENTS_API,List.class,param);
    List<Documents> documentsData = Arrays.asList(mapper
        .readValue(restTemplate.getForObject(GET_DOCUMENT_BY_PATIENTS_API, String.class, param), Documents[].class));
    System.out.println("4444");
    Patient result = restTemplate.getForObject(GET_PATIENT_BY_ID_API, Patient.class, param);
    System.out.println("5555");
    System.out.println(documentsData.get(0).getDocName());

    modelAndView.setViewName("patient_details_doc");
    modelAndView.addObject("patient", result);
    modelAndView.addObject("photos", result.getPhotos());
    modelAndView.addObject("firstName", result.getFirstName());
    modelAndView.addObject("lastName", result.getLastName());
    modelAndView.addObject("age", result.getAge());
    modelAndView.addObject("gender", result.getGender());
    modelAndView.addObject("city", result.getCity());
    modelAndView.addObject("pincode", result.getPincode());
    modelAndView.addObject("patientId", patientId);
    List<String> docs = new ArrayList<>();
    for (Documents docList : documentsData) {
      docs.add(docList.getDocName());
      modelAndView.addObject("docs", docs);
    }
    // modelAndView.addObject("docs", documentsData.getDocName());
    return modelAndView;
  }

  // ***************************VIEW WITHOUT DOCUMENTS************************************************* */
  @RequestMapping(path = "/patient_details/{patientId}", method = RequestMethod.GET)
  public ModelAndView viewProfile(@PathVariable Long patientId, @ModelAttribute("patient") Patient patient) {
    ModelAndView modelAndView = new ModelAndView();

    Map<String, Long> param = new HashMap<>();
    param.put("patientId", patientId);

    Patient result = restTemplate.getForObject(GET_PATIENT_BY_ID_API, Patient.class, param);

    modelAndView.setViewName("patient_details");
    modelAndView.addObject("patient", result);
    modelAndView.addObject("photos", result.getPhotos());
    modelAndView.addObject("firstName", result.getFirstName());
    modelAndView.addObject("lastName", result.getLastName());
    modelAndView.addObject("age", result.getAge());
    modelAndView.addObject("gender", result.getGender());
    modelAndView.addObject("city", result.getCity());
    modelAndView.addObject("pincode", result.getPincode());
    modelAndView.addObject("patientId", patientId);
    return modelAndView;
  }
}
