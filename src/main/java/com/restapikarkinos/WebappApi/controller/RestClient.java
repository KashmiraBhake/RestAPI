// package com.restapikarkinos.WebappApi.controller;

// import java.util.Arrays;
// //import java.util.HashMap;
// import java.util.List;
// //import java.util.Map;

// //import javax.print.attribute.standard.MediaSizeName;

// import com.restapikarkinos.WebappApi.model.Doctor;

// //import org.springframework.http.HttpEntity;
// import org.springframework.http.HttpHeaders;
// //import org.springframework.http.HttpMethod;
// import org.springframework.http.MediaType;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.client.RestTemplate;

// public class RestClient {
//     private static final String GET_ALL_DOCTORS_API = "https://localhost:8080/api/doctors";
//    // private static final String GET_DOCTOR_BY_ID_API = "https://localhost:8080/api/doctors/{id}";
//    // private static final String CREATE_DOCTOR_API = "https://localhost:8080/api/doctors";
//    // private static final String UPDATE_DOCTOR_API = "https://localhost:8080/api/doctors/{id}";
//    // private static final String DELETE_DOCTOR_API = "https://localhost:8080/api/doctors/{id}";

//     static RestTemplate restTemplate = new RestTemplate();
//     // public static void main(String[] args){

//     //     callGetAllDoctorsAPI();
//     // }
//     @SuppressWarnings("unchecked")
//      private static void callGetAllDoctorsAPI(){

//         HttpHeaders headers = new HttpHeaders();
//         headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

//         //HttpEntity<String> entity = new HttpEntity<>("parameters",headers);
//         ResponseEntity<List<Doctor>> result = restTemplate.getForObject(GET_ALL_DOCTORS_API,ResponseEntity.class);

//         System.out.println(result);
//      }
//     //  private static void callGetDoctorByIdAPI(){
//     //      Map<String, String> param = new HashMap<>();
//     //      param.put("id", "1");//give id of doc here instead od 1

//     //      Doctor doctor = restTemplate.getForObject(GET_DOCTOR_BY_ID_API,Doctor.class,param);
//     //      System.out.println(doctor.getFirstName());
//     //      System.out.println(doctor.getLastName());
//     //      System.out.println(doctor.getSpecialization());
//     //      System.out.println(doctor.getPhoneNumber());
//     //      System.out.println(doctor.getAddress());
//     //      System.out.println(doctor.getCity());
//     //      System.out.println(doctor.getPincode());
//     //  }

//     //  private static void callCreateDoctorAPI() {
//     //      Doctor doctor = new Doctor("Ramesh", "Jadhav", "Oncology", "5544554455", "ttn,ngp-15","Nagpur","440022");
//     //     ResponseEntity<Doctor> doctor2= restTemplate.postForEntity(CREATE_DOCTOR_API,doctor, Doctor.class);
//     //     System.out.println(doctor2.getBody());
//     //  }

//     //  private static void callUpdateuserAPI(){}


    
// }
