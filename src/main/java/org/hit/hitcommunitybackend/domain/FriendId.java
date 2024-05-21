package org.hit.hitcommunitybackend.domain;

import java.io.Serializable;
import java.util.Objects;

public class FriendId implements Serializable {
    private Integer uid1;
    private Integer uid2;

    // Default constructor
    public FriendId() {}

    // Constructor with parameters
    public FriendId(Integer uid1, Integer uid2) {
        if(uid1 < uid2) {
            this.uid1 = uid1;
            this.uid2 = uid2;
        } else {
            this.uid1 = uid2;
            this.uid2 = uid1;
        }
    }

    // Getters and setters
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

    // hashCode and equals methods
    @Override
    public int hashCode() {
        return Objects.hash(uid1, uid2);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FriendId that = (FriendId) obj;
        return Objects.equals(uid1, that.uid1) &&
                Objects.equals(uid2, that.uid2);
    }
}
