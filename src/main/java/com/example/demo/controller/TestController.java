package com.example.demo.controller;

import com.example.demo.controller.dto.JoinRequest;
import com.example.demo.entity.TestEntity;
import com.example.demo.repository.TestRepository;
import com.example.demo.service.TestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;
    @GetMapping("/hello")
    public String getHello(){

        return "Hello World";
    }

    @PostMapping("/join")
    public String join(@RequestBody JoinRequest joinRequest){

        String result = testService.join(joinRequest);

        if("success".equalsIgnoreCase(result)){
            return "추가 완료";
        }else {
            return "fail";
        }
    }

    private final TestRepository testRepository;

    @GetMapping("/searchAll")
    @ResponseBody
    public List<TestEntity> searchAll(){
        return testRepository.findAll();
    }

}
