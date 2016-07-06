package ru.pkg.web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.pkg.utils.exception.ErrorInfo;
import ru.pkg.utils.exception.NotFoundException;
import ru.pkg.utils.exception.VotingException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;

@ControllerAdvice(annotations = RestController.class)
public interface ErrorInfoExceptionHandler {

    Logger LOG = LoggerFactory.getLogger(ErrorInfoExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE)
    default ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE)
    default ErrorInfo handleBindException(HttpServletRequest req, BindingResult result) throws Exception {
        StringBuilder sb = new StringBuilder();
        result.getFieldErrors().forEach(fe -> sb.append(fe.getField()).append(": ").append(fe.getDefaultMessage()).append("<br>"));
        return logAndGetErrorInfo(req, new ValidationException(sb.toString()), true);
    }

    @ExceptionHandler(VotingException.class)
    @ResponseStatus(value = HttpStatus.PRECONDITION_FAILED)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE)
    default ErrorInfo handleVotingException(HttpServletRequest req, VotingException e) {
        return logAndGetErrorInfo(req, e, true);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ResponseBody
    @Order(Ordered.HIGHEST_PRECEDENCE)
    default ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        return logAndGetErrorInfo(req, e, true);
    }

    default ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e, boolean logException) {
        LOG.error("Exception at request " + req.getRequestURL(), logException ? e : null);
        return new ErrorInfo(req.getRequestURL(), e);
    }
}
