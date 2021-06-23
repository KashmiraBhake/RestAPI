package com.restapikarkinos.WebappApi.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;



@Entity 
@Table(name = "document")
public class Documents implements Serializable {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long docId;
  private String docName;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", referencedColumnName = "patientId", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
  private Patient patients;
      
  protected Documents() {
    
  }
     
    public Documents(String docName,Patient patients) {
      this.docName=docName;
      this.patients = patients;

    }

    public Long getDocId() {
      return docId;
    }
    public void setDocId(Long docId) {
      this.docId = docId;
    }
    public String getDocName() {
      return docName;
    }
    public void setDocName(String docName) {
      this.docName = docName;
    }
    public Patient getPatients() {
      return patients;
    }
    public void setPatients(Patient patients) {
      this.patients = patients;
    }
}
