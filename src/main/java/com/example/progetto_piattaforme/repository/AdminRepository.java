package com.example.progetto_piattaforme.repository;

import com.example.progetto_piattaforme.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {

}
