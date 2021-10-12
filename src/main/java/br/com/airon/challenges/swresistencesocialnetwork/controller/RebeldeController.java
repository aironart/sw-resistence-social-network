package br.com.airon.challenges.swresistencesocialnetwork.controller;

import br.com.airon.challenges.swresistencesocialnetwork.domain.Rebelde;
import br.com.airon.challenges.swresistencesocialnetwork.exceptions.BusinessException;
import br.com.airon.challenges.swresistencesocialnetwork.request.CriaRebeldeRequest;
import br.com.airon.challenges.swresistencesocialnetwork.request.LocalizacaoBaseRequest;
import br.com.airon.challenges.swresistencesocialnetwork.service.RebeldeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("rebelde")
public class RebeldeController {

    @Autowired
    private RebeldeService rebeldeService;

    @Autowired
    private ObjectMapper mapper;

    @ApiOperation("Insere um novo rebelde e o retorna")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Rebelde criado com sucesso"),
            @ApiResponse(code = 400, message = "Ocorreu algum erro de validação")
    })
    @RequestMapping(path = "/", method = RequestMethod.POST,
            consumes = "application/json", produces = "application/json")
    public ResponseEntity criaRebelde(@RequestBody CriaRebeldeRequest criaRebeldeRequest) {
        try {
            var rebelde = rebeldeService.criaRebelde(criaRebeldeRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(rebelde);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e);
        }
    }

    @ApiOperation("Marca um rebelde como traidor")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Marcação efetuada com sucesso"),
            @ApiResponse(code = 400, message = "Ocorreu algum erro de validação")
    })
    @RequestMapping(path = "/{autor}/traidor/{traidor}", method = RequestMethod.POST,
            produces = "text/plain")
    public ResponseEntity marcaRebeldeTraidor(@ApiParam(value = "Rebelde autor da marcação de traidor") @PathVariable("autor") Rebelde autor,
                                              @ApiParam(value = "Rebelde que será marcado como traidor") @PathVariable("traidor") Rebelde traidor) {
        try {
            rebeldeService.marcaRebeldeTraidor(autor, traidor);
            return ResponseEntity.status(HttpStatus.OK).body("Rebelde marcado como traidor com sucesso");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e);
        }
    }

    @ApiOperation("Busca todos os rebeldes")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Retorna todos os rebeldes", response = Rebelde[].class),
            @ApiResponse(code = 400, message = "Ocorreu algum erro de validação")
    })
    @RequestMapping(path = "/", method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity findAllRebeldes() {
        try {
            var rebeldes = rebeldeService.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(rebeldes);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e);
        }
    }

    @ApiOperation("Busca um rebelde por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Retorna todos os rebeldes", response = Rebelde.class),
            @ApiResponse(code = 404, message = "Rebelde não encontrado para este ID")
    })
    @RequestMapping(path = "/{id}", method = RequestMethod.GET,
            produces = "application/json")
    public ResponseEntity findById(@ApiParam(value = "Identificador do Rebelde") @PathVariable("id") Long id) {
        try {
            var rebelde = rebeldeService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(rebelde);
        } catch (Exception e) {
            BusinessException businessException = new BusinessException(e.getMessage());
            businessException.setStatus(HttpStatus.NOT_FOUND);
            return ResponseEntity.status(businessException.getStatus())
                    .body(businessException);
        }
    }

    @ApiOperation("Atualiza a localização de um rebelde")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Localização atualizada com sucesso"),
            @ApiResponse(code = 400, message = "Ocorreu algum erro de validação")
    })
    @RequestMapping(path = "/{rebelde}/localizacao", method = RequestMethod.PUT,
            produces = "text/plain", consumes = "application/json")
    public ResponseEntity atualizaLocalizacaoRebelde(
            @ApiParam(value = "Rebelde para atualizar a localização")
            @PathVariable("rebelde") Rebelde autor,
            @RequestBody() LocalizacaoBaseRequest localizacaoBaseRequest) {
        try {
            rebeldeService.atualizaLocalizacaoRebelde(autor, localizacaoBaseRequest);
            return ResponseEntity.status(HttpStatus.OK).body("Localização do rebelde atualizada com sucesso");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e);
        }
    }
}
