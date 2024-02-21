package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.CadastroAbrigoDto;
import br.com.alura.adopet.api.dto.CadastroPetDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Abrigo;
import br.com.alura.adopet.api.model.TipoPet;
import br.com.alura.adopet.api.service.AbrigoService;
import br.com.alura.adopet.api.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AbrigoControllerTest {

    @MockBean
    private AbrigoService abrigoService;

    @MockBean
    private PetService petService;

    @Mock
    private Abrigo abrigo;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<CadastroAbrigoDto> jsonCadastroDto;

    @Autowired
    private JacksonTester<CadastroPetDto> jsonPetDto;


    @Test
    @DisplayName("Deve devolver um código 200 para uma requisição de listar abrigos")
    void DeveDevolverCodigo200ParaListagem() throws Exception {
        var response = mockMvc.perform(
                get("/abrigos")
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve devolver um código 200 para uma requisição de cadastro de abrigo")
    void DeveDevolverCodigo200ParaCadastro() throws Exception {
        CadastroAbrigoDto dto = new CadastroAbrigoDto("Abrigo feliz", "(11)8823-4230", "abrigofeliz@email.com");

        var response = mockMvc.perform(
                post("/abrigos").content(jsonCadastroDto.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve devolver um código 400 para uma requisição de cadastro de abrigo")
    void DedeDevolver400ParaCadastro() throws Exception {
        CadastroAbrigoDto dto = new CadastroAbrigoDto("Abrigo feliz", "(11)98823-4230", "abrigofeliz@email.com");

        var response = mockMvc.perform(
                post("/abrigos").content(jsonCadastroDto.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deve devolver um código 200 para uma listagem de pets do abrigo filtrado por nome")
    void DeveDevolver200ParaListarPetAbrigoPorNome() throws Exception {
        String nome = "Abrigo feliz";

        var response = mockMvc.perform(
                get("/abrigos/{nome}/pets", nome)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve devolver um código 200 para uma listagem de pets do abrigo filtrado por id")
    void DeveDevolver200ParaListarPetAbrigoPorId() throws Exception {
        String id = "1";

        var response = mockMvc.perform(
                get("/abrigos/{id}/pets", id)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve devolver um código 400 para uma listagem de pets do abrigo filtrado por nome inválido")
    void DeveDevolver404ParaListarPetAbrigoPorNome() throws Exception {
        String nome = "Abrigo da felicidade";
        given(abrigoService.listarPetsDoAbrigo(nome)).willThrow(ValidacaoException.class);

        var response = mockMvc.perform(
                get("/abrigos/{nome}/pets", nome)
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("Deve devolver um código 400 para uma listagem de pets do abrigo filtrado por id inválido")
    void DeveDevolver404ParaListarPetAbrigoPorId() throws Exception {
        String id = "1";
        given(abrigoService.listarPetsDoAbrigo(id)).willThrow(ValidacaoException.class);

        var response = mockMvc.perform(
                get("/abrigos/{id}/pets", id)
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("Deve devolver um código 200 para uma requisição de cadastro de pet pelo id do abrigo")
    void DedeDevolver200ParaCadastroPetPorAbrigoId() throws Exception {
        CadastroPetDto dto = new CadastroPetDto(
                TipoPet.GATO, "Lua", "siames", 7, "laranja", 5.3f
        );

        String idAbrigo = "1";

        var response = mockMvc.perform(
                post("/abrigos/{idAbrigo}/pets", idAbrigo).content(jsonPetDto.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve devolver um código 200 para uma requisição de cadastro de pet pelo nome do abrigo")
    void DedeDevolver200ParaCadastroPetPorAbrigoNome() throws Exception {
        CadastroPetDto dto = new CadastroPetDto(
                TipoPet.GATO, "Lua", "siames", 7, "laranja", 5.3f
        );

        String abrigoNome = "Abrigo feliz";

        var response = mockMvc.perform(
                post("/abrigos/{abrigoNome}/pets", abrigoNome).content(jsonPetDto.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve devolver um código 200 para uma requisição de cadastro de pet pelo id inválido do abrigo")
    void DedeDevolver400ParaCadastroPetPorAbrigoId() throws Exception {
        CadastroPetDto dto = new CadastroPetDto(
                TipoPet.GATO, "Lua", "siames", 7, "laranja", 5.3f
        );

        String idAbrigo = "1";
        given(abrigoService.carregarAbrigo(idAbrigo)).willThrow(ValidacaoException.class);

        var response = mockMvc.perform(
                post("/abrigos/{idAbrigo}/pets", idAbrigo).content(jsonPetDto.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }

    @Test
    @DisplayName("Deve devolver um código 200 para uma requisição de cadastro de pet pelo nome inválido do abrigo")
    void DedeDevolver400ParaCadastroPetPorAbrigoNome() throws Exception {
        CadastroPetDto dto = new CadastroPetDto(
                TipoPet.GATO, "Lua", "siames", 7, "laranja", 5.3f
        );

        String abrigoNome = "Abrigo feliz";
        given(abrigoService.carregarAbrigo(abrigoNome)).willThrow(ValidacaoException.class);

        var response = mockMvc.perform(
                post("/abrigos/{abrigoNome}/pets", abrigoNome).content(jsonPetDto.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(404, response.getStatus());
    }
}