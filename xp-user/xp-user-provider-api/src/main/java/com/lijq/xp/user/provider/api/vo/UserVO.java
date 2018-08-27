package com.lijq.xp.user.provider.api.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Lijq
 */
@Getter
@Setter
public class UserVO implements Serializable {

    private static final long serialVersionUID = -7105349057905178746L;

    private Long id;
    private String username;

    private String realname;
    private String mobile;
    private String nickname;
    private String avatar;
    private Date createTime;
}
