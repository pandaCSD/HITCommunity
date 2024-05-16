package org.hit.hitcommunitybackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iid;

    @ManyToOne
    @JoinColumn(name = "pid", nullable = false, foreignKey = @ForeignKey(name = "FK_IMAGES_POSTS"))
    private Post post;

    @Column(name = "iurl", nullable = false, length = 255)
    private String iurl;

    // Default constructor
    public Image() {
    }

    // Constructor with parameters
    public Image(Post post, String iurl) {
        this.post = post;
        this.iurl = iurl;
    }

    // Getters and Setters

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
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
                ", post=" + post +
                ", iurl='" + iurl + '\'' +
                '}';
    }
}

