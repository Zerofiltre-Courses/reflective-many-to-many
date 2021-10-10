package com.zerofiltre.reflectivemanytomany.model;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import lombok.Data;

@Data
@Entity
public class Connection {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "initializer_id")
  User initializer;

  @ManyToOne
  @JoinColumn(name = "receiver_id")
  User receiver;

  LocalDateTime createdOn;

}
