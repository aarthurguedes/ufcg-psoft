let assert = require('assert');
let Disciplina = require('./scoord').Disciplina;
let Turma = require('./scoord').Turma
let Estudante = require('./scoord').Estudante
let Professor = require('./scoord').Professor

describe('factory Disciplina', function() {
  let d0;

    before(async () => {
        d0 = new Disciplina('prog1', 'Programação 1', 4, []);
    })

    it('deve criar disciplinas distintas a cada invocação', function(){
        d0 = new Disciplina('prog1', 'Programação 1', 4, []);
        d1 = new Disciplina('prog1', 'Programação 1', 4, []);
        d2 = new Disciplina('prog1', 'Programação 1', 4, []);
        assert.notEqual(d0, d1);
        assert.notEqual(d0, d2);
        assert.notEqual(d1, d2);
    });

    it('deve reter os dados de inicialização', function(){
        assert.equal('prog1', d0.id());
        assert.equal('Programação 1', d0.get_nome());
        assert.equal(4, d0.creditos);
        assert.deepEqual([], d0.pre_requisitos);
    });

    it('deve permitir atualização de nome', function(){
        d0.set_nome('Programação de Computadores I')
        assert.equal('prog1', d0.id());
        assert.equal('Programação de Computadores I', d0.get_nome());
        assert.deepEqual([], d0.pre_requisitos);
    });

    it('não deve permitir atualização de id via set_id', function(){
        assert.throws(function () {
            d0.set_id('outro')
        }, TypeError);
        assert.equal('prog1', d0.id());
    });

});

describe('factory Turma', function() {
    let d0
    let t0

    before(async () => {
        d0 = new Disciplina('prog1', 'Programação 1', 4, [])
        t0 = new Turma(d0, 1)
    })

    it('deve ter método para definir professor', function() {
        assert.equal(null, t0.get_professor())
        t0.define_professor("Dalton")
        assert.equal("Dalton", t0.get_professor())
    })

    it('deve ter método para matricular estudantes', function() {
        assert.deepEqual([], t0.get_estudantes())
        e0 = new Estudante('111111111', 'Arthur', 'arthur@email.com', '11122233344', 'foto.com')
        t0.matricula_estudante(e0)
        assert.deepEqual([], t0.get_estudantes())
        
        t0.set_status("planejada")
        t0.matricula_estudante(e0)
        assert.deepEqual([e0], t0.get_estudantes())

        t0.matricula_estudante(e0)
        assert.equal(1, t0.get_estudantes().length)

        t0.set_status("concluída")
        e1 = new Estudante('222222222', 'João', 'joao@email.com', '22233344455', 'foto1.com')
        t0.matricula_estudante(e1)
        assert.notDeepEqual([e0, e1], t0.get_estudantes())

        t0.desmatricula_estudante(e0)
        assert.equal(0, t0.get_estudantes().length)
    })

    it('deve ter método para desmatricular estudantes', function() {
        e0 = new Estudante('111111111', 'Arthur', 'arthur@email.com', '11122233344', 'foto.com')
        t0.set_status("planejada")
        t0.matricula_estudante(e0)
        assert.deepEqual([e0], t0.get_estudantes())
        t0.desmatricula_estudante(e0)
        assert.deepEqual([], t0.get_estudantes())
    })

    it('deve ter uma property curjo valor pode ser planejada, ativa ou concluída', function() {
        t0.set_status("planejada")
        assert.equal("planejada", t0.get_status())

        t0.set_status("ativa")
        assert.equal("ativa", t0.get_status())

        t0.set_status("concluída")
        assert.equal("concluída", t0.get_status())

        t0.set_status("outro")
        assert.equal("concluída", t0.get_status())
    }) 
})

describe('factory Estudante', function() {
    let e0

    before(async () => {
        e0 = new Estudante('111111111', 'Arthur', 'arthur@email.com', '11122233344', 'foto.com')
    })

    it('deve conter uma matrícula que não pode ser mudada', function() {
        assert.equal("111111111", e0.get_matricula())
        assert.throws(function () {
            e0.set_matricula('444444444')
        }, TypeError)
    })

    it('deve conter um nome que pode ser alterado', function() {
        assert.equal('Arthur', e0.nome)
        e0.nome = 'Arthur Guedes'
        assert.equal('Arthur Guedes', e0.nome)
    })

    it('deve ter um método matricula(t)', function() {
        assert.deepEqual([], e0.get_turmas())
        
        d0 = new Disciplina('prog1', 'Programação 1', 4, [])
        t0 = new Turma(d0, 1)
        e0.matricula(t0)
        assert.deepEqual([t0], e0.get_turmas())
    })

    it('deve ter um método turmas(semestre)', function() {
        assert.deepEqual([t0], e0.turmas(1))

        d1 = new Disciplina('psoft', 'Projeto de Software', 4, [d0])
        t1 = new Turma(d1, 4)
        e0.matricula(t1)
        assert.deepEqual([t1], e0.turmas(4))
    })
})

describe('factory Professor', function() {
    let p0
    
    before(async () => {
        p0 = new Professor('333333333', 'Professor', 'prof@email.com', '44455566677', 'foto.com')
    })

    it('deve conter uma matrícula que não pode ser mudada', function() {
        assert.equal("333333333", p0.get_matricula())
        assert.throws(function () {
            p0.set_matricula('444444444')
        }, TypeError)
    })

    it('deve conter um nome que pode ser alterado', function() {
        assert.equal('Professor', p0.nome)
        p0.nome = 'Dalton'
        assert.equal('Dalton', p0.nome)
    })

    it('deve ter um método aloca_turma(t)', function() {
        assert.deepEqual([], p0.get_turmas())
        
        d0 = new Disciplina('prog1', 'Programação 1', 4, [])
        t0 = new Turma(d0, 1)
        p0.aloca_turma(t0)
        assert.deepEqual([t0], p0.get_turmas())
    })

    it('deve ter um método turmas(semestre)', function() {
        assert.deepEqual([t0], p0.turmas(1))

        d1 = new Disciplina('psoft', 'Projeto de Software', 4, [d0])
        t1 = new Turma(d1, 4)
        p0.aloca_turma(t1)
        assert.deepEqual([t1], p0.turmas(4))
    })
})