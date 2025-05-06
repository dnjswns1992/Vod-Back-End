package com.example.StreamCraft.controller.advice;

import com.example.StreamCraft.Handler.ErrorHandler.DuplicateEmailException;
import com.example.StreamCraft.Handler.ErrorHandler.DuplicateNickNameException;
import com.example.StreamCraft.Handler.ErrorHandler.DuplicateUsernameException;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/* ControllerAdvice 안내
* 1 . 전역 예외처리 : 애프리케이션 모든 컨트롤러에서 발생하는 예외를 한곳에서 처리할 수 있습니다.
*  */
@ControllerAdvice
@Tag(name = "예외처리 컨트롤러 description = 닉네임 중복 이메일 중복등 예외처리 시 처리")
public class GlobalExceptionHandler {


    /**
     * 이메일 중복 예외 처리
     */
    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<String> handleDuplicateEmailException(DuplicateEmailException duplicateEmailException){
        return new ResponseEntity<>(duplicateEmailException.getMessage(), HttpStatus.CONFLICT);
    }
    /**
     * 사용자명 중복 예외 처리
     */

    @ExceptionHandler(DuplicateUsernameException.class)
    public ResponseEntity<String> handleDuplicateUsernameException(DuplicateUsernameException ex){
        return new ResponseEntity<>(ex.getMessage() ,HttpStatus.CONFLICT);
    }
    /**
     * 닉네임 중복 예외 처리
     */
    @ExceptionHandler(DuplicateNickNameException.class)
    public ResponseEntity<String> handleDuplicatedIdException(DuplicateNickNameException ex){
        return new ResponseEntity<>(ex.getMessage() , HttpStatus.CONFLICT);
    }
}
