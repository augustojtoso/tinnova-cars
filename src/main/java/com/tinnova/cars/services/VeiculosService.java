package com.tinnova.cars.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinnova.cars.models.Marca;
import com.tinnova.cars.models.Veiculo;
import com.tinnova.cars.models.VeiculoDTO;
import com.tinnova.cars.repository.VeiculosRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class VeiculosService {
    private VeiculosRepository veiculosRepository;
    private ObjectMapper objectMapper;

    public List<Veiculo> getAllVeiculos(){
        return veiculosRepository.findAll();

    }

    public Veiculo addNewVeiculo(VeiculoDTO veiculodto){
        return veiculosRepository.save(objectMapper.convertValue(veiculodto,Veiculo.class));
    }

    public Veiculo updateVeiculo(long id, VeiculoDTO veiculo) {
        Veiculo updateVeiculo = objectMapper.convertValue(veiculo,Veiculo.class);
        updateVeiculo.setId(id);
        return veiculosRepository.existsById(id) ? veiculosRepository.save(updateVeiculo) : new Veiculo(); //Update if exist
    }

    public Veiculo getVeiculo(long id) {
        return veiculosRepository.findById(id).orElse(new Veiculo());
    }

    public Veiculo patchVeiculo(long id, VeiculoDTO veiculodto) {
        return veiculosRepository.findById(id).stream()//checks if id exist on DB
                .findFirst()
                .map(veiculoOld -> {
                    HashMap<String,Object> veiculoOldMap = objectMapper.convertValue(veiculoOld,HashMap.class);
                    HashMap<String,Object> veiculoPatchMap = objectMapper.convertValue(veiculodto,HashMap.class);
                    veiculoPatchMap.forEach((key,value) -> {
                        if (value != null){ //Replace only populate fields
                            veiculoOldMap.replace(key,value);
                        }
                    });
                    veiculoOld = objectMapper.convertValue(veiculoOldMap,Veiculo.class); //Reconverts to object
                    return veiculosRepository.save(veiculoOld);}) //Save new info
                .orElse(new Veiculo()); //If id is invalid, return null
    }

    public List<Veiculo> deleteVeiculo(long id) {
        veiculosRepository.findById(id).ifPresent(veiculosRepository::delete);
        return getAllVeiculos();
    }

    public Map<String, Long> findByParam(String searchType) {
        switch (searchType) {
            case "disponiveis":
                return Collections.singletonMap(
                        "disponiveis"
                        ,veiculosRepository.countByVendido(false));
            case "decada":
                return veiculosRepository.countByDecada().stream()
                        .collect(Collectors.toMap(o -> "Década " + ((Integer) o[0]).toString() + "0" , o -> (Long) o[1]));
            case "fabricante":
                return veiculosRepository.countByMarca().stream()
                        .collect(Collectors.toMap(o -> ((Marca) o[0]).toString() , o -> (Long) o[1]));
            default:
                return null;
        }
    }
}
