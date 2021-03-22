package com.tinnova.cars.repository;

import com.tinnova.cars.models.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VeiculosRepository extends JpaRepository<Veiculo, Long> {

    Long countByVendido(boolean vendido);

    @Query("SELECT marca, COUNT(*) FROM Veiculo  GROUP BY marca")
    List<Object[]> countByMarca();

    @Query("SELECT (ano / 10), COUNT(*) FROM Veiculo GROUP BY (ano / 10) ")
    List<Object[]> countByDecada();

}
