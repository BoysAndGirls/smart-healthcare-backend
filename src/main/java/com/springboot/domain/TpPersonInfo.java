package com.springboot.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class TpPersonInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String address;

    private String language;

    private Integer age;

    private String city;

    private String education;

    private String tel;

    private String email;

    private String introduce;

    private String specialty;

    private String cooperationType;

    private Date registerTime;

}
