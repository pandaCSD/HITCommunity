package org.hit.hitcommunitybackend.daotset;

import org.hit.hitcommunitybackend.domain.Friend;
import org.hit.hitcommunitybackend.domain.FriendId;
import org.hit.hitcommunitybackend.domain.User;
import org.hit.hitcommunitybackend.repository.FriendDao;
import org.hit.hitcommunitybackend.repository.UserDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class FriendDaoTest {

    @Autowired
    private FriendDao friendDao;
    @Autowired
    private UserDao userDao;

    @Test
    void testSaveAndFindById() {
        // 创建一个新的朋友关系
        Friend friend = new Friend(1, 2);

        // 保存朋友关系
        Friend savedFriend = friendDao.save(friend);

        // 检查保存后的对象是否与原始对象相同
        assertEquals(friend, savedFriend);

        // 通过主键查找保存的朋友关系
        Optional<Friend> foundFriend = friendDao.findById(new FriendId(1, 2));

        // 确保找到了保存的朋友关系
        assertTrue(foundFriend.isPresent());

        // 检查找到的朋友关系是否与原始对象相同
        assertEquals(savedFriend, foundFriend.get());
    }

    @Test
    void testDelete() {
        User user1 = new User();
        user1.setUname("A");
        user1.setUpassword("A");
        userDao.save(user1);
        User user2 = new User();
        user2.setUname("B");
        user2.setUpassword("B");
        userDao.save(user2);
        User user3 = new User();
        user3.setUname("C");
        user3.setUpassword("C");
        userDao.save(user3);

        // 创建一个新的朋友关系
        Friend friend = new Friend(1, 2);

        // 保存朋友关系
        friendDao.save(friend);

        // 删除保存的朋友关系
        friendDao.deleteById(new FriendId(1, 2));

        // 检查是否成功删除
        assertFalse(friendDao.existsById(new FriendId(1, 2)));
    }
}

