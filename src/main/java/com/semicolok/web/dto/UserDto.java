package com.semicolok.web.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(of = {"id", "email"})
public class UserDto implements Serializable{
    private static final long serialVersionUID = -735155310093228017L;
    
    private Long id;
    private String email;
    private String name;
    private boolean enabled = true;
    private Date registTime;
    private Date updatedTime;
}
