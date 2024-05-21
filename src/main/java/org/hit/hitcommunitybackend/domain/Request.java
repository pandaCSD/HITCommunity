package org.hit.hitcommunitybackend.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

//PENDING
//REJECTED

@Entity
@Table(name = "requests")
@IdClass(RequestId.class)
public class Request {

    @Id
    @Column(name = "suid", nullable = false)
    private Integer suid;

    @Id
    @Column(name = "ruid", nullable = false)
    private Integer ruid;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "request_time", nullable = false)
    private LocalDateTime requestTime;

    public Request() {
    }

    public Request(RequestId id, String status) {
        this.suid = id.getSuid();
        this.ruid = id.getRuid();
        this.status = status;
        this.requestTime = LocalDateTime.now();
    }

    // Getters and setters

    public RequestId getId() {
        return new RequestId(suid, ruid);
    }

    public void setId(RequestId id) {
        this.suid = id.getSuid();
        this.ruid = id.getRuid();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(LocalDateTime requestTime) {
        this.requestTime = requestTime;
    }
}
