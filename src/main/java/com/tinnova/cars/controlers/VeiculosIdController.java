package com.tinnova.cars.controlers;

import com.tinnova.cars.models.Veiculo;
import com.tinnova.cars.models.VeiculoDTO;
import com.tinnova.cars.services.VeiculosService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/veiculos/{id}")
@AllArgsConstructor
public class VeiculosIdController {
    private VeiculosService veiculosService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Veiculo getVeiculo(
            @PathVariable("id") long id) {
        return veiculosService.getVeiculo(id);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
        public Veiculo updateVeiculo(
        @PathVariable("id") long id, @RequestBody VeiculoDTO veiculo) {
        return veiculosService.updateVeiculo(id,veiculo);
    }

    @PatchMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Veiculo patchVeiculo(
            //Uses HashMap so empty fields are not populated with null
            @PathVariable("id") long id, @RequestBody VeiculoDTO veiculo) {
        return veiculosService.patchVeiculo(id,veiculo);
    }

    @DeleteMapping
    public List<Veiculo> deleteVeiculo(
            @PathVariable("id") long id) {
        return veiculosService.deleteVeiculo(id);
    }

}
