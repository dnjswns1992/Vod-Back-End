package com.example.springsecurity04.Controller.ControllerAdvice;

import com.example.springsecurity04.Handler.ErrorHandler.DuplicateEmailException;
import com.example.springsecurity04.Handler.ErrorHandler.DuplicateNickNameException;
import com.example.springsecurity04.Handler.ErrorHandler.DuplicateUsernameException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/* ControllerAdvice 안내
* 1 . 전역 예외처리 : 애프리케이션 모든 컨트롤러에서 발생하는 예외를 한곳에서 처리할 수 있습니다.
*  */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateEmailException.class)
    public ResponseEntity<String> handleDuplicateEmailException(DuplicateEmailException duplicateEmailException){
        return new ResponseEntity<>(duplicateEmailException.getMessage(), HttpStatus.CONFLICT);
    }
    @ExceptionHandler(DuplicateUsernameException.class)

    public ResponseEntity<String> handleDuplicateUsernameException(DuplicateUsernameException ex){
        return new ResponseEntity<>(ex.getMessage() ,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(DuplicateNickNameException.class)

    public ResponseEntity<String> handleDuplicatedIdException(DuplicateNickNameException ex){
        return new ResponseEntity<>(ex.getMessage() , HttpStatus.CONFLICT);
    }
}
