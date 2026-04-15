package com.anastasiia.itkacademy.repository;

import java.util.Optional;

import com.anastasiia.itkacademy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByLogin(String login);
}