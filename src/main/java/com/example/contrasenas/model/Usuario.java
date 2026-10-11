package com.example.contrasenas.model;

import java.sql.Timestamp;

public class Usuario {
    private String userId;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String userStatus;
    private String role; // 'Jefe' o 'Empleado'
    private Timestamp createdDate;
    private String password;
    private String curp;
    private String rfc;
    private String specialty;
    private String notes;
    private String phone;   // Cifrado en BD (phoneEncrypted)
    private String address; // Cifrado en BD (addressEncrypted)
    private String salary;  // Cifrado en BD (salaryEncrypted)

    public Usuario() {}

    public Usuario(String userId, String firstName, String lastName, String emailAddress,
                   String userStatus, String role, Timestamp createdDate, String curp,
                   String rfc, String specialty, String notes, String phone, String address, String salary) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.userStatus = userStatus;
        this.role = role;
        this.createdDate = createdDate;
        this.curp = curp;
        this.rfc = rfc;
        this.specialty = specialty;
        this.notes = notes;
        this.phone = phone;
        this.address = address;
        this.salary = salary;
    }

    // Getters y Setters
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmailAddress() { return emailAddress; }
    public void setEmailAddress(String emailAddress) { this.emailAddress = emailAddress; }

    public String getUserStatus() { return userStatus; }
    public void setUserStatus(String userStatus) { this.userStatus = userStatus; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Timestamp getCreatedDate() { return createdDate; }
    public void setCreatedDate(Timestamp createdDate) { this.createdDate = createdDate; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCurp() { return curp; }
    public void setCurp(String curp) { this.curp = curp; }

    public String getRfc() { return rfc; }
    public void setRfc(String rfc) { this.rfc = rfc; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getSalary() { return salary; }
    public void setSalary(String salary) { this.salary = salary; }
}