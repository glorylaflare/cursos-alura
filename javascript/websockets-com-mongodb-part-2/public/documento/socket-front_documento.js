import { obterCookie } from '../utils/cookies.js';
import { alertarERedirecionar, atualizaTextoEditor, atualizarInterfaceUsuarios, tratarAutorizacaoSucesso } from './documento.js';

const socket = io("/usuarios", {
    auth: {
        token: obterCookie("tokenJwt"),
    }
});

socket.on("autorizacao_sucesso", tratarAutorizacaoSucesso);

socket.on("connect_error", (erro) => {
    alert(erro);
    window.location.href = "/login/index.html";
});

export const selecionarDocumento = (dadosEntrada) => {
    socket.emit("selecionar_documento", dadosEntrada, (texto) => {
        atualizaTextoEditor(texto);
    });
};

socket.on("usuario_ja_no_documento", () => {
    alert("Documento já aberto em outra aba");
    window.location.href = "/";
});

socket.on("usuarios_no_documento", atualizarInterfaceUsuarios);

export const emitirTextoEditor = (dados) => {
    socket.emit('texto_editor', dados);
};

export const emitirExcluirDocumento = (nome) => {
    socket.emit("excluir_documento", nome);
}

socket.on('texto_editor_clientes', (texto) => {
    atualizaTextoEditor(texto); 
});

socket.on('excluir_documento_sucesso', (nome) => {
    alertarERedirecionar(nome);
});