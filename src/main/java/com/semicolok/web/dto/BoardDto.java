package com.semicolok.web.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(of = {"id", "title"})
public class BoardDto implements Serializable{
    private static final long serialVersionUID = -2864324296955372498L;
    
    private Long id;
    private String title;
    private String content;
    private Long userId;
    private String userName;
    private String userEmail;
    private Date registTime;
    private Date updatedTime;
    
}
