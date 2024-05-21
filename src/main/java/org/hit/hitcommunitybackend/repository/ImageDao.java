package org.hit.hitcommunitybackend.repository;

import org.hit.hitcommunitybackend.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageDao extends JpaRepository<Image, Integer> {

}
