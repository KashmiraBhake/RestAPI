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
import com.restapikarkinos.WebappApi.PaginatedResponse;
import com.restapikarkinos.WebappApi.model.Doctor;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;


@Controller
//@RequestMapping("/doctor")
public class DoctorController {
  private static final String GET_ALL_DOCTORS_API = "https://8080-blue-condor-789glqj9.ws-us08.gitpod.io/api/doctors";
  private static final String CREATE_DOCTOR_API = "https://8080-blue-condor-789glqj9.ws-us08.gitpod.io/api/doctors";
  private static final String GET_DOCTOR_BY_SP_CT_API = "https://8080-blue-condor-789glqj9.ws-us08.gitpod.io/api/finddoctors/?specialization={specialization}&city={city}";
  private static final String GET_DOCTOR_BY_ID_API = "https://8080-blue-condor-789glqj9.ws-us08.gitpod.io/api/finddoctors/{id}";
  private static final String UPDATE_DOCTOR_API = "https://8080-blue-condor-789glqj9.ws-us08.gitpod.io/api/doctors/{id}";  
  private static final String DELETE_DOCTOR_API = "https://8080-blue-condor-789glqj9.ws-us08.gitpod.io/api/doctors/";
  private static final String PAGINATION_DOCTOR_API = "https://8080-blue-condor-789glqj9.ws-us08.gitpod.io/api/doctors/";
  static RestTemplate restTemplate = new RestTemplate();

    //***************************NEW DOCTOR FORM************************************************* */

    @RequestMapping("/new_doctor")
    public ModelAndView new_doctor() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new_doctor");

