package br.com.alura.adopet.api.controller;

import br.com.alura.adopet.api.dto.AtualizacaoTutorDto;
import br.com.alura.adopet.api.dto.CadastroTutorDto;
import br.com.alura.adopet.api.service.TutorService;
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
class TutorControllerTest {

    @MockBean
    private TutorService tutorService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<CadastroTutorDto> jsonCadastroTutorDto;

    @Autowired
    private JacksonTester<AtualizacaoTutorDto> jsonAtualizaTutorDto;

    @Test
    @DisplayName("Deve devolver um código 200 para uma requisição de cadastro de tutor")
    void DeveDevolverCodigo200ParaCadastroTutor() throws Exception {
        CadastroTutorDto dto = new CadastroTutorDto("Marcelo", "(88)3432-3342", "marcelo@email.com");

        var response = mockMvc.perform(
                post("/tutores").content(jsonCadastroTutorDto.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve devolver um código 400 para uma requisição de cadastro de tutor com dados inválidos")
    void DeveDevolverCodigo400ParaCadastroTutor() throws Exception {
        CadastroTutorDto dto = new CadastroTutorDto("Marcelo", "(88)3432-33421", "marcelo@email.com");

        var response = mockMvc.perform(
                post("/tutores").content(jsonCadastroTutorDto.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    @DisplayName("Deve devolver um código 200 para uma requisição de atualização de tutor")
    void DeveDevolverCodigo200ParaAtualizarTutor() throws Exception {
        AtualizacaoTutorDto dto = new AtualizacaoTutorDto(1l, "Marcelo", "(88)3432-3342", "marcelo@email.com");

        var response = mockMvc.perform(
                post("/tutores").content(jsonAtualizaTutorDto.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    @DisplayName("Deve devolver um código 400 para uma requisição de atualização de tutor")
    void DeveDevolverCodigo400ParaAtualizarTutor() throws Exception {
        AtualizacaoTutorDto dto = new AtualizacaoTutorDto(1l, "Marcelo", "(88)3432-33422", "marcelo@email.com");

        var response = mockMvc.perform(
                post("/tutores").content(jsonAtualizaTutorDto.write(dto).getJson()).contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }
}