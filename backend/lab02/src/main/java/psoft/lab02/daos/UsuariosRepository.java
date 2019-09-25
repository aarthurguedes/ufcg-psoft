package psoft.lab02.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import psoft.lab02.entities.Usuario;

import java.io.Serializable;
import java.util.Optional;

@Repository
public interface UsuariosRepository<T, ID extends Serializable> extends JpaRepository<Usuario, String> {

    Optional<Usuario> findByEmail(String email);
}
