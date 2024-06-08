package org.hit.hitcommunitybackend.repository;

import org.hit.hitcommunitybackend.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageDao extends JpaRepository<Image, Integer> {
    List<Image> findByPid(Integer pid);
}
