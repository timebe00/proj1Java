package com.example.demo.controller;

import lombok.extern.java.Log;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Iterator;

@Log
@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "http://localhost:8080", allowedHeaders = "*")
public class FileController {

    @PostMapping("/file")
    public ResponseEntity<String> sinup(HttpServletRequest request) throws Exception {
        log.info("Controller File Sin Up");

        MultipartHttpServletRequest mult = null;
        String original = "";
        if(request instanceof MultipartHttpServletRequest)       //  request가 MultipartHttpServletRequest 로형변환 가능한가
        {
            log.info("yes");
            mult = (MultipartHttpServletRequest)request;

            Iterator<String> iterator = mult.getFileNames();
            MultipartFile fil = mult.getFile(iterator.next());

            original = fil.getOriginalFilename();

            String fileTeg = original;

//            String filePath = "D:\\Git\\Bitcamp\\Project\\soproj1\\pythonwork";
            String filePath = "/home/bitai/proj/Project/pro2/sopython";
            File folder = new File(filePath);

            if(!folder.exists()){
                folder.mkdirs();
            }

            File file = new File(filePath + File.separator + fileTeg);
            fil.transferTo(file);

            return new ResponseEntity<String>(file.getAbsolutePath(),HttpStatus.OK);
        }

        return new ResponseEntity<String>(original,HttpStatus.OK);
    }

    @PostMapping("/remove")
    public void remove(@RequestBody String path) throws Exception
    {
        log.info("path : " + path);
        if(path != null) {
            File f = new File(path);
            if(f.exists()){
                f.delete();
            }
        }
    }

    @GetMapping("/name")
    public void load(HttpServletRequest request) throws Exception
    {
        log.info("Controller File Load");
        if(request instanceof MultipartHttpServletRequest)       //  request가 MultipartHttpServletRequest 로형변환 가능한가
        {
            log.info("Log yes");
        }
    }

    @PostMapping("/pythonwork")
    public ResponseEntity<String> pythonwork(@RequestBody String nick) throws Exception
    {
        log.info("Controller Python Work : " + nick);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
