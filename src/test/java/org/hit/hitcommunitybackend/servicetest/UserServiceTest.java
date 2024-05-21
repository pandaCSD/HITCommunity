package org.hit.hitcommunitybackend.servicetest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.hit.hitcommunitybackend.domain.User;
import org.hit.hitcommunitybackend.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserService userService;

    @Test
    public void testUserRegisterService() {
        User mockUser = new User("testUser", "password");
        when(userService.userRegisterService(anyString(), anyString())).thenReturn(mockUser);

        User user = userService.userRegisterService("testUser", "password");
        assertEquals("testUser", user.getUname());
    }

    @Test
    public void testUserLoginService() {
        User mockUser = new User("testUser", "password");
        when(userService.userLoginService(anyString(), anyString())).thenReturn(mockUser);

        User user = userService.userLoginService("testUser", "password");
        assertEquals("testUser", user.getUname());
    }

    @Test
    public void testUserUpdateUnameService() {
        User mockUser = new User("updatedUser", "password");
        when(userService.userUpdateUnameService(anyInt(), anyString())).thenReturn(mockUser);

        User user = userService.userUpdateUnameService(1, "updatedUser");
        assertEquals("updatedUser", user.getUname());
    }

    @Test
    public void testUserUpdateUpasswordService() {
        User mockUser = new User("testUser", "updatedPassword");
        when(userService.userUpdateUpasswordService(anyInt(), anyString())).thenReturn(mockUser);

        User user = userService.userUpdateUpasswordService(1, "updatedPassword");
        assertEquals("updatedPassword", user.getUpassword());
    }

    @Test
    public void testUserDeleteService() {
        when(userService.userDeleteService(anyInt())).thenReturn(true);

        boolean result = userService.userDeleteService(1);
        assertTrue(result);
    }

    @Test
    public void testUserDeleteFriendService() {
        when(userService.userDeleteFriendService(anyInt(), anyInt())).thenReturn(true);

        boolean result = userService.userDeleteFriendService(1, 2);
        assertTrue(result);
    }

    @Test
    public void testFriendRequestSentService() {
        when(userService.friendRequestSentService(anyInt(), anyInt())).thenReturn(true);

        boolean result = userService.friendRequestSentService(1, 2);
        assertTrue(result);
    }

    @Test
    public void testFriendRequestAccepteService() {
        when(userService.friendRequestAccepteService(anyInt(), anyInt())).thenReturn(true);

        boolean result = userService.friendRequestAccepteService(1, 2);
        assertTrue(result);
    }

    @Test
    public void testFriendRequestRejectService() {
        when(userService.friendRequestRejectService(anyInt(), anyInt())).thenReturn(true);

        boolean result = userService.friendRequestRejectService(1, 2);
        assertTrue(result);
    }

    @Test
    public void testGetAllFriendService() {
        User friend1 = new User("friend1", "password1");
        User friend2 = new User("friend2", "password2");
        List<User> friends = Arrays.asList(friend1, friend2);
        when(userService.getAllFriendService(anyInt())).thenReturn(friends);

        List<User> result = userService.getAllFriendService(1);
        assertEquals(2, result.size());
        assertEquals("friend1", result.get(0).getUname());
        assertEquals("friend2", result.get(1).getUname());
    }

    @Test
    public void testGetAllFriendRequestService() {
        User request1 = new User("request1", "password3");
        User request2 = new User("request2", "password4");
        List<User> requests = Arrays.asList(request1, request2);
        when(userService.getAllFriendRequestService(anyInt())).thenReturn(requests);

        List<User> result = userService.getAllFriendRequestService(1);
        assertEquals(2, result.size());
        assertEquals("request1", result.get(0).getUname());
        assertEquals("request2", result.get(1).getUname());
    }
}
