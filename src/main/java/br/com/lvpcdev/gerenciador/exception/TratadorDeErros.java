package br.com.lvpcdev.gerenciador.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErroValidacaoDTO>> tratarErro400(MethodArgumentNotValidException ex) {

        List<FieldError> errosDoSpring = ex.getFieldErrors();
        List<ErroValidacaoDTO> listaErrosLimpa = new ArrayList<>();

        for (int i = 0; i < errosDoSpring.size(); i++) {
            FieldError erro = errosDoSpring.get(i);
            listaErrosLimpa.add(new ErroValidacaoDTO(erro.getField(), erro.getDefaultMessage()));
        }
        return ResponseEntity.badRequest().body(listaErrosLimpa);
    }

    @ExceptionHandler(IllegalAccessError.class)
    public ResponseEntity<String> tratarErroRegraDeNegocio(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> tratarErro500(Exception ex) {
        ex.printStackTrace();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Aconteceu um erro inesperado.");
    }

    public record ErroValidacaoDTO(String campo, String mensage) {}
}
