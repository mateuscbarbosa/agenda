package br.com.petshoptchutchucao.agenda.infra;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.petshoptchutchucao.agenda.model.response.Error400OutputDto;
import br.com.petshoptchutchucao.agenda.model.response.Error500OutputDto;

@RestControllerAdvice
public class ErrorHandling {

	@ExceptionHandler(BusinessRulesException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public String handleBusinessRulesException(BusinessRulesException ex) {
		return ex.getMessage();
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	public Error500OutputDto handleError500(Exception ex, HttpServletRequest req) {
		return new Error500OutputDto(
									LocalDateTime.now(),
									ex.getClass().toString(),
									ex.getMessage(),
									req.getRequestURI());
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public List<Error400OutputDto> handleError400(MethodArgumentNotValidException ex){
		return ex.getFieldErrors()
				  .stream()
				  .map(error -> new Error400OutputDto(error.getField(), error.getDefaultMessage()))
				  .collect(Collectors.toList());
	}
	
}
