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
@Table(name = "reposts")
public class Repost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rid;

    @ManyToOne
    @JoinColumn(name = "original_pid", nullable = false, foreignKey = @ForeignKey(name = "FK_REPOSTS_POSTS"))
    private Post originalPost;

    @Column(name = "rtime", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime rtime;

    @ManyToOne
    @JoinColumn(name = "rowner", nullable = false, foreignKey = @ForeignKey(name = "FK_REPOSTS_USERS"))
    private User rowner;

    // Default constructor
    public Repost() {
        this.rtime = LocalDateTime.now(); // Ensure rtime is set to current timestamp
    }

    // Constructor with parameters
    public Repost(Post originalPost, User rowner) {
        this.originalPost = originalPost;
        this.rtime = LocalDateTime.now(); // Ensure rtime is set to current timestamp
        this.rowner = rowner;
    }

    // Getters and Setters

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Post getOriginalPost() {
        return originalPost;
    }

    public void setOriginalPost(Post originalPost) {
        this.originalPost = originalPost;
    }

    public LocalDateTime getRtime() {
        return rtime;
    }

    public void setRtime(LocalDateTime rtime) {
        this.rtime = rtime;
    }

    public User getRowner() {
        return rowner;
    }

    public void setRowner(User rowner) {
        this.rowner = rowner;
    }

    @Override
    public String toString() {
        return "Repost{" +
                "rid=" + rid +
                ", originalPost=" + originalPost +
                ", rtime=" + rtime +
                ", rowner=" + rowner +
                '}';
    }
}
