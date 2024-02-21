import { randomBytes, scryptSync } from "crypto";

export const criaHashESalSenha = (senha) => {
    const salSenha = randomBytes(16).toString("hex");
    const hashSenha = scryptSync(senha, salSenha, 64).toString("hex");

    return { salSenha, hashSenha };
};