package com.semicolok.web.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mobile.device.site.SitePreference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.semicolok.web.dto.BoardDto;
import com.semicolok.web.entity.Board;
import com.semicolok.web.service.BoardService;

@Slf4j
@Controller
//@RestController
public class BoardController {
    @Autowired private BoardService boardService;
    @Autowired private ModelMapper modelMapper;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public String home(SitePreference sitePreference, Locale locale, Model model) {
        System.out.println("sitePreference : "+sitePreference);
        log.debug("Welcome home! The client locale is {}.", locale);
        return "home";
    }
    
    
    @RequestMapping(value = "/boards", produces={"application/json", "application/xml"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Map<String, Object> getBoards() {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("data", boardService.getBoards());
        return result;
    }
    
    @RequestMapping(value = "/boards/{boardId}", produces={"application/json", "application/xml"}, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public BoardDto findById(@PathVariable Long boardId) {
        return modelMapper.map(boardService.findById(boardId), BoardDto.class);
//        Map<String, Object> result = new HashMap<String, Object>();
//        result.put("data", boardService.getBoards());
//        return result;
    }
}
