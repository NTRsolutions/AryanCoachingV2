package com.example.rakesh.aryancoaching;

import com.google.firebase.database.Exclude;

public class Faculty {
    private String name;
    private String subject;
    private String facultyPicUrl;
    private String Qualification;
    private String descrption;
    private String mKeyFaculty;
    public Faculty(){

    }

    public Faculty(String name, String subject, String facultyPic) {
        this.name = name;
        this.subject = subject;
        this.facultyPicUrl = facultyPic;
    }
    public Faculty( String facultyPic,String name, String subject,String Qualification,String descr) {
        this.name = name;
        this.subject = subject;
        this.facultyPicUrl = facultyPic;
        this.Qualification = Qualification;
        this.descrption = descr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFacultyPic() {
        return facultyPicUrl;
    }

    public void setFacultyPic(String facultyPic) {
        this.facultyPicUrl = facultyPic;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }
    @Exclude
    public String getKey() {
        return mKeyFaculty;

    }

    @Exclude
    public void setKey(String key) {
        mKeyFaculty = key;
    }
}
