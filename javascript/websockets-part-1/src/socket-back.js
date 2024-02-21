import { adicionarDocumento, atualizarDocumento, encontrarDocumento, excluirDocumento, obterDocumentos } from "./documentos-db.js";
import io from "./server.js";

// const documentos = [
//     {
//         nome: "JavaScript",
//         texto: "texto de javascript..."
//     },
//     {
//         nome: "Node",
//         texto: "texto de node..."
//     },
//     {
//         nome: "Socket.io",
//         texto: "texto de socket.io..."
//     },
// ];

io.on('connection', (socket) => {
    // console.log("Um cliente se conectou! ID:", socket.id);
    socket.on("obter_documentos", async (devolverDocumentos) => {
        const documentos = await obterDocumentos();

        devolverDocumentos(documentos);
    });
    
    socket.on("selecionar_documento", async (nome, devolverTexto) => {
        socket.join(nome);
        // const documento = encontrarDocumento(nome);
        const documento = await encontrarDocumento(nome);

        if(documento) {
            // socket.emit("texto_documento", documento.texto);
            devolverTexto(documento.texto);
        };
    });

    socket.on("adicionar_documento", async (nome) => {
        const documentoExiste = (await encontrarDocumento(nome)) !== null;
        
        if(documentoExiste) socket.emit("documento_existente", nome);
        else {
            const resultado = await adicionarDocumento(nome);
    
            if(resultado.acknowledged) io.emit("adicionar_documento_interface", nome);
        };
    });

    socket.on('texto_editor', async ({ texto, nomeDocumento }) => {
        // socket.broadcast.emit('texto_editor_clientes', texto);
        // const documento = encontrarDocumento(nomeDocumento);
        const atualizacao = await atualizarDocumento(nomeDocumento, texto);

        if(atualizacao.modifiedCount) {
            // documento.texto = texto;
            socket.to(nomeDocumento).emit('texto_editor_clientes', texto);
        };
    });
    
    socket.on("excluir_documento", async (nome) => {
        const resultado = await excluirDocumento(nome);

        if(resultado.deletedCount) io.emit("excluir_documento_sucesso", nome);
    });

    // socket.on("disconnect", (motivo) => {
    //     console.log(`Cliente "${socket.id}" desconectado!...Motivo: ${motivo}`);
    // });
});