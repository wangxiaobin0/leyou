package com.leyou.auth.service.service;

/**
 * @author
 * @date 2020/3/28
 */
public interface IAuthService {
    /**
     * 身份认证
     * @param username
     * @param password
     */
    String authentication(String username, String password);
}
