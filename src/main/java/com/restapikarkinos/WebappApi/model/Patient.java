package com.restapikarkinos.WebappApi.model;

import java.io.Serializable;

public class Patient implements Serializable{

 
  private Long patientId;

  private String firstName;

  private String lastName;

  private String age;

  private String gender;

  private String city;

  private String pincode;

  private String photos;


  protected Patient() {

  }
    
  public Patient(String firstName, String lastName, String age, String gender, String city, String pincode, String photos) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.gender = gender;
    this.city = city;
    this.pincode = pincode;
    this.photos = photos;
  
  }

  public Long getPatientId() {
    return patientId;
  }

  public void setPatientId(Long patientId) {
    this.patientId = patientId;
  }
  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }
  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
  public String getAge() {
    return age;
  }
  public void setAge(String age) {
    this.age = age;
  }
  public String getGender() {
    return gender;
  }
  public void setGender(String gender) {
    this.gender = gender;
  }
  public String getCity() {
    return city;
  }
  public void setCity(String city) {
    this.city = city;
  }
  public String getPincode() {
    return pincode;
  }
  public void setPincode(String pincode) {
    this.pincode = pincode;
  }
  public String getPhotos() {
    return photos;
  }
  public void setPhotos(String photos) {
    this.photos = photos;
  }
}