package org.hit.hitcommunitybackend.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;

@Entity
@Table(name = "friends")
public class Friend {

    @Id
    @Column(name = "uid1", nullable = false)
    private Integer uid1;

    @Id
    @Column(name = "uid2", nullable = false)
    private Integer uid2;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private FriendshipStatus status;

    @Column(name = "since", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime since;

    // Default constructor
    public Friend() {
        this.since = LocalDateTime.now(); // Ensure since is set to current timestamp
    }

    // Constructor with parameters
    public Friend(Integer uid1, Integer uid2, FriendshipStatus status) {
        this.uid1 = uid1;
        this.uid2 = uid2;
        this.status = status;
        this.since = LocalDateTime.now(); // Ensure since is set to current timestamp
    }

    // Getters and Setters

    public Integer getUid1() {
        return uid1;
    }

    public void setUid1(Integer uid1) {
        this.uid1 = uid1;
    }

    public Integer getUid2() {
        return uid2;
    }

    public void setUid2(Integer uid2) {
        this.uid2 = uid2;
    }

    public FriendshipStatus getStatus() {
        return status;
    }

    public void setStatus(FriendshipStatus status) {
        this.status = status;
    }

    public LocalDateTime getSince() {
        return since;
    }

    public void setSince(LocalDateTime since) {
        this.since = since;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "uid1=" + uid1 +
                ", uid2=" + uid2 +
                ", status=" + status +
                ", since=" + since +
                '}';
    }
}

