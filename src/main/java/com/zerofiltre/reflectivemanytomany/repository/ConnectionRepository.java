package com.zerofiltre.reflectivemanytomany.repository;


import com.zerofiltre.reflectivemanytomany.model.Connection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {

  List<Connection> findByInitializerIdOrReceiverId(Long initializerId,Long receiverId);
}
