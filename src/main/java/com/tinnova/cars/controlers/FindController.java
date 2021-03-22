package com.tinnova.cars.controlers;

import com.tinnova.cars.services.VeiculosService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping(value = "/veiculos/find")
@AllArgsConstructor
public class FindController {
    private VeiculosService veiculosService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Long> getAllVeiculos(@RequestParam("q") String searchType) {
        return veiculosService.findByParam(searchType);
    }
}
