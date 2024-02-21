/* eslint-disable linebreak-style */
import request from 'supertest';
import {
  describe, expect, it, jest,
} from '@jest/globals';
import app from '../../app.js';

let server;
beforeEach(() => {
  const port = 4040;
  server = app.listen(port);
});

afterEach(() => {
  server.close();
});

describe('GET em /editoras', () => {
  it('Deve retornar uma lista de editoras', async () => {
    const resposta = await request(app)
      .get('/editoras')
      .set('Accept', 'application/json')
      .expect('content-type', /json/)
      .expect(200);

    expect(resposta.body[0].email).toEqual('e@e.com');
  });
});

let id;
describe('POST em /editoras', () => {
  it('Deve adicionar uma nova editora', async () => {
    const resposta = await request(app)
      .post('/editoras')
      .send({
        nome: 'CDC',
        cidade: 'Recife',
        email: 's@s.com',
      })
      .expect(201);

    id = resposta.body.content.id;
  });

  it('Deve adicionar nada ao passar um body vazio', async () => {
    await request(app)
      .post('/editoras')
      .send({})
      .expect(400);
  });
});

describe('DELETE em /editoras', () => {
  it('Deve deeletar o recurso adicionado', async () => {
    await request(app)
      .delete(`/editoras/${id}`)
      .expect(200);
  });
});

describe('GET em /editoras/id', () => {
  it('Deve retornar o recurso selecionado', async () => {
    await request(app)
      .get(`/editoras/${id}`)
      .expect(200);
  });
});

describe('PUT em /editoras/id', () => {
  it.each([
    ['nome', { nome: 'Casa do Codigo' }],
    ['cidade', { cidade: 'SP' }],
    ['email', { email: 'cdc@cdc.com' }],
  ])('Deve alterar o campo %s', async (chave, param) => {
    const requisicao = { request };
    const spy = jest.spyOn(requisicao, 'request');
    await requisicao.request(app)
      .put(`/editoras/${id}`)
      .send(param)
      .expect(500);

    expect(spy).toHaveBeenCalled();
  });
});

describe('DELETE em /editoras/id', () => {
  it('Deve deletar o recurso selecionado', async () => {
    await request(app)
      .delete(`/editoras/${id}`)
      .expect(200);
  });
});
