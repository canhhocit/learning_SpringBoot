package com.canhhocit.learn00.Basic;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class BaseController {
    // get value from URI path  /api/users/2
    @GetMapping("/{userID}")
    public String getUserID(@PathVariable Long userID){
        return "ID: "+ userID;
    }
    //get value form query(/?key =abc)
    @GetMapping("/action")
    public String getQuery(@RequestParam String key){
        return "Key: " + key;
    }
    @GetMapping("/msg")
    public MessageDTO getMSG(){
        return new MessageDTO(999,"Đôi khi lựa chọn sai lại đưa đến đúng đích...");
    }
    @PostMapping("/msg")
    public MessageDTO createDTO(@RequestBody MessageDTO msgPayload){
        System.out.println("Received: " + msgPayload.getMsg());
        return msgPayload;
    }
}
