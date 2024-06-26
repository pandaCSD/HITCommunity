package org.hit.hitcommunitybackend.repository;

import org.hit.hitcommunitybackend.domain.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminDao extends JpaRepository<Admin, Integer>{
    Admin findByAnameAndApassword(String aname, String apassword);
}