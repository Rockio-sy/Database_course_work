package com.Vidstream.vidstream.dto;

import com.Vidstream.vidstream.model.Subscription;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

public class SubscriptionDTO {
    private Long id;
    @NotNull(message = "User id required")
    private Long user_id;
    @NotNull(message = "Plan is required")
    private Subscription.plan_type plan;

    private Timestamp startDate;
    private Timestamp endDate;

    public SubscriptionDTO() {}

    public SubscriptionDTO(Long id, Long user_id, Subscription.plan_type plan, Timestamp startDate, Timestamp endDate) {
        this.id = id;
        this.user_id = user_id;
        this.plan = plan;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public SubscriptionDTO(Long user_id, Subscription.plan_type plan, Timestamp startDate, Timestamp endDate) {
        this.user_id = user_id;
        this.plan = plan;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Subscription.plan_type getPlan() {
        return plan;
    }

    public void setPlan(Subscription.plan_type plan) {
        this.plan = plan;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }
}

