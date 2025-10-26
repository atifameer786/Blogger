package com.blog.blogger.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.blogger.pojo.APIResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<APIResponse> resourceNotFouncExceptionHandler(ResourceNotFoundException ex){
        String message = ex.getMessage();
        APIResponse apiResponse = new APIResponse("1",message);
        return new ResponseEntity<APIResponse>(apiResponse,HttpStatus.NOT_FOUND);
        
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handelMathodArgsNotValidEception(MethodArgumentNotValidException ex){
        Map<String,String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error->{
            String feildName = ((FieldError)error).getField();
            String message = error.getDefaultMessage();
            resp.put(feildName, message);

        });
        return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
        
    }
    
}
