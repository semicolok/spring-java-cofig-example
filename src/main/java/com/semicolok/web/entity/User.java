package com.semicolok.web.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Maps;

@Entity
@Table(name = "USER")
@NoArgsConstructor
@EqualsAndHashCode(of = {"email"}, callSuper = false)
@ToString(of = {"id", "email", "name"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User  implements Serializable{
    private static final long serialVersionUID = -5364103629467728599L;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Setter
    @Getter
    private String email;
    
    @Setter
    @Getter
    private String name;
    
    @Setter
//    @Getter(AccessLevel.PRIVATE)
    @Getter
    private String password;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 5)
    private UserRole role = UserRole.USER;

    @Setter
    @Getter
    @Column(nullable = false, columnDefinition="int default 1")
    private boolean enabled = true;
    
    @Setter
    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    private Date registTime;
    
    @Setter
    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;
    
    public enum UserRole {
        USER,
        ADMIN
    }
    
    @PrePersist
    protected void onCreate() {
        updatedTime = registTime = new Date();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedTime = new Date();
    }

    public Map<String, Object> getFieldMap() {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", this.id);
        map.put("name", this.name);
        map.put("email", this.email);
        return map;
    }
}
