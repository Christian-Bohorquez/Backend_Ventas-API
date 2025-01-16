package com.sistema.ventas.app.infrastructure.data.repositories;

import com.sistema.ventas.app.domain.accounts.models.Credential;
import com.sistema.ventas.app.domain.accounts.ports.out.IAccountRepositoryPort;
import com.sistema.ventas.app.infrastructure.data.entities.UserEntity;
import com.sistema.ventas.app.infrastructure.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class JapAccountRepositoryAdapter implements IAccountRepositoryPort {

    private final JapAccountRepository _repository;
    private final PasswordEncoder _passwordEncoder;
    private final JwtTokenProvider _jwtTokenProvider;

    public JapAccountRepositoryAdapter(
        JapAccountRepository repository,
        PasswordEncoder passwordEncoder,
        JwtTokenProvider jwtTokenProvider )
    {
        this._repository = repository;
        this._passwordEncoder = passwordEncoder;
        this._jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String Authenticate(Credential credential) {
        Optional<UserEntity> userOptional = _repository.findByUsernameAndIsActiveTrue(credential.getUsername());

        if (userOptional.isEmpty()) {
            return ""; // Usuario no encontrado
        }

        UserEntity user = userOptional.get();
        if (!_passwordEncoder.matches(credential.getPassword(), user.getPassword())) {
            return ""; // Contraseña incorrecta
        }

        String fullName = user.getPerson().getFirstName() + " " + user.getPerson().getLastName();
        return _jwtTokenProvider.generateToken(user.getId(), fullName, user.getRole().getName());
    }

    @Override
    public boolean existsByUsername(String username) {
        return _repository.existsByUsernameAndIsActiveTrue(username);
    }
}
