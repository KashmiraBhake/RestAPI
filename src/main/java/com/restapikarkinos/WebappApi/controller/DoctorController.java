package com.restapikarkinos.WebappApi.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.restapikarkinos.WebappApi.model.Doctor;


import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/doctor")
public class DoctorController {
  private static final String GET_ALL_DOCTORS_API = "https://8080-copper-cockroach-65w3fq4v.ws-us09.gitpod.io/api/doctors";
  private static final String CREATE_DOCTOR_API = "https://8080-copper-cockroach-65w3fq4v.ws-us09.gitpod.io/api/doctors";
  private static final String GET_DOCTOR_BY_SP_CT_API = "https://8080-copper-cockroach-65w3fq4v.ws-us09.gitpod.io/api/finddoctors/?specialization={specialization}&city={city}";

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
    
    @RequestMapping(value="/view_all_doc",method=RequestMethod.GET)
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
        // Doctor doctor = restTemplate.getForObject(GET_DOCTOR_BY_SP_CT_API,Doctor.class,param);
        // System.out.println(restTemplate.getForObject(GET_DOCTOR_BY_SP_CT_API,Doctor.class,param));
        modelAndView.setViewName("search_doctor_result");
        modelAndView.addObject("doctors", doctor);
        return modelAndView;
      }
}
