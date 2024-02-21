import { cadastrarUsuario, encontrarUsuario } from "../data/usuarios-db.js";

export const registrarEventosCadastro = (socket, io) => {
    socket.on("cadastrar_usuario", async (dados) => {
        const usuario = await encontrarUsuario(dados.usuario);

        if(usuario === null) {
            const resultado = await cadastrarUsuario(dados);
    
            if(resultado.acknowledged) socket.emit("cadastro_sucesso");
            else socket.emit("cadastro_erro");
        } else {
            socket.emit("usuario_existente");
        };
    });
};