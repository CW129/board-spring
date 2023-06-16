package com.example.demo.service;

import com.example.demo.controller.dto.JoinRequest;
import com.example.demo.entity.TestEntity;
import com.example.demo.repository.TestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService{
    private final TestRepository testRepository;
    @Override
    public String join(JoinRequest joinRequest) {
        TestEntity testEntity = TestEntity.builder()
                .id(joinRequest.getId())
                .username(joinRequest.getUsername())
                .age(joinRequest.getAge())
                .build();
        testRepository.save(testEntity);

        return "success";
    }

//    public String searchAll(JoinRequest joinRequest){
//        TestEntity testEntity = TestEntity.builder()
//                .id(joinRequest.getId())
//                .build();
//
//        return "test";
//    }
}
