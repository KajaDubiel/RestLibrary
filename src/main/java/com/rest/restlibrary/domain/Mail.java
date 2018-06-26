package com.rest.restlibrary.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
    private String mailTo;
    private String subject;
    private String message;
    private String toCC;

    public Mail(String mailTo, String subject, String message){
        this.mailTo = mailTo;
        this.subject = subject;
        this.message = message;
    }
}
