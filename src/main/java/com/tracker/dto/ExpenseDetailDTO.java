package com.tracker.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by nakulkumar on 24/6/16.
 */
public class ExpenseDetailDTO {
    private String description;
    private Double amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date created;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date updated;

    public ExpenseDetailDTO() {
    }

    public ExpenseDetailDTO(String description, Double amount, Date created, Date updated) {
        this.description = description;
        this.amount = amount;
        this.created = created;
        this.updated = updated;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "ExpenseDetailDTO{" +
                "description='" + description + '\'' +
                ", amount=" + amount +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
