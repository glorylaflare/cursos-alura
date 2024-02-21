import "dotenv/config";
import { registrarEventosCadastro } from "./events/cadastro.js";
import { registrarEventosDocumentos } from "./events/documento.js";
import { registrarEventosInicio } from "./events/inicio.js";
import { registrarEventosLogin } from "./events/login.js";
import { autorizarUsuario } from "./middlewares/autorizarUsuario.js";
import io from "./server.js";

const nspUsuarios = io.of("/usuarios");

nspUsuarios.use(autorizarUsuario);

nspUsuarios.on("connection", (socket) => {
    registrarEventosInicio(socket, nspUsuarios);
    registrarEventosDocumentos(socket, nspUsuarios);
});

io.of("/").on("connection", (socket) => {
    registrarEventosCadastro(socket, io);
    registrarEventosLogin(socket, io);
});