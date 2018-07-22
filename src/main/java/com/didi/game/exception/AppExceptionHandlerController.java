package com.didi.game.exception;

import com.didi.game.common.AppBusinessException;
import com.didi.game.constant.Const;
import com.didi.game.util.JsonUtils;
import com.google.common.base.Joiner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 统一异常处理
 */
@ControllerAdvice
public class AppExceptionHandlerController extends ResponseEntityExceptionHandler {

    protected Logger logger = LoggerFactory.getLogger(AppExceptionHandlerController.class);


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        String errorMessage = extractErrorMessageFromObjectErrors(allErrors, Const.BAD_REQUEST);
        return createResponseEntity(status.value(), request.getDescription(false), errorMessage);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body,
                                                             HttpHeaders headers, HttpStatus status,
                                                             WebRequest request) {

        logger.error("spring mvc 异常: " + ex.getMessage(), ex);

        return createResponseEntity(status.value(), request.getDescription(false), status.getReasonPhrase());
    }

    @ExceptionHandler(value = AppBusinessException.class)
    public ResponseEntity<Object> handleAppBusinessException(HttpServletRequest request, AppBusinessException e) {

        //业务异常
        return createResponseEntity(e.getHttpStatus(), request.getRequestURI(), e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handleException(HttpServletRequest request, Exception e) {

        logger.error("服务器发生错误: " + e.getMessage(), e);
        return createResponseEntity(Const.INTERNAL_ERROR_CODE, request.getRequestURI(), Const.INTERNAL_ERROR);

    }

    private ResponseEntity<Object> createResponseEntity(int httpStatus, String requestUri, String message) {
        Map<String, Object> error = new HashMap<>();
        error.put("code", httpStatus);
        error.put("uri", requestUri);
        error.put("info", message);
        String json = JsonUtils.object2Json(error);

        return ResponseEntity.status(HttpStatus.valueOf(httpStatus)).body(json);

    }

    private String extractErrorMessageFromObjectErrors(List<ObjectError> allErrors, String defaultMessage) {
        if(allErrors == null || allErrors.isEmpty()) {
            return defaultMessage;
        } else {
            List<String> errorMessages = allErrors.stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return Joiner.on(",").skipNulls().join(errorMessages);
        }
    }
}