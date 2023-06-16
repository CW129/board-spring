package com.example.demo.repository;

import com.example.demo.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface TestRepository extends JpaRepository<TestEntity, String> {
}