       return modelAndView;
    }

    //***************************NEW DOCTOR SUBMIT************************************************ */
    @RequestMapping(path="/create_new_doctor",method=RequestMethod.POST)
    private ModelAndView callCreateDoctorsAPI(@ModelAttribute Doctor doctor,
        @RequestParam String firstName,
        @RequestParam String lastName,
        @RequestParam String specialization,
        @RequestParam String phoneNumber,
        @RequestParam String address,
        @RequestParam String city,
        @RequestParam String pincode){

         //Doctor _doctor =new Doctor(firstName,lastName,specialization,phoneNumber,address,city,pincode);
          ResponseEntity<Doctor> doctor2= restTemplate.postForEntity(CREATE_DOCTOR_API,doctor, Doctor.class);
          System.out.println(doctor2.getBody());
          ModelAndView modelAndView = new ModelAndView();

          modelAndView.setViewName("submit_doctor");
              modelAndView.addObject("firstName", firstName);
              modelAndView.addObject("lastName", lastName);
              modelAndView.addObject("specialization", specialization);
              modelAndView.addObject("phoneNumber", phoneNumber);
              modelAndView.addObject("address", address);
              modelAndView.addObject("city", city);
              modelAndView.addObject("pincode", pincode);

          return modelAndView;
        }

    //***************************VIEW ALL DOCTORS************************************************* */
    
    @RequestMapping(value="/view_all_doctor",method=RequestMethod.GET)
     private ModelAndView callGetAllDoctorsAPI() throws JsonMappingException, JsonProcessingException, RestClientException{

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        //Doctor[] result = restTemplate.getForObject(GET_ALL_DOCTORS_API,Doctor[].class);
        ObjectMapper mapper = new ObjectMapper();
        List<Doctor> result = Arrays.asList(mapper.readValue(restTemplate.getForObject(GET_ALL_DOCTORS_API, String.class),Doctor[].class));
        System.out.println(result.get(0).getFirstName());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view_all_doctor");
        modelAndView.addObject("doctors", result);
  
        return modelAndView;
     }

     @RequestMapping(path = "/view_all_doctor/{page}", method = RequestMethod.GET)
    public ModelAndView viewAllDoctorapi(@PathVariable("page") Integer page) {
      System.out.println("***********************************************");
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        System.out.println("***********************************************");
        //ObjectMapper mapper = new ObjectMapper();
        ParameterizedTypeReference<PaginatedResponse<Doctor>> responseType = new ParameterizedTypeReference<PaginatedResponse<Doctor>>() { };
        ResponseEntity<PaginatedResponse<Doctor>> result = restTemplate.exchange(PAGINATION_DOCTOR_API+ "?page="+(page-1)+"&size=10", HttpMethod.GET, null, responseType);
        System.out.println(result.getBody());
        List<Doctor> doctors = result.getBody().getContent();
        System.out.println("1111111111111111111111111111111111111111111111111111111111111111111");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("view_all_doctor");
        modelAndView.addObject("doctors", doctors);
        System.out.println("---------------------------------------------");
        modelAndView.addObject("number", result.getBody().getNumber()+1);
        modelAndView.addObject("totalPages", result.getBody().getTotalPages());
        modelAndView.addObject("currentPage" , page);
    
        return modelAndView;
    }
      //***************************SEARCH DOCTOR FORM SPECIALIZATION & CITY************************************************* */
      @RequestMapping(path="/search_doctor_form",method=RequestMethod.GET)
      public ModelAndView search_doctor_form() {
          ModelAndView modelAndView = new ModelAndView();
          modelAndView.setViewName("search_doctor_form");
  
          return modelAndView;
      }

      //***************************DOCTORS SPECIALIZATION & CITY************************************************* */
      @RequestMapping(path="/search_doctor",method=RequestMethod.GET)
      private ModelAndView callGetDoctorBySpcAndCtAPI(@RequestParam String specialization,@RequestParam String city) throws JsonMappingException, JsonProcessingException, RestClientException{
        System.out.println("11");
        Map<String, String> param = new HashMap<>();
        System.out.println("22");
        System.out.println(specialization);
        param.put("specialization", specialization);
        System.out.println(param);
        param.put("city", city);
        System.out.println("44");
        System.out.println(param);
        ModelAndView modelAndView = new ModelAndView();
        System.out.println("55");
        ObjectMapper mapper = new ObjectMapper();
        List<Doctor> doctor = Arrays.asList(mapper.readValue(restTemplate.getForObject(GET_DOCTOR_BY_SP_CT_API, String.class,param),Doctor[].class));
        System.out.println(doctor.get(0).getFirstName());

        modelAndView.setViewName("search_doctor_result");
        modelAndView.addObject("doctors", doctor);
        return modelAndView;
      }

      //***************************EDIT DOCTOR FORM************************************************* */
      @RequestMapping(value = "/edit1/{id}",method=RequestMethod.GET)
      public ModelAndView showEditDoctorPage(@PathVariable(name = "id") String id,@ModelAttribute("doctor") Doctor doctor) throws JsonMappingException, JsonProcessingException, RestClientException {
      
        Map<String, String> param = new HashMap<>();
        param.put("id", id);
       
        Doctor result = restTemplate.getForObject(GET_DOCTOR_BY_ID_API,Doctor.class,param);
        
        System.out.println(result.getFirstName());
     
          ModelAndView modelAndView = new ModelAndView();
          modelAndView.setViewName("edit_doctor");
          modelAndView.addObject("doctor",result);
          modelAndView.addObject("firstName", result.getFirstName());
          modelAndView.addObject("lastName", result.getLastName());
        modelAndView.addObject("specialization", result.getSpecialization());
        modelAndView.addObject("phoneNumber", result.getPhoneNumber());
        modelAndView.addObject("address", result.getAddress());
        modelAndView.addObject("city", result.getCity());
        modelAndView.addObject("pincode", result.getPincode());
          modelAndView.addObject("id", id);
         
          return modelAndView;
      }
       //***************************UPDATE DOCTOR************************************************* */
       @RequestMapping(value ="/update1/{id}",method=RequestMethod.POST)
       private ModelAndView callUpdateDoctor(@PathVariable String id, @ModelAttribute Doctor doctor,
       @RequestParam String firstName,
       @RequestParam String lastName,
       @RequestParam String specialization,
       @RequestParam String phoneNumber,
       @RequestParam String address,
       @RequestParam String city,
       @RequestParam String pincode){
         System.out.println("1");
        
         System.out.println(firstName);
         System.out.println("2");
         System.out.println(lastName);
         System.out.println("3");
        Map<String, String> param = new HashMap<>();
        System.out.println("4");
        param.put("id", id);
        System.out.println("5");
        ModelAndView modelAndView = new ModelAndView();
        System.out.println("6");
        Doctor _doctor = new Doctor(firstName,lastName,specialization,phoneNumber,address,city,pincode);
         restTemplate.put(UPDATE_DOCTOR_API,_doctor,param);
      
        System.out.println("8");
        modelAndView.setViewName("submit_doctor");
        System.out.println("9");
        modelAndView.addObject("firstName", firstName);

        modelAndView.addObject("lastName", lastName);
        modelAndView.addObject("specialization", specialization);
        modelAndView.addObject("phoneNumber", phoneNumber);
        modelAndView.addObject("address", address);
        modelAndView.addObject("city", city);
        modelAndView.addObject("pincode", pincode);

        return modelAndView;
       }
       //***************************DELETE DOCTOR************************************************* */
       @RequestMapping(value = "/delete1/{id}", method = RequestMethod.GET)
    public String deleteDoctorApi(@PathVariable("id") String id) {
      System.out.println(DELETE_DOCTOR_API+id);
        restTemplate.delete(DELETE_DOCTOR_API+id);
      
        return "redirect:/";       
    }

}
