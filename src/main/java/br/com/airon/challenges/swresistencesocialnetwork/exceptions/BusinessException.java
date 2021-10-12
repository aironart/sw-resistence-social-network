package br.com.airon.challenges.swresistencesocialnetwork.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma Exceção de negócio
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"stackTrace", "cause", "message", "suppressed", "localizedMessage"})
public class BusinessException extends RuntimeException{

    public BusinessException(String message){
        listaErros.add(new BusinessError(message));
    }

    public BusinessException(List<BusinessError> listaErros){
        this.listaErros = listaErros;
    }

    private HttpStatus status = HttpStatus.BAD_REQUEST;

    /**
     * Lista de erros
     */
    private List<BusinessError> listaErros = new ArrayList<>();
}
