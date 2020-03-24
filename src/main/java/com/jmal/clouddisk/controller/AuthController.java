package com.jmal.clouddisk.controller;

import com.jmal.clouddisk.interceptor.AuthInterceptor;
import com.jmal.clouddisk.model.Consumer;
import com.jmal.clouddisk.service.IAuthService;
import com.jmal.clouddisk.util.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 登录、登出、验证
 *
 * @blame jmal
 */
@RestController
@Api(tags = "登录认证")
public class AuthController {

    @Autowired
    IAuthService authService;

    @ApiOperation("登录")
    @PostMapping("/login")
    public ResponseResult<Object> login(@RequestBody Consumer user){
        return authService.login(user.getUsername(), user.getPassword());
    }

    @ApiOperation("校验旧密码")
    @PostMapping("/valid-old-pass")
    public ResponseResult<Object> validOldPass(@RequestBody Consumer user){
        return authService.validOldPass(user.getId(), user.getPassword());
    }

    @ApiOperation("登出")
    @GetMapping("/logout")
    public ResponseResult<Object> logout(HttpServletRequest request){
        String token = request.getHeader(AuthInterceptor.JMAL_TOKEN);
        return authService.logout(token);
    }
}
