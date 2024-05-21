package org.hit.hitcommunitybackend.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

public class RequestId implements Serializable {
    private Integer suid;
    private Integer ruid;

    // Constructors, getters, setters, hashCode, equals
    public RequestId() {}

    public RequestId(Integer suid, Integer ruid) {
        this.suid = suid;
        this.ruid = ruid;
    }

    public Integer getSuid() {
        return suid;
    }

    public void setSuid(Integer suid) {
        this.suid = suid;
    }

    public Integer getRuid() {
        return ruid;
    }

    public void setRuid(Integer ruid) {
        this.ruid = ruid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RequestId that = (RequestId) o;
        return Objects.equals(suid, that.suid) && Objects.equals(ruid, that.ruid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suid, ruid);
    }
}

