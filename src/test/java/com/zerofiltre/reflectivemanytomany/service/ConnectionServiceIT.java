package com.zerofiltre.reflectivemanytomany.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.zerofiltre.reflectivemanytomany.model.Connection;
import com.zerofiltre.reflectivemanytomany.model.User;
import com.zerofiltre.reflectivemanytomany.repository.ConnectionRepository;
import com.zerofiltre.reflectivemanytomany.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class ConnectionServiceIT {

  @Autowired
  ConnectionRepository connectionRepository;

  @Autowired
  UserRepository userRepository;

  Connection connection, anotherConnection;
  User paul, jean, marc;

  @Autowired
  ConnectionService connectionService;


  @BeforeAll
  void init() {
    paul = new User();
    paul.setFirstName("paul");
    paul.setLastName("PAUL");
    userRepository.save(paul);

    jean = new User();
    jean.setFirstName("jean");
    jean.setLastName("JEAN");
    userRepository.save(jean);

    marc = new User();
    marc.setFirstName("marc");
    marc.setLastName("MARC");
    userRepository.save(marc);

    //paul ==> jean
    connection = new Connection();
    connection.setCreatedOn(LocalDateTime.now());
    connection.setInitializer(paul);
    connection.setReceiver(jean);
    connectionRepository.save(connection);

    //marc ==> jean
    anotherConnection = new Connection();
    anotherConnection.setCreatedOn(LocalDateTime.now());
    anotherConnection.setInitializer(marc);
    anotherConnection.setReceiver(paul);
    connectionRepository.save(anotherConnection);

  }

  @Test
  @DisplayName("getConnections must return a list of users related to the considered user, but excluding him")
  void getConnections_returnsAListOfUsersRelatedToTheConsideredUser() {

    List<User> paulConnections = connectionService.getConnections(paul);
    assertThat(paulConnections).isNotNull();
    assertThat(paulConnections.size()).isEqualTo(2);

    //none of the 2 users are paul
    assertThat(
        paulConnections.stream().noneMatch(user ->
            paul.getId().equals(user.getId())
        )
    ).isTrue();

    //one the 2 connections is marc
    checkConnections(paulConnections, marc);

    //one the 2 connections is jean
    checkConnections(paulConnections, jean);
  }


  private void checkConnections(List<User> paulConnections, User checkedUser) {
    assertThat(
        paulConnections.stream().anyMatch(user ->
            checkedUser.getId().equals(user.getId()) &&
                checkedUser.getFirstName().equals(user.getFirstName()) &&
                checkedUser.getLastName().equals(user.getLastName())
        )
    ).isTrue();
  }

}
