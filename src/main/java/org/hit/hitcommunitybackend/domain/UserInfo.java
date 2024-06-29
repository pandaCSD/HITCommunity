package org.hit.hitcommunitybackend.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "userinfo")
public class UserInfo {
    @Id
    private Integer uid;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender", length = 10)
    private Gender gender;

    @Column(name = "age")
    private Integer age;

    @Column(name = "signature")
    private String signature;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email")
    private String email;

    // 枚举类型Gender
    public enum Gender {
        Male, Female, Other
    }

    // Getters and Setters
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}