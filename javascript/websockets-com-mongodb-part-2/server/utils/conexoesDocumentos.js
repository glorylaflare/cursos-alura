const conexoesDocumentos = [];

export const encontrarConexao = (nome, nomeUsuario) => { 
    return conexoesDocumentos.find((conexao) => {
        return (conexao.nome === nome && conexao.nomeUsuario === nomeUsuario);
    });
};

export const adicionarConexao = (conexao) => {
    conexoesDocumentos.push(conexao);
};

export const obterUsuariosDocumento = (nome) => {
    return conexoesDocumentos
        .filter((conexao) => conexao.nome === nome)
        .map((conexao) => conexao.nomeUsuario);
};

export const removerConexao = (nome, nomeUsuario) => {
    const indice = conexoesDocumentos.findIndex((conexao) => {
        return (conexao.nome === nome && conexao.nomeUsuario === nomeUsuario);
    });

    if(indice !== -1) {
        conexoesDocumentos.splice(indice, 1);
    };
};