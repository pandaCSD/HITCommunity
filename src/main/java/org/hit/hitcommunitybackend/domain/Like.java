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
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer lid;

    @ManyToOne
    @JoinColumn(name = "pid", nullable = false, foreignKey = @ForeignKey(name = "FK_LIKES_POSTS"))
    private Post post;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false, foreignKey = @ForeignKey(name = "FK_LIKES_USERS"))
    private User user;

    @Column(name = "ltime", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime ltime;

    // Default constructor
    public Like() {
        this.ltime = LocalDateTime.now(); // Ensure ltime is set to current timestamp
    }

    // Constructor with parameters
    public Like(Post post, User user) {
        this.post = post;
        this.user = user;
        this.ltime = LocalDateTime.now(); // Ensure ltime is set to current timestamp
    }

    // Getters and Setters

    public Integer getLid() {
        return lid;
    }

    public void setLid(Integer lid) {
        this.lid = lid;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getLtime() {
        return ltime;
    }

    public void setLtime(LocalDateTime ltime) {
        this.ltime = ltime;
    }

    @Override
    public String toString() {
        return "Like{" +
                "lid=" + lid +
                ", post=" + post +
                ", user=" + user +
                ", ltime=" + ltime +
                '}';
    }
}

