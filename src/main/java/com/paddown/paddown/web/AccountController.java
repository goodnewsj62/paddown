package com.paddown.paddown.web;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.paddown.paddown.data.Account;
import com.paddown.paddown.services.AccountService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;


@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    AccountService accountService;

    @PostMapping
    public ResponseEntity<Account> createAccount(@Valid @RequestBody Account account){
        Account createdAccount =  accountService.createAccount(account);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
    }
    @GetMapping
    public ResponseEntity<HttpStatus> get(){
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    // update password

    @PostMapping("/{username}/upload/profile")
    public ResponseEntity<HttpStatus> uploadProfileImage(@PathVariable(name = "username") String username, @RequestParam("file")  MultipartFile file){
        accountService.saveProfileImage(file, username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
    @GetMapping("/{username}/profile")
    public ResponseEntity<Resource> fetchProfileImage(@PathVariable("username") String username){
        Resource resource =  accountService.getProfileImage(username);

        if (resource == null)
			return ResponseEntity.notFound().build();
        
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,  "attachment; filename=\"" + resource.getFilename() + "\"").body(resource);
    }
}
