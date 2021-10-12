package br.com.airon.challenges.swresistencesocialnetwork.validator;

import br.com.airon.challenges.swresistencesocialnetwork.exceptions.BusinessException;

/**
 * Interface para validar o corpo das requisições
 * @param <T>
 */
public interface BusinessValidator<T> {

    /**
     * Realiza a validação do corpo da requisição
     * @param requestBody
     * @throws BusinessException
     */
    void validate(T requestBody) throws BusinessException;

}
