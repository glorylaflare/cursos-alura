import { emitirAdicionarDocumento } from "./socket-front_index.js";

const listaDocumentos = document.getElementById("lista-documentos");
const form = document.getElementById("form-adiciona-documento");
const inputDocumento = document.getElementById("input-documento");

form.addEventListener("submit", (e) => {
    e.preventDefault();
    emitirAdicionarDocumento(inputDocumento.value);
    inputDocumento.value = "";
});

export const inserirLinkDocumento = (nomeDocumento) => {
    listaDocumentos.innerHTML += `
    <a
    href="documento.html?nome=${nomeDocumento}" 
    class="list-group-item list-group-item-action"
    id="documento-${nomeDocumento}">
    ${nomeDocumento}
    </a>`;
};

export const removerLinkDocumento = (nomeDocumento) => {
    const documento = document.getElementById(`documento-${nomeDocumento}`);
    listaDocumentos.removeChild(documento);
};