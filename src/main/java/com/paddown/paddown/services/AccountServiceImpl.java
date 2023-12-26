package com.paddown.paddown.services;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.paddown.paddown.ConfigProperties;
import com.paddown.paddown.data.Account;
import com.paddown.paddown.error.CustomDataViolationException;
import com.paddown.paddown.error.EntityNotFound;
import com.paddown.paddown.error.StorageException;
import com.paddown.paddown.respository.AccountRepo;
import com.paddown.paddown.utils.Base64EncodedUUID;

@Service
public class  AccountServiceImpl implements AccountService{

    private final  Path  rootPath;
    protected static final String  IMAGE_SUB_DIR =  "images";

    AccountRepo accountRepo;

    private PasswordEncoder passwordEncoder;
    private ConfigProperties properties;

    public AccountServiceImpl(AccountRepo accountRepo,  PasswordEncoder passwordEncoder,  ConfigProperties properties){
        this.accountRepo =  accountRepo;
        this.passwordEncoder =  passwordEncoder;
        this.properties =  properties;

        if(properties.mediaUrl().trim().length() == 0){
            throw new StorageException("File upload location can not be Empty.");
        }
        this.rootPath =  Paths.get(properties.mediaUrl(), IMAGE_SUB_DIR);
    }

    @Override
    public Account getAccountById(long id)  {
        Optional<Account> account = accountRepo.findById(id);
        if(account.isPresent()) return account.get();
        throw  new EntityNotFound(("" + id ),  Account.class);
    }

    @Override
    public Account getAccountByUsername(String username) {
        Optional<Account> account =  accountRepo.findByEmail(username);
        if(account.isPresent()) return account.get();
        throw  new EntityNotFound(username,  Account.class);

    }

    @Override
    public Account createAccount(Account user)  throws CustomDataViolationException {
        // user.setActive(false);
        if(accountRepo.findByEmail(user.getEmail()).orElse(null) != null){
            throw new CustomDataViolationException("Account with email " +  user.getEmail() +  " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return accountRepo.save(user);
    }
    @Override
    public Account updateAccount(Account user) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void saveProfileImage(MultipartFile file, String username) {
            Account account =  this.getAccountByUsername(username);
            if(file.isEmpty()){
                throw new StorageException("empty file");
            }

            try{
                
                if(!Files.exists(this.rootPath.toAbsolutePath())){
                        Files.createDirectory(this.rootPath.toAbsolutePath());
                }

                String name =  Base64EncodedUUID.getBase64EncodedUUID() +  file.getContentType();
                Path destination =  this.rootPath.toAbsolutePath().resolve(name);

                try(InputStream inputStream =  file.getInputStream()){
                    Files.copy( inputStream,destination, StandardCopyOption.REPLACE_EXISTING  );
                    account.setImage(name.toString());
                    accountRepo.save(account);
                }
            }catch(IOException e ){
                throw new StorageException(e.getMessage());
            }
        
    }

    @Override
    public Resource getProfileImage(String username) {
        Account  account =  this.getAccountByUsername(username);
        String filename =  account.getImage();
        Path imagePath =  Paths.get(properties.mediaUrl(), IMAGE_SUB_DIR, filename);
        try{
            Resource urlresource =  new UrlResource(imagePath.toAbsolutePath().toUri());
            if(urlresource.exists() ||  urlresource.isReadable()){
                return urlresource;
            }
            throw new EntityNotFound("could not process or find resource");
        }catch(MalformedURLException e){
            throw new EntityNotFound("could not process or find resource");
        }
    }
}