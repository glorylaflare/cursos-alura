import { atualizarDocumento, encontrarDocumento, excluirDocumento } from "../data/documentos-db.js";
import { adicionarConexao, encontrarConexao, obterUsuariosDocumento, removerConexao } from "../utils/conexoesDocumentos.js";

export const registrarEventosDocumentos = (socket, io) => {
    socket.on("selecionar_documento", async ({ nome, nomeUsuario }, devolverTexto) => {
        const documento = await encontrarDocumento(nome);
        
        if(documento) {
            const conexaoEncontrada = encontrarConexao(nome, nomeUsuario);
            if(!conexaoEncontrada) {
                socket.join(nome);
    
                adicionarConexao({ nome, nomeUsuario });
                
                socket.data = {
                    usuarioEntrou: true,
                };

                const usuariosNoDocumento = obterUsuariosDocumento(nome);
                io.to(nome).emit("usuarios_no_documento", usuariosNoDocumento);
                
                devolverTexto(documento.texto);
            } else { 
                socket.emit("usuario_ja_no_documento");
            };
        };
        
        socket.on('texto_editor', async ({ texto, nomeDocumento }) => {
            const atualizacao = await atualizarDocumento(nomeDocumento, texto);
    
            if(atualizacao.modifiedCount) {
                socket.to(nomeDocumento).emit('texto_editor_clientes', texto);
            };
        });
        
        socket.on("excluir_documento", async (nome) => {
            const resultado = await excluirDocumento(nome);
    
            if(resultado.deletedCount) io.emit("excluir_documento_sucesso", nome);
        });

        socket.on("disconnect", () => {
            if(socket.data.usuarioEntrou) {
                removerConexao(nome, nomeUsuario);
    
                const usuariosNoDocumento = obterUsuariosDocumento(nome);
                io.to(nome).emit("usuarios_no_documento", usuariosNoDocumento);
            };
        });
    });
};