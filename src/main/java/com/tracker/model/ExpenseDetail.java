package com.tracker.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by nakulkumar on 24/6/16.
 */
@Entity
@Table(name = "expense_detail")
public class ExpenseDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Double amount;
    private Date created;
    private Date updated;

    public ExpenseDetail() {
    }

    public ExpenseDetail(String description, Double amount, Date created, Date updated) {
        this.description = description;
        this.amount = amount;
        this.created = created;
        this.updated = updated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "amount", nullable = false)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Column(name = "created", nullable = false)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Column(name = "updated", nullable = false)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Override
    public String toString() {
        return "ExpenseDetail{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", created=" + created +
                ", updated=" + updated +
                '}';
    }
}
