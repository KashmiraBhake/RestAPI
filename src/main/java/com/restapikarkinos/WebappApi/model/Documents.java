package com.restapikarkinos.WebappApi.model;



public class Documents {


  private Long docId;
  private String docName;
  private Patient patients;

  protected Documents() {
    
  }
 
public Documents(String docName,Patient patients){
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
    
public Object getId() {
        return null;
    }


    public String getId(Patient id) {
        return null;
    }


    public String getByPatients(Patient id) {
        return null;
    }
}