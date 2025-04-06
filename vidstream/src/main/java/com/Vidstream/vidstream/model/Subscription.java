package com.Vidstream.vidstream.model;

import java.sql.Timestamp;


public class Subscription {
    public enum plan_type {
        Free,
        Plus,
        Premium;
    }
    private Long id;
    private Long user_id;
    private plan_type plan;
    private Timestamp startDate;
    private Timestamp endDate;

    public Subscription() {
    }

    public Subscription(Long user_id, plan_type plan, Timestamp startDate, Timestamp endDate) {
        this.user_id = user_id;
        this.plan = plan;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Subscription(Long id, Long user_id, plan_type plan, Timestamp startDate, Timestamp endDate) {
        this.id = id;
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

    public plan_type getPlan() {
        return plan;
    }

    public void setPlan(plan_type plan) {
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
