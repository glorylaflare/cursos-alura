import { emitirCadastrarUsuarios } from "./socket-front-cadastro.js";

const form = document.getElementById("form-cadastro");

form.addEventListener("submit", (e) => {
    e.preventDefault();

    const usuario = form["input-usuario"].value;
    const senha = form["input-senha"].value;

    emitirCadastrarUsuarios({ usuario, senha });
});