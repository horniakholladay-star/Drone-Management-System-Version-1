package com.drone.management.exception;

import com.drone.management.common.Result;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

/**
 * 全局异常处理器
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ==================== 页面跳转异常 ====================

    /** Shiro 未认证 → 登录页 */
    @ExceptionHandler(UnauthenticatedException.class)
    public String handleUnauthenticated(UnauthenticatedException e) {
        return "redirect:/login";
    }

    /** Shiro 未授权 → 403 页面 */
    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ModelAndView handleAuthorization(AuthorizationException e) {
        ModelAndView mav = new ModelAndView("error/403");
        mav.addObject("message", e.getMessage());
        return mav;
    }

    /** 业务异常 → 错误页面 */
    @ExceptionHandler(BusinessException.class)
    public ModelAndView handleBusiness(BusinessException e, HttpServletRequest request) {
        log.warn("业务异常: {}", e.getMessage());
        ModelAndView mav = new ModelAndView("error/500");
        mav.addObject("message", e.getMessage());
        return mav;
    }

    /** 通用异常 → 500 页面 */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleException(Exception e, HttpServletRequest request) {
        log.error("系统异常: {}", e.getMessage(), e);
        ModelAndView mav = new ModelAndView("error/500");
        mav.addObject("message", "服务器内部错误，请稍后重试");
        return mav;
    }

    // ==================== AJAX 异常（返回 JSON） ====================

    /** 参数校验异常 (AJAX) */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Result<?> handleBindException(BindException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return Result.error(400, message);
    }

    /** 参数类型转换异常（如 ID 传了非数字值） */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView handleTypeMismatch(MethodArgumentTypeMismatchException e,
                                           HttpServletRequest request) {
        log.warn("参数类型转换失败: name={}, value={}, requiredType={}",
                e.getName(), e.getValue(),
                e.getRequiredType() != null ? e.getRequiredType().getSimpleName() : "unknown");
        ModelAndView mav = new ModelAndView("error/400");
        mav.addObject("message", "请求参数格式错误: " + e.getName());
        return mav;
    }
}
