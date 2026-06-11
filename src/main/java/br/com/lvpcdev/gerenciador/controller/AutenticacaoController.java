package br.com.lvpcdev.gerenciador.controller;


import br.com.lvpcdev.gerenciador.dto.LoginRequestDTO;
import br.com.lvpcdev.gerenciador.dto.LoginResponseDTO;
import br.com.lvpcdev.gerenciador.model.Usuario;
import br.com.lvpcdev.gerenciador.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AutenticacaoController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(dto.login(), dto.senha());
        Authentication authentication = authenticationManager.authenticate(authToken);
        Usuario usuario = (Usuario) authentication.getPrincipal();
        String token = tokenService.gerarToken(usuario);
        return ResponseEntity.ok(new LoginResponseDTO(token, usuario.getNome()));
    }}
