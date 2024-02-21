import { emitirExcluirDocumento, emitirTextoEditor, selecionarDocumento } from './socket-front_documento.js';

const parametros = new URLSearchParams(window.location.search);
const nomeDocumento = parametros.get('nome');

const textoEditor = document.getElementById("editor-texto");
const tituloDocumento = document.getElementById("titulo-documento");
const botaoExcluir = document.getElementById("excluir-documento");

tituloDocumento.textContent = nomeDocumento || "Documento sem título";
selecionarDocumento(nomeDocumento);

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