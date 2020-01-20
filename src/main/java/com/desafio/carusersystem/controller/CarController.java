package com.desafio.carusersystem.controller;

import com.desafio.carusersystem.api.CarsApi;
import com.desafio.carusersystem.api.model.Cars;
import com.desafio.carusersystem.api.model.CarsResponse;
import com.desafio.carusersystem.api.model.MessageException;
import com.desafio.carusersystem.exceptions.*;
import com.desafio.carusersystem.service.CarrosService;
import com.desafio.carusersystem.utils.ModelToEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class CarController implements CarsApi {

    private CarrosService carsService;

    @Autowired
    public CarController(CarrosService carsService) {
        this.carsService = carsService;
    }

    @Override
    public ResponseEntity<Void> atualizaCarro(Long id, @Valid Cars body) {
        try {
            validarCarro(body);
            if (body.getId() != id) {
                return new ResponseEntity(new MessageException().message("Invalid fields").errorCode(Long.valueOf(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
            }
            carsService.saveCarro(ModelToEntity.carroModelToCarroEntity(body));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ExceptionNotFound e) {
            return new ResponseEntity(new MessageException().message(e.getMessage()).errorCode(Long.valueOf(HttpStatus.NOT_FOUND.value())),  HttpStatus.NOT_FOUND);
        } catch (ExceptionConflict e) {
            return new ResponseEntity(new MessageException().message(e.getMessage()).errorCode(Long.valueOf(HttpStatus.BAD_REQUEST.value())), HttpStatus.CONFLICT);
        } catch (ExceptionUnauthorized e) {
            return new ResponseEntity(new MessageException().message(e.getMessage()).errorCode(Long.valueOf(HttpStatus.UNAUTHORIZED.value())), HttpStatus.UNAUTHORIZED);
        } catch (BlankFields e) {
            return new ResponseEntity(new MessageException().message(e.getMessage()).errorCode(Long.valueOf(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Void> cadastrarCarro(@Valid Cars body) {
        try {
            validarCarro(body);
            carsService.saveCarro(ModelToEntity.carroModelToCarroEntity(body));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ExceptionNotFound e) {
            return new ResponseEntity(new MessageException().message(e.getMessage()).errorCode(Long.valueOf(HttpStatus.NOT_FOUND.value())),  HttpStatus.NOT_FOUND);
        } catch (ExceptionConflict e) {
            return new ResponseEntity(new MessageException().message(e.getMessage()).errorCode(Long.valueOf(HttpStatus.BAD_REQUEST.value())), HttpStatus.CONFLICT);
        } catch (ExceptionUnauthorized e) {
            return new ResponseEntity(new MessageException().message(e.getMessage()).errorCode(Long.valueOf(HttpStatus.UNAUTHORIZED.value())), HttpStatus.UNAUTHORIZED);
        } catch (BlankFields e) {
            return new ResponseEntity(new MessageException().message(e.getMessage()).errorCode(Long.valueOf(HttpStatus.BAD_REQUEST.value())), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<CarsResponse> listarCarros() {
        try {
            CarsResponse retorno = new CarsResponse();
            retorno.setCarros(ModelToEntity.listCarsEntityToCarsModel(carsService.listarCarros()));
            return new ResponseEntity<>(retorno, HttpStatus.OK);
        } catch (ExceptionNotFound e) {
            return new ResponseEntity(new MessageException().message(e.getMessage()).errorCode(Long.valueOf(HttpStatus.NOT_FOUND.value())),  HttpStatus.NOT_FOUND);
        } catch (ExceptionConflict e) {
            return new ResponseEntity(new MessageException().message(e.getMessage()).errorCode(Long.valueOf(HttpStatus.BAD_REQUEST.value())), HttpStatus.CONFLICT);
        } catch (ExceptionUnauthorized e) {
            return new ResponseEntity(new MessageException().message(e.getMessage()).errorCode(Long.valueOf(HttpStatus.UNAUTHORIZED.value())), HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Cars> recuperaCarro(Long id) {
        try {
            Cars retorno = new Cars();
            retorno = ModelToEntity.carsEntityToCarroModel(carsService.buscarCarro(id));
            return new ResponseEntity<>(retorno, HttpStatus.OK);
        } catch (ExceptionNotFound e) {
            return new ResponseEntity(new MessageException().message(e.getMessage()).errorCode(Long.valueOf(HttpStatus.NOT_FOUND.value())),  HttpStatus.NOT_FOUND);
        } catch (ExceptionConflict e) {
            return new ResponseEntity(new MessageException().message(e.getMessage()).errorCode(Long.valueOf(HttpStatus.BAD_REQUEST.value())), HttpStatus.CONFLICT);
        } catch (ExceptionUnauthorized e) {
            return new ResponseEntity(new MessageException().message(e.getMessage()).errorCode(Long.valueOf(HttpStatus.UNAUTHORIZED.value())), HttpStatus.UNAUTHORIZED);
        }
    }

    @Override
    public ResponseEntity<Void> removeCarro(Long id) {
        try {
            carsService.removerCarro(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ExceptionNotFound e) {
            return new ResponseEntity(new MessageException().message(e.getMessage()).errorCode(Long.valueOf(HttpStatus.NOT_FOUND.value())),  HttpStatus.NOT_FOUND);
        } catch (ExceptionConflict e) {
            return new ResponseEntity(new MessageException().message(e.getMessage()).errorCode(Long.valueOf(HttpStatus.BAD_REQUEST.value())), HttpStatus.CONFLICT);
        } catch (ExceptionUnauthorized e) {
            return new ResponseEntity(new MessageException().message(e.getMessage()).errorCode(Long.valueOf(HttpStatus.UNAUTHORIZED.value())), HttpStatus.UNAUTHORIZED);
        }
    }

    private void validarCarro(Cars carro) {
        if (carro.getModel().isEmpty() || carro.getColor().isEmpty() || carro.getLicensePlate().isEmpty() || carro.getYear() == null || carro.getYear() == 0) {
            throw new BlankFields();
        }
    }
}
