class Disciplina {
    constructor(id, nome, creditos, pre_requisitos) {
        this._id = id
        this._nome = nome
        this.creditos = creditos
        this.pre_requisitos = pre_requisitos
    }

    id() {
        return this._id
    }

    get_nome() {
        return this._nome
    }
    
    set_nome(novo_nome) {
        this._nome = novo_nome
    }
}

class Turma {
    constructor(disciplina, periodo) {
        this.disciplina = disciplina
        this.periodo = periodo
        this._professor = null
        this._estudantes = []
        this._status = ""
    }

    define_professor (professor) {
        this._professor = professor
    }

    matricula_estudante(estudante) {
        if (!this._estudantes.includes(estudante) && (this._status == "planejada" || this._status == "ativa")) {
            this._estudantes.push(estudante)
        }
    }

    desmatricula_estudante(estudante) {
        if (this._estudantes.includes(estudante)) {
            for (let i = 0; i < this._estudantes.length; i++) {
                if (this._estudantes[i] == estudante) {
                    this._estudantes.splice(i, 1)
                }
            }
        }
    }

    get_professor() {
        return this._professor
    }

    get_estudantes() {
        return this._estudantes
    }

    get_status() {
        return this._status
    }

    set_status(novo_status) {
        if (["planejada", "ativa", "concluída"].includes(novo_status)) {
            this._status = novo_status
        }
    }
}

function Pessoa(matricula, nome, email, cpf, url_foto) {
    let _matricula = matricula
    let _cpf = cpf
    let _turmas = []
    
    this.nome = nome
    this.email = email
    this.url_foto = url_foto

    this.turmas = semestre => _turmas.filter(turma => turma.periodo === semestre)
    this.get_cpf = () => _cpf
    this.get_matricula = () => _matricula
    this.get_turmas = () => _turmas

}

function Estudante(matricula, nome, email, cpf, url_foto) {
    Pessoa.call(this, matricula, nome, email, cpf, url_foto)
    this.matricula = turma => {
        if (!this.get_turmas().includes(turma)) {
            this.get_turmas().push(turma)
        }
    }
}

Estudante.prototype = new Pessoa()
Estudante.prototype.constructor = Estudante

function Professor(matricula, nome, email, cpf, url_foto) {
    Pessoa.call(this, matricula, nome, email, cpf, url_foto)
    this.aloca_turma = turma => {
        if (!this.get_turmas().includes(turma)) {
            this.get_turmas().push(turma)
        }
    }
}

Professor.prototype = new Pessoa()
Professor.prototype.constructor = Professor

module.exports = {Disciplina, Turma, Estudante, Professor}