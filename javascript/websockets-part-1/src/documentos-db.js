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
    // const documento = documentos.find((documento) => {
    //     return documento.nome === nome;
    // });
    const documento = documentosColecao.findOne({
        nome: nome, //ou apenas "nome"
    });

    return documento;
};

export const atualizarDocumento = (nome, texto) => {
    const atualizacao = documentosColecao.updateOne({
        nome: nome, //ou apenas "nome"

    }, {
        $set: {
            texto: texto, //ou apenas "texto"
        }
    });

    return atualizacao;
};

export const excluirDocumento = (nome) => {
    const resultado = documentosColecao.deleteOne({
        nome: nome, //ou apenas "nome"
    });

    return resultado;
};