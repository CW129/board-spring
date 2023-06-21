package com.example.demo.controller;


import com.example.demo.controller.dto.FileDto;
import com.example.demo.service.FileService;
import com.example.demo.util.MD5Generator;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import com.example.demo.controller.dto.BoardDto;
import com.example.demo.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

@Controller
@Slf4j
public class BoardController {
    private BoardService boardService;
    private FileService fileService;

    public BoardController(BoardService boardService, FileService fileService) {

        this.boardService = boardService;
        this.fileService = fileService;
    }
    @GetMapping ("/")
    public String list(Model model) {
        List<BoardDto> boardDtoList = boardService.getBoardList();
        model.addAttribute("postList", boardDtoList);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("Header : " + request.getHeader("X-FORWARDED-FOR"));
        log.info("Remote IP : " + request.getRemoteAddr());

        return "board/list.html";
    }
    @GetMapping("/post")
    public String post() {
        return "board/post.html";
    }
    @PostMapping("/post")
    public String write(@RequestParam("file") MultipartFile files, BoardDto boardDto){
        try{
            String origFilename = files.getOriginalFilename();
            String filename = new MD5Generator(origFilename).toString();
            String savePath = System.getProperty("user.dir") + "/files";
            if(!new File(savePath).exists()){
                try{
                    new File(savePath).mkdir();
                }
                catch (Exception e){
                    e.getStackTrace();
                }
            }
            String filePath = savePath + "/" + filename;
            files.transferTo(new File(filePath));

            FileDto fileDto = new FileDto();
            fileDto.setOrigFilename(origFilename);
            fileDto.setFilename(filename);
            fileDto.setFilePath(filePath);

            Long fileId = fileService.saveFile(fileDto);
            boardDto.setFileId(fileId);
            boardService.savePost(boardDto);
        } catch (Exception e){
            e.printStackTrace();
        }
        return "redirect:/";
    }
    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        BoardDto boardDto = boardService.getPost(id);
        model.addAttribute("post", boardDto);
        return "board/detail.html";
    }
    @GetMapping("/post/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        BoardDto boardDto = boardService.getPost(id);
        model.addAttribute("post", boardDto);
        return "board/edit.html";
    }
    @PutMapping("/post/edit/{id}")
    public String update(BoardDto boardDto){
        boardService.savePost(boardDto);
        return "redirect:/";
    }

    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.deletePost(id);
        return "redirect:/";
    }
}
