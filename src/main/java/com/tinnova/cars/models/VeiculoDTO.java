package com.tinnova.cars.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VeiculoDTO {
    private String veiculo;
    private Marca marca;
    private Integer ano;
    private String descrição;
    private boolean vendido;
}
