import NaoEncontrado from "../error/NaoEncontrado.js";
import { autores } from "../models/index.js";

class AutorController {

  static listarAutores = async (req, res, next) => {
    try {
      const autoresResultado = autores.find();
  
      req.resultado = autoresResultado;

      next();
    } catch (erro) {
      next(erro);
    }
  };

  static listarAutorPorId = async (req, res, next) => {
    try {
      const id = req.params.id;

      const autorResultadoPorId = await autores.findById(id);

      if(autorResultadoPorId !== null) {
        res.status(200).send(autorResultadoPorId);
      } else {
        next(new NaoEncontrado("Id do Autor não localizado."));
      }
    } catch (erro) {
      next(erro);
    }
  };


  static cadastrarAutor = async (req, res, next) => {
    try {
      let autor = new autores(req.body);
      const autorCadastrado = await autor.save();

      res.status(201).send(autorCadastrado.toJSON());
    } catch (erro) {
      next(erro);
    }
  };


  static atualizarAutor = async (req, res, next) => {
    try {
      const id = req.params.id;
  
      const autorAtualizado = await autores.findByIdAndUpdate(id, {$set: req.body});

      if(autorAtualizado !== null) {
        res.status(200).send({message: "Autor atualizado com sucesso"});
      } else {
        next(new NaoEncontrado("Id do Autor não localizado."));
      }
    } catch (erro) {
      next(erro);
    }
  };


  static excluirAutor = async (req, res, next) => {
    try {
      const id = req.params.id;
  
      const autorExcluido = await autores.findByIdAndDelete(id);

      if(autorExcluido !== null) {
        res.status(200).send({message: "Autor removido com sucesso"});
      } else {
        next(new NaoEncontrado("Id do Autor não localizado."));
      }
    } catch (erro) {
      next(erro);
    }
  };

}

export default AutorController;