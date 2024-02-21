import { documentosColecao } from "./db-connect.js";

export const obterDocumentos = () => {
    const documentos = documentosColecao.find().toArray();
    return documentos;
};

export const adicionarDocumento = (nome) => {
    const resultado = documentosColecao.insertOne({
        nome: nome,
        texto: "",
    });

    return resultado;
};

export const encontrarDocumento = (nome) => {
    const documento = documentosColecao.findOne({
        nome: nome, 
    });

    return documento;
};

export const atualizarDocumento = (nome, texto) => {
    const atualizacao = documentosColecao.updateOne({
        nome: nome, 

    }, {
        $set: {
            texto: texto,
        }
    });

    return atualizacao;
};

export const excluirDocumento = (nome) => {
    const resultado = documentosColecao.deleteOne({
        nome: nome, 
    });

    return resultado;
};