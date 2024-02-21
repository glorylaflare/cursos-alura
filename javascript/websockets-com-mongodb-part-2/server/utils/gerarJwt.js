import jwt from "jsonwebtoken";

export const gerarJwt = (payload) => {
    const tokenJwt = jwt.sign(payload, process.env.SEGREDO_JWT, {
        expiresIn: "1h",
    });

    return tokenJwt;
};