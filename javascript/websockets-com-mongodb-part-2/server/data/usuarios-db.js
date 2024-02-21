import { criaHashESalSenha } from "../utils/criaHashESalSenha.js";
import { usuariosColecao } from "./db-connect.js"

export const cadastrarUsuario = ({ usuario, senha }) => {
    const { hashSenha, salSenha } = criaHashESalSenha(senha);

    return usuariosColecao.insertOne({ 
        nome:  usuario, 
        hashSenha,
        salSenha, 
    });
};

export const encontrarUsuario = (nome) => {
    return usuariosColecao.findOne({ 
        nome: nome,
    });
};