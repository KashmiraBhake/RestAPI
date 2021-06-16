package com.restapikarkinos.WebappApi.controller;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.restapikarkinos.WebappApi.model.Doctor;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/doctor")
public class DoctorController {
  private static final String GET_ALL_DOCTORS_API = "https://8080-emerald-whale-xtd7vopp.ws-us09.gitpod.io/api/doctors";
  private static final String CREATE_DOCTOR_API = "https://8080-emerald-whale-xtd7vopp.ws-us09.gitpod.io/api/doctors";

    static RestTemplate restTemplate = new RestTemplate();

    //***************************NEW DOCTOR FORM************************************************* */

    @RequestMapping("/new_doctor")
    public ModelAndView new_doctor() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new_doctor");

       return modelAndView;
    }

    //***************************NEW DOCTOR SUBMIT************************************************ */
    // @RequestMapping(path="/create_new_doctor",method=RequestMethod.POST)
    // public ModelAndView create_new_doctor(@Valid @ModelAttribute Doctor doctor, BindingResult bindingResult,
    //     @RequestParam String firstName,
    //     @RequestParam String lastName,
    //     @RequestParam String specialization,
    //     @RequestParam String phoneNumber,
    //     @RequestParam String address,
    //     @RequestParam String city,
    //     @RequestParam String pincode) {
    //       ModelAndView modelAndView = new ModelAndView();

    //         if (bindingResult.hasErrors()) {       
        
    //             System.out.println(bindingResult);
                
    //             modelAndView.setViewName("new_doctor");
    //             return modelAndView;
    //         }
    //     modelAndView.setViewName("submit_doctor");
    //     modelAndView.addObject("firstName", firstName);
    //     modelAndView.addObject("lastName", lastName);
    //     modelAndView.addObject("specialization", specialization);
    //     modelAndView.addObject("phoneNumber", phoneNumber);
    //     modelAndView.addObject("address", address);
    //     modelAndView.addObject("city", city);
    //     modelAndView.addObject("pincode", pincode);

    //     return modelAndView;
    // }

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


}
