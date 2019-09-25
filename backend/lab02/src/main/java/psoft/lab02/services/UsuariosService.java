package psoft.lab02.services;

import org.springframework.stereotype.Service;
import psoft.lab02.daos.UsuariosRepository;
import psoft.lab02.entities.Usuario;

import javax.servlet.ServletException;
import java.util.Optional;

@Service
public class UsuariosService {

    private UsuariosRepository<Usuario, String> usuariosDAO;

    public UsuariosService(UsuariosRepository<Usuario, String> usuariosDAO) {
        this.usuariosDAO = usuariosDAO;
    }

    public Usuario adicionaUsuario(Usuario usuario) {
        return this.usuariosDAO.save(usuario);
    }

    public Optional<Usuario> getUsuario(String email) {
        return this.usuariosDAO.findById(email);
    }

    public Usuario removeUsuario(String email) throws ServletException {
        Optional<Usuario> usuario = usuariosDAO.findByEmail(email);

        if (!usuario.isPresent()) {
            throw new ServletException("Usuario nao encontrado!");
        }

        usuariosDAO.delete(usuario.get());
        return usuario.get();
    }
}
