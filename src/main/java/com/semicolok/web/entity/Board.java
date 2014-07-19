package com.semicolok.web.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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

@Entity
@Table(name = "BOARD")
@NoArgsConstructor
@EqualsAndHashCode(of = {"title"}, callSuper = false)
@ToString(of = {"id", "content"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Board  implements Serializable{
    private static final long serialVersionUID = -3323492894820582222L;

    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private User user; // 생성자
    
    @Setter
    @Getter
    private String title;
    
    @Setter
    @Getter
    private String content;
    
    @Setter
    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    private Date registTime;
    
    @Setter
    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTime;
    
    @PrePersist
    protected void onCreate() {
        updatedTime = registTime = new Date();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedTime = new Date();
    }
}
