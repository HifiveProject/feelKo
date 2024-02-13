package com.ll.feelko.global.common.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        // 세션에 에러 메시지 저장
        HttpSession session = request.getSession();
        session.setAttribute("errorMessage", "잘못된 요청입니다.");
        return "redirect:/"; // 'error'는 잘못된 요청을 처리하는 데 사용할 뷰의 이름입니다. 실제 프로젝트에 맞게 변경하세요.
    }
}