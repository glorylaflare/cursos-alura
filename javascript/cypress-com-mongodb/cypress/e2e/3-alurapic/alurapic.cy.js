describe('Login e registro de usuários Alurapic', () => {
    beforeEach(() => {
        cy.visit('https://alura-fotos.herokuapp.com');
        /* cy.intercept('POST', 'https://apialurapic.herokuapp.com/user/login', {
            statusCode: 400
        }).as('stubPost'); */
    })

    it('verifica mensagens de validação', () => {
        cy.contains('a', 'Register now').click();
        cy.contains('button', 'Register').click();
        cy.contains('ap-vmessage', 'Email is required!').should('be.visible');
        cy.contains('button', 'Register').click();
        cy.contains('ap-vmessage', 'User name is required!').should('be.visible');
        cy.contains('ap-vmessage', 'Password is required!').should('be.visible');
        cy.contains('ap-vmessage', 'Full name is required!').should('be.visible');
    })

    it('verifica mensagem de e-mail inválido', () => {
        cy.contains('a', 'Register now').click();
        cy.contains('button', 'Register').click();
        cy.get('input[formcontrolname="email"]').type('marcelo');
        cy.contains('ap-vmessage', 'Invalid e-mail').should('be.visible');
    })

    it('verifica mensagem de nome completo curto demais', () => {
        cy.contains('a', 'Register now').click();
        cy.contains('button', 'Register').click();
        cy.get('input[formcontrolname="fullName"]').type('a');
        cy.contains('button', 'Register').click();
        cy.contains('ap-vmessage', 'Mininum length is 2').should('be.visible');
    })

    it('verifica mensagem de nome de usuário com letra maiúscula', () => {
        cy.contains('a', 'Register now').click();
        cy.contains('button', 'Register').click();
        cy.get('input[formcontrolname="userName"]').type('GloryLaFlare');
        cy.contains('button', 'Register').click();
        cy.contains('ap-vmessage', 'Must be lower case').should('be.visible');
    })

    it('verifica mensagem de senha com menos de 8 caracteres', () => {
        cy.contains('a', 'Register now').click();
        cy.contains('button', 'Register').click();
        cy.get('input[formcontrolname="password"]').type('123');
        cy.contains('button', 'Register').click();
        cy.contains('ap-vmessage', 'Mininum length is 8').should('be.visible');
    })

    it.only('fazer login de usuário válido', () => {
        cy.login(Cypress.env('userName'), Cypress.env('password'))
        //cy.wait('@stubPost')
        cy.contains('a', '(Logout)').should('be.visible');
    })

    it('fazer login de usuário inválido', () => {
        cy.login('marcelo', '5435')
        cy.on('window:alert', (str) => {
            expect(str).to.equal('Invalid user name or password')
        })
    })

    const usuarios = require('../../fixtures/usuarios.json');
    usuarios.forEach(usuario => {
        it('registrar novo usuário ' + usuario.userName, () => {
            cy.registra(usuario.email, usuario.fullName, usuario.userName, usuario.password)
        })
    })
})