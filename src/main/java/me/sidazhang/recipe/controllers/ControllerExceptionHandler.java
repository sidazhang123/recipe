package me.sidazhang.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import me.sidazhang.recipe.exceptions.GetRoot;
import me.sidazhang.recipe.exceptions.ImageFormatException;
import me.sidazhang.recipe.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception e) {
        log.error(e.getMessage() + " /NOT FOUND");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/404");
        modelAndView.addObject("exception", GetRoot.getRootCause(e));
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormat(Exception e) {
        log.error(e.getMessage() + " /Bad Request");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/400");
        modelAndView.addObject("exception", GetRoot.getRootCause(e));
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ImageFormatException.class)
    public ModelAndView handleImageFormat(Exception e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/400");
        modelAndView.addObject("exception", GetRoot.getRootCause(e));
        return modelAndView;
    }
}
