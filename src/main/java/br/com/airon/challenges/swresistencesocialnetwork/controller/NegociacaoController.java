package br.com.airon.challenges.swresistencesocialnetwork.controller;

import br.com.airon.challenges.swresistencesocialnetwork.exceptions.BusinessException;
import br.com.airon.challenges.swresistencesocialnetwork.request.NegociacaoRequest;
import br.com.airon.challenges.swresistencesocialnetwork.service.NegociacaoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/negociacao")
public class NegociacaoController {

    @Autowired
    private NegociacaoService negociacaoService;

    @ApiOperation("Atualiza a localização de um rebelde")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Negociação Realizada com sucesso"),
            @ApiResponse(code = 400, message = "Ocorreu algum erro de validação")
    })
    @RequestMapping(path = "/itens/rebeldes", method = RequestMethod.POST,
            produces = "text/plain", consumes = "application/json")
    public ResponseEntity realizaNegociacao(
            @RequestBody() NegociacaoRequest negociacaoRequest) {
        try {
            negociacaoService.realizaNegociacao(negociacaoRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Negociação realizada com sucesso");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e);
        }
    }

}
