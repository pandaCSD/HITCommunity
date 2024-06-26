package org.hit.hitcommunitybackend.service;

import org.hit.hitcommunitybackend.domain.Admin;

public interface AdminService {
    Admin adminLoginService(String aname, String apassword);
}
