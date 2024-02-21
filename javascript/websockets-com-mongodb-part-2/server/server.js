import express from 'express';
import url from 'url';
import path from 'path';
import http from 'http';
import { Server } from 'socket.io';
import "./data/db-connect.js";

const port = 3000;
const app = express();

const currentPath = url.fileURLToPath(import.meta.url);
const publicDir = path.join(currentPath, '../../', 'public');
app.use(express.static(publicDir));

const httpServer = http.createServer(app);

httpServer.listen(port, () => console.log(`Servidor ouvindo na porta http://localhost:${3000}`));

const io = new Server(httpServer);

export default io;