package com.desafio.carusersystem.controller;

import com.desafio.carusersystem.api.CarsApi;
import com.desafio.carusersystem.api.model.Cars;
import com.desafio.carusersystem.api.model.CarsResponse;
import com.desafio.carusersystem.exceptions.ExceptionConflict;
import com.desafio.carusersystem.exceptions.ExceptionNotFound;
import com.desafio.carusersystem.exceptions.Message;
import com.desafio.carusersystem.service.CarrosService;
import com.desafio.carusersystem.utils.ModelToEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin
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
        return null;
    }

    @Override
    public ResponseEntity<Void> cadastrarCarro(@Valid Cars body) {
        return null;
    }

    @Override
    public ResponseEntity<CarsResponse> listarCarros() {
        try {
            CarsResponse retorno = new CarsResponse();
            retorno.setCarros(ModelToEntity.listCarsEntityToCarsModel(carsService.listarCarros()));
            return new ResponseEntity<>(retorno, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(new Message(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Cars> recuperaCarro(Long id) {
        try {
            Cars retorno = new Cars();
            retorno = ModelToEntity.carsEntityToCarroModel(carsService.buscarCarro(id));
            return new ResponseEntity<>(retorno, HttpStatus.OK);
        } catch (ExceptionNotFound e) {
            return new ResponseEntity(new Message(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.NOT_FOUND);
        } catch (ExceptionConflict e) {
            return new ResponseEntity(new Message(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.CONFLICT);
        }
    }

    @Override
    public ResponseEntity<Void> removeCarro(Long id) {
        try {
            carsService.removerCarro(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (ExceptionNotFound e) {
            return new ResponseEntity(new Message(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.NOT_FOUND);
        } catch (ExceptionConflict e) {
            return new ResponseEntity(new Message(e.getMessage(), HttpStatus.BAD_REQUEST), HttpStatus.CONFLICT);
        }
    }
}
