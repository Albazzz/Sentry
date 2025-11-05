package com.mycompany.sentry.repository;

import com.mycompany.sentry.entity.Block;
import com.mycompany.sentry.entity.BlockId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, BlockId> {
    List<Block> findByBlockerID(Integer blockerID);
    List<Block> findByBlockedID(Integer blockedID);
    boolean existsByBlockerIDAndBlockedID(Integer blockerID, Integer blockedID);
}




