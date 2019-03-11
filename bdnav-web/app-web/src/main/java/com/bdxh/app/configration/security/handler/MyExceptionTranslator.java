package com.bdxh.app.configration.security.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

/**
 * @description: 登陆异常处理
 * @author: xuyuan
 * @create: 2019-03-08 11:00
 **/
@Component
public class MyExceptionTranslator implements WebResponseExceptionTranslator<OAuth2Exception> {

    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    public MyExceptionTranslator() {

    }

    @Override
    public ResponseEntity translate(Exception e) {
        Throwable[] causeChain = this.throwableAnalyzer.determineCauseChain(e);
        Exception ase = (OAuth2Exception)this.throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if (ase != null) {
            return this.handleOAuth2Exception((OAuth2Exception)ase);
        } else {
            ase = (AuthenticationException)this.throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
            if (ase != null) {
                return this.handleOAuth2Exception(new MyExceptionTranslator.UnauthorizedException(e.getMessage(), e));
            } else {
                ase = (AccessDeniedException)this.throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
                if (ase instanceof AccessDeniedException) {
                    return this.handleOAuth2Exception(new MyExceptionTranslator.ForbiddenException(ase.getMessage(), ase));
                } else {
                    ase = (HttpRequestMethodNotSupportedException)this.throwableAnalyzer.getFirstThrowableOfType(HttpRequestMethodNotSupportedException.class, causeChain);
                    return ase instanceof HttpRequestMethodNotSupportedException ? this.handleOAuth2Exception(new MyExceptionTranslator.MethodNotAllowed(ase.getMessage(), ase)) : this.handleOAuth2Exception(new MyExceptionTranslator.ServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
                }
            }
        }
    }

    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) {
        int status = e.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        if (status == HttpStatus.UNAUTHORIZED.value() || e instanceof InsufficientScopeException) {
            headers.set("WWW-Authenticate", String.format("%s %s", "Bearer", e.getSummary()));
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code",500);
        String message = e.getOAuth2ErrorCode();
        if (status!=401&&status!=403&&status!=405&&status!=500){
            message = "授权失败";
        }
        jsonObject.put("message",message);
        ResponseEntity<OAuth2Exception> response = new ResponseEntity(jsonObject, headers, HttpStatus.valueOf(status));
        return response;
    }

    private static class UnauthorizedException extends OAuth2Exception {
        public UnauthorizedException(String msg, Throwable t) {
            super(msg, t);
        }

        public String getOAuth2ErrorCode() {
            return "用户名或密码不正确";
        }

        public int getHttpErrorCode() {
            return 401;
        }
    }

    private static class ForbiddenException extends OAuth2Exception {
        public ForbiddenException(String msg, Throwable t) {
            super(msg, t);
        }

        public String getOAuth2ErrorCode() {
            return "没有相关权限";
        }

        public int getHttpErrorCode() {
            return 403;
        }
    }

    private static class MethodNotAllowed extends OAuth2Exception {
        public MethodNotAllowed(String msg, Throwable t) {
            super(msg, t);
        }

        public String getOAuth2ErrorCode() {
            return "请求方法不支持";
        }

        public int getHttpErrorCode() {
            return 405;
        }
    }

    private static class ServerErrorException extends OAuth2Exception {
        public ServerErrorException(String msg, Throwable t) {
            super(msg, t);
        }

        public String getOAuth2ErrorCode() {
            return "内部错误";
        }

        public int getHttpErrorCode() {
            return 500;
        }
    }

}
