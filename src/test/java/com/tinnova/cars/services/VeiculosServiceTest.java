package com.tinnova.cars.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinnova.cars.models.Marca;
import com.tinnova.cars.models.Veiculo;
import com.tinnova.cars.models.VeiculoDTO;
import com.tinnova.cars.repository.VeiculosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VeiculosServiceTest {

    @Mock
    private static VeiculosRepository veiculosRepository;
    @Spy
    private static ObjectMapper objectMapper;

    @InjectMocks
    private static VeiculosService veiculosService;

    Veiculo firstVeiculo = new Veiculo();
    
    @BeforeEach
    public void setup(){
        firstVeiculo.setId(1l);
        firstVeiculo.setVeiculo("first");
        firstVeiculo.setMarca(Marca.BMW);
        firstVeiculo.setAno(2000);
        firstVeiculo.setDescrição("Bonito");
    }
    
    @Test
    public void test_getAllCars(){
        //given
        when(veiculosRepository.findAll()).thenReturn(Collections.singletonList(firstVeiculo));
        //When
        List<Veiculo> list = veiculosService.getAllVeiculos();
        //Then
        assertNotNull(list);
        assertEquals(list.size(),1);
    }

    @Test
    public void test_addNewCar(){
        //given
        VeiculoDTO validDTO = VeiculoDTO.builder()
                .veiculo("first")
                .ano(2000)
                .marca(Marca.BMW)
                .build();
        when(veiculosRepository.save(any(Veiculo.class))).thenReturn(firstVeiculo);
        //When
        Veiculo response = veiculosService.addNewVeiculo(validDTO);
        //Then
        assertNotNull(response);
        assertEquals(response.getId(),1l);
    }

    @Test
    public void test_updateCar_notFound(){
        //given
        VeiculoDTO validDTO = VeiculoDTO.builder()
                .veiculo("first")
                .ano(2000)
                .marca(Marca.BMW)
                .build();
        //When
        Veiculo response = veiculosService.updateVeiculo(1l,validDTO);
        //Then
        assertEquals(response,new Veiculo());
    }

    @Test
    public void test_updateCar_Found(){
        //given
        VeiculoDTO validDTO = VeiculoDTO.builder()
                .veiculo("first")
                .ano(2000)
                .marca(Marca.BMW)
                .build();
        when(veiculosRepository.existsById(1l)).thenReturn(true);
        when(veiculosRepository.save(any(Veiculo.class))).thenReturn(firstVeiculo);
        //When
        Veiculo response = veiculosService.updateVeiculo(1l,validDTO);
        //Then
        assertNotNull(response);
        assertEquals(response.getId(),1l);
    }

    @Test
    public void test_getByID_notFound(){
        //given
        //When
        Veiculo response = veiculosService.getVeiculo(1l);
        //Then
        assertEquals(response,new Veiculo());
    }

    @Test
    public void test_getByID_Found(){
        //given
        when(veiculosRepository.findById(1l)).thenReturn(Optional.of(firstVeiculo));
        //When
        Veiculo response = veiculosService.getVeiculo(1l);
        //Then
        assertNotNull(response);
        assertEquals(response.getId(),1l);
    }


    @Test
    public void test_patch_notFound(){
        //given
        VeiculoDTO validDTO = VeiculoDTO.builder()
                .build();
        //When
        Veiculo response = veiculosService.getVeiculo(1l);
        //Then
        assertEquals(response,new Veiculo());
    }

    @Test
    public void test_patch_Found(){
        //given
        VeiculoDTO validDTO = VeiculoDTO.builder()
                .vendido(true)
                .build();

        Veiculo patchedVeiculo = new Veiculo();
        patchedVeiculo.setId(1l);
        patchedVeiculo.setVeiculo("first");
        patchedVeiculo.setMarca(Marca.BMW);
        patchedVeiculo.setAno(2000);
        patchedVeiculo.setDescrição("Bonito");
        patchedVeiculo.setVendido(true);
        when(veiculosRepository.findById(1l)).thenReturn(Optional.of(firstVeiculo));
        //only mocks for patched veiculo. This way testing that data from firstVeiculo is preserved
        when(veiculosRepository.save(patchedVeiculo)).thenReturn(firstVeiculo);
        //When
        Veiculo response = veiculosService.patchVeiculo(1l,validDTO);
        //Then
        assertNotNull(response);
        assertEquals(response.getId(),1l);

    }

    @Test
    public void test_delete(){
        //given
        when(veiculosRepository.findById(1l)).thenReturn(Optional.of(firstVeiculo));
        when(veiculosRepository.findAll()).thenReturn(Collections.singletonList(firstVeiculo));
        //When
        List<Veiculo> list = veiculosService.deleteVeiculo(1l);
        //Then
        assertNotNull(list);
        assertEquals(list.size(),1);
    }


    @Test
    public void test_findByVendido(){
        //given
        String searchType = "disponiveis";
        when(veiculosRepository.countByVendido(false)).thenReturn(2l);
        //When
        Map<String, Long> searchMap = veiculosService.findByParam(searchType);
        //Then
        assertNotNull(searchMap);
        assertEquals(searchMap.size(),1);
        assertTrue(searchMap.containsKey("disponiveis"));

    }

    @Test
    public void test_findByDecada(){
        //given
        String searchType = "decada";
        List<Object[]> response = new ArrayList<>();
        Object[] dec1 = new Object[2];
        dec1[0] = 200;
        dec1[1] = 1l;
        Object[] dec2 = new Object[2];
        dec2[0] = 201;
        dec2[1] = 3l;
        response.add(dec1);
        response.add(dec2);
        when(veiculosRepository.countByDecada()).thenReturn(response);
        //When
        Map<String, Long> searchMap = veiculosService.findByParam(searchType);
        //Then
        assertNotNull(searchMap);
        assertEquals(searchMap.size(),2);
        assertTrue(searchMap.containsKey("Década 2000"));
        assertTrue(searchMap.containsKey("Década 2010"));

    }

    @Test
    public void test_findByFabricante(){
        //given
        String searchType = "fabricante";
        List<Object[]> response = new ArrayList<>();
        Object[] dec1 = new Object[2];
        dec1[0] = Marca.BMW;
        dec1[1] = 1l;
        Object[] dec2 = new Object[2];
        dec2[0] = Marca.FIAT;
        dec2[1] = 3l;
        response.add(dec1);
        response.add(dec2);
        when(veiculosRepository.countByMarca()).thenReturn(response);
        //When
        Map<String, Long> searchMap = veiculosService.findByParam(searchType);
        //Then
        assertNotNull(searchMap);
        assertEquals(searchMap.size(),2);
        assertTrue(searchMap.containsKey("FIAT"));
        assertTrue(searchMap.containsKey("BMW"));

    }

    @Test
    public void test_find_badParam(){
        //given
        String searchType = "bla";
        //When
        Map<String, Long> searchMap = veiculosService.findByParam(searchType);
        //Then
        assertNull(searchMap);
    }

}
