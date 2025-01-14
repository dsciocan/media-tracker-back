package com.duroc.mediatracker.controller;

import com.duroc.mediatracker.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/mediatracker/shows")
public class ShowController {
    @Autowired
    ShowService showService;
}
