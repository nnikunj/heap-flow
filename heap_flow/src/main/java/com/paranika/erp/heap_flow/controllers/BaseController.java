package com.paranika.erp.heap_flow.controllers;

 

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 

@RestController
@RequestMapping("/api/v1")
public class BaseController {
	@RequestMapping("/status")
	public String getItems() {
		return "Up and Running.";
	}

}
