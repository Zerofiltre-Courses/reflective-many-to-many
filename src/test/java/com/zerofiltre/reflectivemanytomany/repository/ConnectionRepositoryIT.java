package com.zerofiltre.reflectivemanytomany.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.zerofiltre.reflectivemanytomany.model.Connection;
import com.zerofiltre.reflectivemanytomany.model.User;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@TestInstance(Lifecycle.PER_CLASS)
class ConnectionRepositoryIT {

  @Autowired
  ConnectionRepository connectionRepository;

  @Autowired
  UserRepository userRepository;
  private Connection connection, anotherConnection;
  private User paul, jean, marc;

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
  void savingAConnectionFromUsers_works() {
    assertThat(paul.getId()).isNotNull();
    assertThat(jean.getId()).isNotNull();
    assertThat(connection.getId()).isNotNull();
  }

  @Test
  void findByInitializerIdOrRetrieverId_returnsAllTheUserConnections() {

    //given another connection was successfully created with paul as the receiver
    assertThat(anotherConnection.getId()).isNotNull();

    //when we get paul connections
    List<Connection> paulConnections = connectionRepository.findByInitializerIdOrReceiverId(paul.getId(), paul.getId());


    //then we return 2 connections, in which paul is either the initializer or the receiver
    assertThat(paulConnections).isNotNull();
    assertThat(paulConnections.size()).isEqualTo(2);
    assertThat(paulConnections.stream().allMatch(aConnection ->
        paul.getId().equals(aConnection.getInitializer().getId()) ||
            paul.getId().equals(aConnection.getReceiver().getId())
    )).isTrue();

  }

}
