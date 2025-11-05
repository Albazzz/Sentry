package com.mycompany.sentry.entity;

import java.io.Serializable;
import java.util.Objects;

public class UserPremiumId implements Serializable {
    private Integer userID;
    private Integer planID;

    public UserPremiumId() {
    }

    public UserPremiumId(Integer userID, Integer planID) {
        this.userID = userID;
        this.planID = planID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPremiumId that = (UserPremiumId) o;
        return Objects.equals(userID, that.userID) &&
               Objects.equals(planID, that.planID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, planID);
    }
}




