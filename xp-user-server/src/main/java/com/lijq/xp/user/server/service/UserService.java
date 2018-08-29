package com.lijq.xp.user.server.service;

import com.lijq.xp.user.server.repository.IUserRepository;
import com.lijq.xp.user.server.repository.domain.User;
import com.lijq.xp.user.server.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Lijq
 */
@Service
public class UserService {

    @Autowired
    private IUserRepository userRepository;

    public UserVO get(Long id) {
        if (id != null && userRepository.existsById(id)) {
            return buildSimpleUserVO(userRepository.getOne(id));
        }
        return new UserVO();
    }

    public List<UserVO> list() {
        return userRepository.findAll().stream().map(this::buildSimpleUserVO).collect(Collectors.toList());
    }


    private UserVO buildSimpleUserVO(User user) {

        UserVO vo = new UserVO();
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
