package br.com.alura.adopet.api.service;

import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.repository.AbrigoRepository;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class AbrigoServiceTest {

    @InjectMocks
    private AbrigoService abrigoService;

    @Mock
    private AbrigoRepository abrigoRepository;

    @Mock
    private Abrigo abrigo;

    @Mock
    private PetRepository petRepository;

    @Test
    @DisplayName("Deve chamar uma lista com todos os abrigos")
    void DeveChamarListaDeTodosOsAbrigos() {
        abrigoService.listar();
        then(abrigoRepository).should().findAll();
    }

    @Test
    @DisplayName("Deve chamar uma lista de pets do abrigo através do nome")
    void DeveChamarListaDePetsAtravesDoNome() {
        String nome = "Lua";
        given(abrigoRepository.findByNome(nome)).willReturn(Optional.of(abrigo));

        abrigoService.listarPetsDoAbrigo(nome);

        then(petRepository).should().findByAbrigo(abrigo);
    }
}