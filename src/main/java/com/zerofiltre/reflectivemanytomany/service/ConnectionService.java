package com.zerofiltre.reflectivemanytomany.service;

import com.zerofiltre.reflectivemanytomany.model.Connection;
import com.zerofiltre.reflectivemanytomany.model.User;
import com.zerofiltre.reflectivemanytomany.repository.ConnectionRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ConnectionService {

  private ConnectionRepository connectionRepository;

  public ConnectionService(ConnectionRepository connectionRepository) {
    this.connectionRepository = connectionRepository;
  }

  public List<User> getConnections(User user) {
    List<User> connections = new ArrayList<>();
    List<Connection> connectionEntities = connectionRepository
        .findByInitializerIdOrReceiverId(user.getId(), user.getId());

    connectionEntities.forEach(connection -> {
      User initializer = connection.getInitializer();
      User receiver = connection.getReceiver();
      if (!user.getId().equals(initializer.getId())) {
        connections.add(initializer);
      }
      if (!user.getId().equals(receiver.getId())) {
        connections.add(receiver);
      }
    });
    return connections;
  }
}
