package com.canhhocit.learn01.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canhhocit.learn01.DTO.Request.SinhvienCreationRequest;
import com.canhhocit.learn01.DTO.Request.SinhvienUpdateRequest;
import com.canhhocit.learn01.Entities.Sinhvien;
import com.canhhocit.learn01.Services.SinhvienService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/sinhvien")
public class SinhvienController {
    @Autowired
    private SinhvienService svService;

    @PostMapping
    Sinhvien createSV(@RequestBody @Valid SinhvienCreationRequest request){
        return svService.createSinhvien(request);
    }
    @GetMapping
    List<Sinhvien> getAllSinhvien(){
        return svService.getAll();
    }
    @GetMapping("/{msv}")
    Sinhvien getSV(@PathVariable String msv){
        return svService.getSVbyMSv(msv);
    }
    @PutMapping("/{msv}")
     Sinhvien updateSV(@RequestBody @Valid SinhvienUpdateRequest uRequest, @PathVariable String msv){
        return svService.updateSV(uRequest,msv);
    }
    @DeleteMapping("/{msv}")
    String deleteSV(@PathVariable String msv){
        svService.deleteSV(msv);
        return "deleted !";
    }
}
