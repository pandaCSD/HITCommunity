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
import java.time.LocalDateTime;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer pid;

    @Column(name = "ptime", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime ptime;

    @ManyToOne
    @JoinColumn(name = "powner", nullable = false, foreignKey = @ForeignKey(name = "FK_POSTS_USERS"))
    private User powner;

    @Column(name = "pcontent", nullable = true)
    private String pcontent;

    // Default constructor
    public Post() {
        this.ptime = LocalDateTime.now(); // Ensure ptime is set to current timestamp
    }

    // Constructor with parameters
    public Post(LocalDateTime ptime, User powner) {
        this.ptime = ptime;
        this.powner = powner;
    }

    // Getters and Setters

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public LocalDateTime getPtime() {
        return ptime;
    }

    public void setPtime(LocalDateTime ptime) {
        this.ptime = ptime;
    }

    public User getPowner() {
        return powner;
    }

    public void setPowner(User powner) {
        this.powner = powner;
    }

    @Override
    public String toString() {
        return "Post{" +
                "pid=" + pid +
                ", ptime=" + ptime +
                ", powner=" + powner +
                ", pcontent='" + pcontent + +
                '}';
    }
}
