package com.tinnova.cars.controlers;


import com.tinnova.cars.models.Veiculo;
import com.tinnova.cars.models.VeiculoDTO;
import com.tinnova.cars.services.VeiculosService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/veiculos")
@AllArgsConstructor
public class VeiculosController {
    private VeiculosService veiculosService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Veiculo> getAllVeiculos() {
        return veiculosService.getAllVeiculos();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Veiculo addNewVeiculo(@RequestBody VeiculoDTO veiculo){
        return veiculosService.addNewVeiculo(veiculo);
    }
}
