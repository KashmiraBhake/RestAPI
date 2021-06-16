package com.restapikarkinos.WebappApi.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


@Entity 
@Table(name = "patient")
public class Patient {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank
  @Pattern(regexp = "^[a-zA-Z0-9'.]{3,50}$" , message = "only alphabets, numbers, apostrophe, dot characters are allowed, Firstname must be 3-50 characters long")
  private String firstName;

  @NotBlank
  @Pattern(regexp = "^[a-zA-Z0-9'.]{3,50}$" , message = "only alphabets, numbers, apostrophe, dot characters are allowed, Firstname must be 3-50 characters long")
  private String lastName;

  @NotNull @Min(0) @Max(125)
  private String age;

  @NotBlank
  @Pattern(regexp = "^(?:Male|Female|Other)$", message = "Gender should be in Male, Female or Other pattern only")
  private String gender;

  @NotBlank
  @Pattern(regexp = "^[A-Za-z]{3,100}$", message = "Please enter between {min} and {max}")
  private String city;

  @NotBlank
  @Pattern(regexp="^[1-9][0-9]{5}$", message="Pincode is invalid")
  private String pincode;

  @Column(nullable = true, length = 64)
  private String photos;
  

  // @OneToMany(mappedBy= "patients",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
  // private List<Documents> documents;

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
    // this.documents = documents;
  }

  public Long getId() {
    return id;
}
  public void setId(Long id) {
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
  // public List<Documents> getDocuments() {
  //   return documents;
  // }
  // public void setDocuments(List<Documents> documents) {
  //   this.documents = documents;
  // }
  // @Transient
  //   public String getPhotosImagePath() {
  //       if (photos == null || id == 0) return null;
  //       return "/patient-photos/" + id + "/" + photos;
  //   }

}