package org.hit.hitcommunitybackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iid;

    @Column(name = "pid", nullable = false)
    private Integer pid;

    @Column(name = "iurl", nullable = false, length = 255)
    private String iurl;

    // Default constructor
    public Image() {
    }

    // Constructor with parameters
    public Image(Integer pid, String iurl) {
        this.pid = pid;
        this.iurl = iurl;
    }

    // Getters and Setters

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getIurl() {
        return iurl;
    }

    public void setIurl(String iurl) {
        this.iurl = iurl;
    }

    @Override
    public String toString() {
        return "Image{" +
                "iid=" + iid +
                ", pid=" + pid +
                ", iurl='" + iurl + '\'' +
                '}';
    }
}
