package com.mycompany.sentry.entity;

import java.io.Serializable;
import java.util.Objects;

public class BlockId implements Serializable {
    private Integer blockerID;
    private Integer blockedID;

    public BlockId() {
    }

    public BlockId(Integer blockerID, Integer blockedID) {
        this.blockerID = blockerID;
        this.blockedID = blockedID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockId blockId = (BlockId) o;
        return Objects.equals(blockerID, blockId.blockerID) &&
               Objects.equals(blockedID, blockId.blockedID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blockerID, blockedID);
    }
}




