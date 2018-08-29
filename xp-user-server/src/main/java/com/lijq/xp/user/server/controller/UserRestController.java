package com.lijq.xp.user.server.controller;

import com.lijq.xp.user.server.service.UserService;
import com.lijq.xp.user.server.vo.UserVO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @author Lijq
 */
@RestController
@RequestMapping(value = "/user")
@Slf4j
public class UserRestController {

    @Autowired
    private UserService userService;

    private final Random random = new Random();


    @GetMapping(value = "/{id}")
    public ResponseEntity<UserVO> get(@PathVariable Long id) {
        return new ResponseEntity<>(userService.get(id), HttpStatus.OK);
    }

    /**
     * 整合 Hystrix
     */
    @GetMapping(value = "/list")
    @HystrixCommand(fallbackMethod = "fallbackForFindAll",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "100")
            })
    public ResponseEntity<List<UserVO>> list() {
        return new ResponseEntity<>(userService.list(), HttpStatus.OK);
    }

    public ResponseEntity<List<UserVO>> fallbackForFindAll() {
        log.error("触发熔断机制");
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
    }
}
