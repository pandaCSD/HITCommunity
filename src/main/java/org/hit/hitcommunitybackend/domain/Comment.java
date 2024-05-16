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
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer cid;

    @ManyToOne
    @JoinColumn(name = "pid", nullable = false, foreignKey = @ForeignKey(name = "FK_COMMENTS_POSTS"))
    private Post post;

    @ManyToOne
    @JoinColumn(name = "uid", nullable = false, foreignKey = @ForeignKey(name = "FK_COMMENTS_USERS"))
    private User user;

    @Column(name = "ctime", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime ctime;

    @Column(name = "ccontent", nullable = false, columnDefinition = "TEXT")
    private String ccontent;

    // Default constructor
    public Comment() {
        this.ctime = LocalDateTime.now(); // Ensure ctime is set to current timestamp
    }

    // Constructor with parameters
    public Comment(Post post, User user, String ccontent) {
        this.post = post;
        this.user = user;
        this.ccontent = ccontent;
        this.ctime = LocalDateTime.now(); // Ensure ctime is set to current timestamp
    }

    // Getters and Setters

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
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

    public LocalDateTime getCtime() {
        return ctime;
    }

    public void setCtime(LocalDateTime ctime) {
        this.ctime = ctime;
    }

    public String getCcontent() {
        return ccontent;
    }

    public void setCcontent(String ccontent) {
        this.ccontent = ccontent;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "cid=" + cid +
                ", post=" + post +
                ", user=" + user +
                ", ctime=" + ctime +
                ", ccontent='" + ccontent + '\'' +
                '}';
    }
}

