package com.account.manager.am;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class AccountManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountManagerApplication.class, args);
    }

}
