import { encontrarUsuario } from "../data/usuarios-db.js";
import { autenticarUsuario } from "../utils/autenticarUsuario.js";
import { gerarJwt } from "../utils/gerarJwt.js";

export const registrarEventosLogin = (socket, io) => {
    socket.on("autenticar_usuario", async ({ usuario, senha }) => {
        const nome = await encontrarUsuario(usuario);
        if(nome) {
            const autenticado = autenticarUsuario(senha, nome);
            if(autenticado) {
                const tokenJwt = gerarJwt({ nomeUsuario: usuario });

                socket.emit("autenticacao_sucesso", tokenJwt);
            } else socket.emit("autenticacao_erro");
        } else {
            socket.emit("usuario_nao_encontrado");
        };
    });
};