package com.restapikarkinos.WebappApi.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotBlank;

@Document(collection = "patient")
public class Patient {
  @Id
  private String id;

//   @NotBlank
//   @Pattern(regexp = "^[a-zA-Z0-9'.]{3,50}$" , message = "only alphabets, numbers, apostrophe, dot characters are allowed, Firstname must be 3-50 characters long")
  private String firstName;

//   @NotBlank
//   @Pattern(regexp = "^[a-zA-Z0-9'.]{3,50}$",message = "only alphabets, numbers, apostrophe, dot characters are allowed, Firstname must be 3-50 characters long")
  private String lastName;

  //@NotNull @Min(0) @Max(125)
  private int age;

  //@NotBlank
  private String gender;

  //@Pattern(regexp = "^[A-Za-z]{3,100}$", message = "Please enter between {min} and {max}")
  private String city;
  
  //@Pattern(regexp="^[1-9][0-9]{5}$", message="Pincode is invalid")
  private String pincode;

//   private String photos;

//   private List<String> docs;

  public Patient() {

  }

  public Patient(String firstName, String lastName, int age, String gender, String city, String pincode) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.gender = gender;
    this.city = city;
    this.pincode = pincode;
    // this.photos = photos;
    // this.docs=docs;
  }

  public String getId() {
    return id;
  }

  
  public void setId(String id) {
    this.id = id;
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

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
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

//   public String getPhotos() {
//     return photos;
//   }

//   public void setPhotos(String photos) {
//     this.photos = photos;
//   }

//   public List<String> getDocs() {
//     return docs;
//   }

//   public void setDocs(List<String> docs) {
//     this.docs = docs;
//   }

//   @Transient
//     public String getPhotosImagePath() {
//         if (photos == null || id == null) return null;
//         return "/patient-photos/" + id + "/" + photos;
//     }

//     @Transient
//     public String getDocsFilePath() {
//       return "/patient-docs/" + id + "/";
//     }
}
