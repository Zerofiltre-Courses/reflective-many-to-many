package com.zerofiltre.reflectivemanytomany.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName;
  private String lastName;

  @OneToMany(mappedBy = "initializer")
  Set<Connection> initializedConnections;

  @OneToMany(mappedBy = "receiver")
  Set<Connection> receivedConnections;

}
