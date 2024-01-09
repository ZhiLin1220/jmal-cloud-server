package com.jmal.clouddisk.websocket;

import cn.hutool.core.text.CharSequenceUtil;
import com.jmal.clouddisk.interceptor.AuthInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import java.security.Principal;
import java.util.Map;

/**
 * 我们可以通过请求信息，比如token、或者session判用户是否可以连接，这样就能够防范非法用户
 *
 * @author jmal
 */
@Component
public class PrincipalHandshakeHandler extends DefaultHandshakeHandler {
    @Override
    protected Principal determineUser(@NotNull ServerHttpRequest request, @NotNull WebSocketHandler wsHandler, @NotNull Map<String, Object> attributes) {
        /*
         * 这边可以按你的需求，如何获取唯一的值，既unicode
         * 得到的值，会在监听处理连接的属性中，既WebSocketSession.getPrincipal().getName()
         * 也可以自己实现Principal()
         */
        if (request instanceof ServletServerHttpRequest servletServerHttpRequest) {
            HttpServletRequest httpRequest = servletServerHttpRequest.getServletRequest();
            /*
             * 这边就获取你最熟悉的陌生人,携带参数，你可以cookie，请求头，或者url携带，这边我采用url携带
             */
            String name = AuthInterceptor.getCookie(httpRequest, "username");
            if (CharSequenceUtil.isBlank(name)) {
                return null;
            }
            return () -> name;
        }
        return null;
    }

}
