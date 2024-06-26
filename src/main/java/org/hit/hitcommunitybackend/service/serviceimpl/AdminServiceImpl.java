package org.hit.hitcommunitybackend.service.serviceimpl;

import jakarta.annotation.Resource;
import org.hit.hitcommunitybackend.domain.Admin;
import org.hit.hitcommunitybackend.repository.AdminDao;
import org.hit.hitcommunitybackend.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Resource
    private AdminDao adminDao;

    @Override
    public Admin adminLoginService(String aname, String apassword) {
        return adminDao.findByAnameAndApassword(aname, apassword);
    }
}
