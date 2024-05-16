package org.hit.hitcommunitybackend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;


public class FriendId implements Serializable {
    private Integer uid1;
    private Integer uid2;

    @Serial
    private static final long serialVersionUID = 1L;

    @Column(name = "uid1")
    private Integer uid1;

    @Column(name = "uid2")
    private Integer uid2;

    // 省略构造函数、getter 和 setter 方法

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendId friendId = (FriendId) o;
        final FriendId other = (FriendId)o;
        if (Objects.equals(other.uid1, this.uid1) && Objects.equals(other.uid2, this.uid2)) {
            return true;
        }
        if (Objects.equals(other.uid1, this.uid2) && Objects.equals(other.uid2, this.uid1)) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid1, uid2);
    }
}
