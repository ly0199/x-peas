package com.lijq.xp.user.provider.service;

import com.lijq.xp.user.provider.api.vo.UserVO;
import com.lijq.xp.user.provider.repository.IUserRepository;
import com.lijq.xp.user.provider.repository.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Lijq
 */
@Service
public class UserService {

    @Autowired
    private IUserRepository iUserRepository;

    public UserVO get(Long id) {
        if (id == null) {
            return new UserVO();
        }
        return buildSimpleVO(iUserRepository.getOne(id));
    }

    /**
     * 构造UserVO
     *
     * @param user {@link User}
     * @return {@link UserVO}
     */
    private UserVO buildSimpleVO(User user) {
        UserVO vo = new UserVO();

        if (user == null) {
            return vo;
        }

        vo.setId(user.getId());
        vo.setUsername(user.getUsername());
        vo.setAvatar(user.getAvatar());
        vo.setCreateTime(user.getCreateTime());
        vo.setMobile(user.getMobile());
        vo.setNickname(user.getNickname());
        vo.setRealname(user.getRealname());
        return vo;
    }
}
