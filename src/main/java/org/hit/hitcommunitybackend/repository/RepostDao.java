package org.hit.hitcommunitybackend.repository;

import org.hit.hitcommunitybackend.domain.Repost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepostDao extends JpaRepository<Repost, Integer> {
    Repost save(Repost repost);
}
