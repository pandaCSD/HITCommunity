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
import java.util.Objects;

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

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "text", nullable = false)
    private String text;

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    // Default constructor
    public Post() {
        this.ptime = LocalDateTime.now(); // Ensure ptime is set to current timestamp
    }

    // Constructor with parameters
    public Post(LocalDateTime ptime, User powner, String title, String text) {
        this.ptime = ptime;
        this.powner = powner;
        this.title = title;
        this.text = text;
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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return pid == post.pid;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid);
    }
}
