package com.restapikarkinos.WebappApi.controller;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.Optional;

//import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapikarkinos.WebappApi.model.Patient;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
  private static final String GET_ALL_PATIENTS_API = "https://8080-jade-hawk-96xoo4at.ws-us09.gitpod.io/api/patients";
  private static final String CREATE_PATIENT_API = "https://8080-jade-hawk-96xoo4at.ws-us09.gitpod.io/api/patients";
  private static final String GET_PATIENT_BY_FNAME_API = "https://8080-jade-hawk-96xoo4at.ws-us09.gitpod.io/api/findpatients/?firstName={firstName}";
  private static final String GET_PATIENT_BY_ID_API = "https://8080-jade-hawk-96xoo4at.ws-us09.gitpod.io/api/findpatients/{id}";
  private static final String UPDATE_PATIENT_API = "https://8080-jade-hawk-96xoo4at.ws-us09.gitpod.io/api/patients/{id}";  
  private static final String UPDATE_PATIENT_IMG_API = "https://8080-jade-hawk-96xoo4at.ws-us09.gitpod.io/api/photos/{id}";
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

         //Patient _patient =new Patient(firstName,lastName,specialization,phoneNumber,address,city,pincode);
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
      System.out.println("11");
      Map<String, String> param = new HashMap<>();
      System.out.println("22");
      System.out.println(firstName);
      param.put("firstName", firstName);
      System.out.println(param);
      ModelAndView modelAndView = new ModelAndView();
      System.out.println("55");
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
        System.out.println("1");
       
        System.out.println(firstName);
        System.out.println("2");
        System.out.println(lastName);
        System.out.println("3");
       Map<String, Long> param = new HashMap<>();
       System.out.println("4");
       param.put("id", id);
       System.out.println("5");
       ModelAndView modelAndView = new ModelAndView();
       System.out.println("6");
       //Patient _patient = new Patient(firstName,lastName,age,gender,city,pincode);
      Patient _patient = new Patient(firstName,lastName,age,gender,city,pincode,patient.getPhotos(),patient.getDocuments());
        restTemplate.put(UPDATE_PATIENT_API,_patient,param);
     
       System.out.println("8");
       modelAndView.setViewName("submit_patient");
       System.out.println("9");
       modelAndView.addObject("firstName", firstName);

       modelAndView.addObject("lastName", lastName);
       modelAndView.addObject("age", age);
       modelAndView.addObject("gender", gender);
       modelAndView.addObject("city", city);
       modelAndView.addObject("pincode", pincode);

       return modelAndView;
      }
      //***************************UPLOAD PATIENT PHOTO FORM************************************************* */

      @RequestMapping(path="/upload_image/{id}",method = RequestMethod.GET)
    public ModelAndView showupload_pic_page(@PathVariable(name = "id") Long id,@ModelAttribute Patient patient) {
      System.out.println("in upload image----------->");
      Map<String, Long> param = new HashMap<>();
      param.put("id", id);
      System.out.println("id is ------>"+id);
      Patient result = restTemplate.getForObject(GET_PATIENT_BY_ID_API,Patient.class,param);
      System.out.println("fist name is----------->");
      System.out.println(result.getFirstName());
      System.out.println(result.getLastName());
      System.out.println(result.getAge());
      System.out.println(result.getGender());
      System.out.println("photo is----------->");
      System.out.println(result.getPhotos());

    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName("upload_image");
    modelAndView.addObject("patient", result);
    System.out.println("result -------->"+result.getLastName());
    modelAndView.addObject("id", id);
    return modelAndView;
    }

    //***************************UPLOAD PATIENT PHOTO ****************************************************** */

    @RequestMapping(path="/photos/add/{id}",method=RequestMethod.POST)
    public ModelAndView savePatientpic(@ModelAttribute Patient patient,
    @RequestParam("image") MultipartFile multipartFile, 
    @PathVariable Long id){
      System.out.println("in add image----------->");
      Map<String, Long> param = new HashMap<>();
      param.put("id", id);
      System.out.println("id is ------>"+id);
      Patient patient1 = restTemplate.getForObject(GET_PATIENT_BY_ID_API,Patient.class,param);
      System.out.println(patient1.getFirstName());
      System.out.println("photo is----------->");
      System.out.println(patient1.getPhotos());
      ModelAndView modelAndView = new ModelAndView();
      System.out.println("**********"+multipartFile.getOriginalFilename());
      Patient _patient = new Patient(patient1.getFirstName(),patient1.getLastName(),patient1.getAge(),patient1.getGender(),patient1.getCity(),patient1.getPincode(),multipartFile.getOriginalFilename(),patient1.getDocuments());
      System.out.println("%----------->");
        restTemplate.put(UPDATE_PATIENT_IMG_API,_patient, param);

      modelAndView.setViewName("image_upload_message");
       return modelAndView;
    }


}