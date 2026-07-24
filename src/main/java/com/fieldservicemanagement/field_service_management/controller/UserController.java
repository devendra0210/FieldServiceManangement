package com.fieldservicemanagement.field_service_management.controller;

import com.fieldservicemanagement.field_service_management.base.BaseURL;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BaseURL.API + BaseURL.USER)
@RequiredArgsConstructor
public class UserController {


    @GetMapping(BaseURL.ME)
    public String getMe() {
        return "Success";
    }
}
