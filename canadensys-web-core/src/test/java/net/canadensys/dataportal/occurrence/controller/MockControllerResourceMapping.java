package net.canadensys.dataportal.occurrence.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MockControllerResourceMapping {
	
	@RequestMapping(value="/test")
	public void handleTest(){
		
	}

}
