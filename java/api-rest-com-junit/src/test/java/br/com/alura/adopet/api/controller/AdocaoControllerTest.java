package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.service.AdocaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AdocaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdocaoService service;

    @Autowired
    private JacksonTester<SolicitacaoAdocaoDto> jsonDto;

    @Test
    @DisplayName("Deve devolver um código 400 para uma solicitação de adoção com erros")
    void DeveDevolverCodigo400() throws Exception {
        String json = "{}";

        var response = mockMvc.perform(
                post("/adocoes").content(json).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deve devolver um código 200 para uma solicitação de adoção sem erros")
    void DeveDevolverCodigo200() throws Exception {
        SolicitacaoAdocaoDto dto = new SolicitacaoAdocaoDto(1l, 1l, "Motivo qualquer");

        var response = mockMvc.perform(
                post("/adocoes").content(jsonDto.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve devolver um código 200 para uma solicitação de aprovação de adoção")
    void DeveDevolverCodigo200AprovaAdocao() throws Exception {
        String json = """
                    {
                    "idAdocao": 1
                    }
                """;

        var response = mockMvc.perform(
                post("/adocoes/aprovar").content(json).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve devolver um código 400 para uma solicitação de aprovação de adoção inválida")
    void DeveDevolverCodigo400AprovaAdocao() throws Exception {
        String json = "{}";

        var response = mockMvc.perform(
                post("/adocoes/aprovar").content(json).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deve devolver um código 200 para uma solicitação de reprovação de adoção")
    void DeveDevolverCodigo200ReprovaAdocao() throws Exception {
        String json = """
                    {
                    "idAdocao": 1,
                    "justificativa: "qualquer justificativa"
                    }
                """;

        var response = mockMvc.perform(
                post("/adocoes/aprovar").content(json).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve devolver um código 400 para uma solicitação de reprovação de adoção inválida")
    void DeveDevolverCodigo400ReprovaAdocao() throws Exception {
        String json = "{}";

        var response = mockMvc.perform(
                post("/adocoes/aprovar").content(json).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }
}