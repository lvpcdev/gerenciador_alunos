package br.com.lvpcdev.gerenciador.service;

import br.com.lvpcdev.gerenciador.dto.UsuarioRequestDTO;
import br.com.lvpcdev.gerenciador.dto.UsuarioResponseDTO;
import br.com.lvpcdev.gerenciador.model.Usuario;
import br.com.lvpcdev.gerenciador.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UsuarioResponseDTO salvarUsuario(UsuarioRequestDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setLogin(dto.login());
        usuario.setNome(dto.nome());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        return toDTO(usuarioRepository.save(usuario));
    }

    public UsuarioResponseDTO atualizarUsuario(Long id, UsuarioRequestDTO dto) {

        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario não encontrado para a edição."));

        usuarioExistente.setNome(dto.nome());
        usuarioExistente.setLogin(dto.login());
        usuarioExistente.setSenha(passwordEncoder.encode(dto.senha()));

        return toDTO(usuarioRepository.save(usuarioExistente));
    }

    public List<UsuarioResponseDTO> listarTodos() {
        return usuarioRepository.findAll().stream().map(this::toDTO).toList();
    }


    public void deletarUsuario(Long id) {

        Usuario usuarioExistente = usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuario não encontrado para a edição."));

        usuarioRepository.delete(usuarioExistente);
    }

    private UsuarioResponseDTO toDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getLogin(),
                usuario.getNome()
        );
    }
}
