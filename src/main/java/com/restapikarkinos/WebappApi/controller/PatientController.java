package com.restapikarkinos.WebappApi.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapikarkinos.WebappApi.model.Patient;


import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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
  private static final String GET_ALL_PATIENTS_API = "https://8080-bronze-beaver-3az3tiru.ws-us09.gitpod.io/api/patients";
  private static final String CREATE_PATIENT_API = "https://8080-bronze-beaver-3az3tiru.ws-us09.gitpod.io/patients";
  private static final String GET_PATIENT_BY_FNAME_API = "https://8080-bronze-beaver-3az3tiru.ws-us09.gitpod.io/api/findpatients/?firstName={firstName}";
  private static final String GET_PATIENT_BY_ID_API = "https://8080-bronze-beaver-3az3tiru.ws-us09.gitpod.io/api/findpatients/{id}";
  private static final String UPDATE_PATIENT_API = "https://8080-bronze-beaver-3az3tiru.ws-us09.gitpod.io/api/patients/{id}";  
  private static final String UPDATE_PATIENT_IMG_API = "https://8080-bronze-beaver-3az3tiru.ws-us09.gitpod.io/api/photos/";
  private static final String DELETE_PATIENT_API = "https://8080-bronze-beaver-3az3tiru.ws-us09.gitpod.io/api/patients/";
  static RestTemplate restTemplate = new RestTemplate();

    //***************************HOME BUTTON************************************************* */

    
    @RequestMapping("/")
    public ModelAndView home() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("home");
        return modelAndView;
    }

      //***************************NEW PATIENT FORM************************************************* */

      @RequestMapping("/new_patient")
      public ModelAndView new_patient() {
          ModelAndView modelAndView = new ModelAndView();
          modelAndView.setViewName("new_patient");
  
         return modelAndView;
      }
        //***************************NEW PATIENT SUBMIT************************************************ */
    @RequestMapping(path="/create_new_patient",method=RequestMethod.POST)
    private ModelAndView callCreatePatientsAPI(@ModelAttribute Patient patient,
        @RequestParam String firstName,
        @RequestParam String lastName,
        @RequestParam String age,
        @RequestParam String gender,
        @RequestParam String city,
        @RequestParam String pincode){

    
          ResponseEntity<Patient> patient2= restTemplate.postForEntity(CREATE_PATIENT_API,patient, Patient.class);
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
        //***************************VIEW ALL PATIENTS************************************************* */
    
    @RequestMapping(value="/view_all_patient",method=RequestMethod.GET)
    private ModelAndView callGetAllPatientsAPI() throws JsonMappingException, JsonProcessingException, RestClientException{

       HttpHeaders headers = new HttpHeaders();
       headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

      
       ObjectMapper mapper = new ObjectMapper();
       List<Patient> result = Arrays.asList(mapper.readValue(restTemplate.getForObject(GET_ALL_PATIENTS_API, String.class),Patient[].class));
       System.out.println(result.get(0).getFirstName());

       ModelAndView modelAndView = new ModelAndView();
       modelAndView.setViewName("view_all_patient");
       modelAndView.addObject("patients", result);
 
       return modelAndView;
    }
    //***************************SEARCH PATIENT FORM FIRST NAME*********************************************************** */
    @RequestMapping(path="/search_patient_form",method=RequestMethod.GET)
    public ModelAndView search_patient_form() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("search_patient_form");

        return modelAndView;
    }
    //***************************PATIENTS FIRST NAME********************************************************************* */
    @RequestMapping(path="/search_patient",method=RequestMethod.GET)
    private ModelAndView callGetPatientByFirstName(@RequestParam String firstName) throws JsonMappingException, JsonProcessingException, RestClientException{
   
      Map<String, String> param = new HashMap<>();
      param.put("firstName", firstName);
     
      ModelAndView modelAndView = new ModelAndView();
  
      ObjectMapper mapper = new ObjectMapper();
      List<Patient> patient = Arrays.asList(mapper.readValue(restTemplate.getForObject(GET_PATIENT_BY_FNAME_API, String.class,param),Patient[].class));
      System.out.println(patient.get(0).getFirstName());

      modelAndView.setViewName("search_patient_result");
      modelAndView.addObject("patients", patient);
      return modelAndView;
    }
     //***************************EDIT PATIENT FORM************************************************* */
     @RequestMapping(value = "/edit/{id}",method=RequestMethod.GET)
     public ModelAndView showEditPatientPage(@PathVariable(name = "id") Long id,@ModelAttribute("patient") Patient patient) throws JsonMappingException, JsonProcessingException, RestClientException {
     
       Map<String, Long> param = new HashMap<>();
       param.put("id", id);
      
       Patient result = restTemplate.getForObject(GET_PATIENT_BY_ID_API,Patient.class,param);
       
       System.out.println(result.getFirstName());
    
         ModelAndView modelAndView = new ModelAndView();
         modelAndView.setViewName("edit_patient");
         modelAndView.addObject("patient",result);
         modelAndView.addObject("firstName", result.getFirstName());
         modelAndView.addObject("lastName", result.getLastName());
         modelAndView.addObject("age", result.getAge());
         modelAndView.addObject("gender", result.getGender());
         modelAndView.addObject("city", result.getCity());
         modelAndView.addObject("pincode", result.getPincode());
         modelAndView.addObject("id", id);
        
         return modelAndView;
     }
      //***************************UPDATE PATIENT************************************************* */
      @RequestMapping(value ="/update/{id}",method=RequestMethod.POST)
      private ModelAndView callUpdatePatient(@PathVariable Long id, @ModelAttribute Patient patient,
      @RequestParam String firstName,
        @RequestParam String lastName,
        @RequestParam String age,
        @RequestParam String gender,
        @RequestParam String city,
        @RequestParam String pincode){
       
       
       Map<String, Long> param = new HashMap<>();
       param.put("id", id);

       ModelAndView modelAndView = new ModelAndView();
      Patient _patient = new Patient(firstName,lastName,age,gender,city,pincode,patient.getPhotos());
        restTemplate.put(UPDATE_PATIENT_API,_patient,param);
     
  
       modelAndView.setViewName("submit_patient");
       modelAndView.addObject("firstName", firstName);
       modelAndView.addObject("lastName", lastName);
       modelAndView.addObject("age", age);
       modelAndView.addObject("gender", gender);
       modelAndView.addObject("city", city);
       modelAndView.addObject("pincode", pincode);

       return modelAndView;
      }
      //***************************DELETE PATIENT ************************************************************ */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public String deletePatientApi(@PathVariable("id") Long id) {
      System.out.println(DELETE_PATIENT_API+id);
        restTemplate.delete(DELETE_PATIENT_API+id);
      
        return "redirect:/";       
    }
      //***************************UPLOAD PATIENT PHOTO FORM************************************************* */

      @RequestMapping(path="/upload_image/{id}",method = RequestMethod.GET)
    public ModelAndView showupload_pic_page(@PathVariable(name = "id") Long id,@ModelAttribute Patient patient) {
      Map<String, Long> param = new HashMap<>();
      param.put("id", id);
      Patient result = restTemplate.getForObject(GET_PATIENT_BY_ID_API,Patient.class,param);
   

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("upload_image");
    modelAndView.addObject("patient", result);
    modelAndView.addObject("id", id);
    return modelAndView;
    }

    //***************************UPLOAD PATIENT PHOTO ****************************************************** */

    @RequestMapping(path="/photos/add/{id}",method=RequestMethod.POST)
    public ModelAndView savePatientpic(@ModelAttribute Patient patient, @RequestParam("file") MultipartFile file, 
    @PathVariable Long id) throws IOException{

    Resource filebody = file.getResource();
    LinkedMultiValueMap<String, Object> fullfile = new LinkedMultiValueMap<>();
    fullfile.add("file", filebody);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
    HttpEntity<LinkedMultiValueMap<String, Object>> httpEntity = new HttpEntity<>(fullfile, httpHeaders);
    ResponseEntity<String>  resp = restTemplate.postForEntity(UPDATE_PATIENT_IMG_API+id, httpEntity, String.class);
    System.out.println(resp.getBody());

      ModelAndView modelAndView = new ModelAndView();
      modelAndView.setViewName("image_upload_message");
      return modelAndView;
    }

}