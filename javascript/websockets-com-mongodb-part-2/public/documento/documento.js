import { emitirExcluirDocumento, emitirTextoEditor, selecionarDocumento } from './socket-front_documento.js';

const parametros = new URLSearchParams(window.location.search);
const nomeDocumento = parametros.get('nome');

const textoEditor = document.getElementById("editor-texto");
const tituloDocumento = document.getElementById("titulo-documento");
const botaoExcluir = document.getElementById("excluir-documento");
const listaUsuariosConectados = document.getElementById("usuarios-conectados");

tituloDocumento.textContent = nomeDocumento || "Documento sem título";

function tratarAutorizacaoSucesso(payloadToken) {
    selecionarDocumento({ nome: nomeDocumento, nomeUsuario: payloadToken.nomeUsuario });
};

function atualizarInterfaceUsuarios(usuariosNoDocumento) {
    listaUsuariosConectados.innerHTML = "";
    usuariosNoDocumento.forEach((usuario) => {
        listaUsuariosConectados.innerHTML += `
            <li class="list-group-item">${usuario}</li>
        `;
    });
};

textoEditor.addEventListener('keyup', () => {
    emitirTextoEditor({
        texto: textoEditor.value, 
        nomeDocumento,
    });
});

botaoExcluir.addEventListener('click', () => {
    emitirExcluirDocumento(nomeDocumento);
});

export const atualizaTextoEditor = (texto) => {
    textoEditor.value = texto;
};

export const alertarERedirecionar = (nome) => {
    if(nome === nomeDocumento) {
        alert(`Documento ${nome} foi excluido.`);
        window.location.href = "/";
    };
};

export { tratarAutorizacaoSucesso, atualizarInterfaceUsuarios };