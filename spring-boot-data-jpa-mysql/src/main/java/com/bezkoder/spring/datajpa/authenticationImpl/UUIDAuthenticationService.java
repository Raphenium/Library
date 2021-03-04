package com.bezkoder.spring.datajpa.authenticationImpl;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import com.bezkoder.spring.datajpa.authentication.UserAuthenticationService;
import com.bezkoder.spring.datajpa.model.User;
import com.bezkoder.spring.datajpa.repository.UserCrudService;

import java.util.Optional;
import java.util.UUID;

import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor(access = PACKAGE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
final class UUIDAuthenticationService implements UserAuthenticationService {
  @NonNull
  UserCrudService users;

  @Override
  public Optional<String> login(final String username, final String password) {
    final String uuid = UUID.randomUUID().toString();
    final User user = User
      .builder()
      .id(uuid)
      .username(username)
      .password(password)
      .build();

    users.save(user);
    return Optional.of(uuid);
  }

  @Override
  public Optional<User> findByToken(final String token) {
    return users.find(token);
  }

  @Override
  public void logout(final User user) {

  }
}
