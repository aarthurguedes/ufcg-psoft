function disciplina(id, nome, creditos, pre_requisitos) {
    let _nome = nome
    
    return {
        creditos,
        pre_requisitos,
        id: () => id,
        get_nome: () => _nome,
        set_nome: novo_nome => _nome = novo_nome
    }
}

function turma(disciplina, periodo) {
    let _professor = null
    let _estudantes = []
    let _status = ""

    return {
        disciplina,
        periodo,
        define_professor: professor => _professor = professor,
        matricula_estudante: estudante => {
            if (!_estudantes.includes(estudante) && (_status == "planejada" || _status == "ativa")) {
                _estudantes.push(estudante)
            }
        },
        desmatricula_estudante: estudante => {
            if (_estudantes.includes(estudante)) {
                for (let i = 0; i < _estudantes.length; i++) {
                    if (_estudantes[i] == estudante) {
                        _estudantes.splice(i, 1)
                    }
                }
            }
        },
        get_professor: () => _professor,
        get_estudantes: () => _estudantes,
        get_status: () => _status,
        set_status: novo => {
            if (["planejada", "ativa", "concluída"].includes(novo)) {
                _status = novo
            }
        }
    }
}

function professor(matricula, nome, email, cpf, url_foto) {
    let _matricula = matricula
    let _cpf = cpf
    let _turmas = []

    return {
        nome,
        email,
        url_foto,
        get_cpf: () => _cpf,
        get_matricula: () => _matricula,
        get_turmas: () => _turmas,
        aloca_turma: t => {
            if (!_turmas.includes(t)) {
                _turmas.push(t)
            }
        },
        turmas: semestre => _turmas.filter(turma => turma.periodo == semestre)
    }
}

function estudante(matricula, nome, email, cpf, url_foto) {
    let _matricula = matricula
    let _cpf = cpf
    let _turmas = []

    return {
        nome,
        email,
        url_foto,
        matricula: t => {
            if (!_turmas.includes(t)) {
                _turmas.push(t)
            }
        },
        turmas: semestre => _turmas.filter(turma => turma.periodo == semestre),
        get_cpf: () => _cpf,
        get_matricula: () => _matricula,
        get_turmas: () => _turmas
    }
}

module.exports = {disciplina, turma, estudante, professor}