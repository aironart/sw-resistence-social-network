package br.com.airon.challenges.swresistencesocialnetwork.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

import javax.validation.constraints.NotNull;

/**
 * Representa um erro ocorrido para retornar na requisição
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessError {

    public BusinessError(String message){
        this.message = message;
    }

    /**
     * Status do erro
     */
    private HttpStatus status = HttpStatus.BAD_REQUEST;

    /**
     * Mensagem do erro
     */
    @NotNull
    private String message;

}
