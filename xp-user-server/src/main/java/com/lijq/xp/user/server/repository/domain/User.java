package com.lijq.xp.user.server.repository.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Lijq
 */
@Getter
@Setter
@Entity
@Table(name = "t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 3700764430939574017L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String realname;
    private String mobile;
    private String nickname;
    private String avatar;
    private Date createTime;
}