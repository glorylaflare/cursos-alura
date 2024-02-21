import { alertarERedirecionar, atualizaTextoEditor } from './documento.js';
const socket = io();

export const selecionarDocumento = (nome) => {
    socket.emit("selecionar_documento", nome, (texto) => {
        atualizaTextoEditor(texto);
    });
};

export const emitirTextoEditor = (dados) => {
    socket.emit('texto_editor', dados);
};

export const emitirExcluirDocumento = (nome) => {
    socket.emit("excluir_documento", nome);
}

// socket.on("texto_documento", (texto) => {
//     atualizaTextoEditor(texto);
// });

socket.on('texto_editor_clientes', (texto) => {
    atualizaTextoEditor(texto); 
});

socket.on('excluir_documento_sucesso', (nome) => {
    alertarERedirecionar(nome);
});