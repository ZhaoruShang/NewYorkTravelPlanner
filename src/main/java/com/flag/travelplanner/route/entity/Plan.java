package com.flag.travelplanner.route.entity;

import com.flag.travelplanner.user.entity.User;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "plans")
public class Plan implements Serializable {

    private static final long serialVersionUID = -8219392613034849926L;
    @Id
    private long planId;
    private String name;
    private Date createTime;
    private Date updateTime;

    @OneToMany
    private List<Route> routes;

    @ManyToOne
    private User owner;

    public Plan() {};

    public Plan(long planId, String name) {
        this.planId = planId;
        this.name = name;
    }

    public long getPlanId() {
        return planId;
    }

    public void setPlanId(long planId) {
        this.planId = planId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
