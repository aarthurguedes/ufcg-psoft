package psoft.lab02.servicos;

import org.springframework.stereotype.Service;
import psoft.lab02.daos.DisciplinasRepository;
import psoft.lab02.entidades.Disciplina;

@Service
public class DisciplinasService {

    private DisciplinasRepository<Disciplina, Long> disciplinasDAO;

    public DisciplinasService(DisciplinasRepository<Disciplina, Long> disciplinasDAO) {
        this.disciplinasDAO = disciplinasDAO;
    }

    


}
