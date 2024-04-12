package com.example.version1.users;

import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class UserSeeder implements CommandLineRunner {
  private static final Logger LOGGER = LoggerFactory.getLogger(UserSeeder.class);
  private static final Faker faker = new Faker(Locale.FRANCE);
  private final UserService userService;
  private final UserRepository userRepository;

  /**
   * For every role, create 2 users. (Runs automatically in every application start or update)
   *
   * @param args incoming main method arguments
   */
  @Override
  public void run(String... args) {
    if (userRepository.count() != 0) {
      LOGGER.info("Users are already seeded.");
      return;
    } /*do not seed if there are users*/
    LOGGER.info("Seeding users.");
    Arrays.stream(Role.values())
        .forEach(
            /*for each role*/
            role ->
                IntStream.range(0, 2) /*loop two times*/
                    .forEach(
                        (i) ->
                            userService.register(
                                /* register user*/
                                User.builder()
                                    .email(faker.internet().safeEmailAddress())
                                    .fullName(faker.name().fullName())
                                    .password("password")
                                    .roles(Set.of(role))
                                    .build())));
  }
}
