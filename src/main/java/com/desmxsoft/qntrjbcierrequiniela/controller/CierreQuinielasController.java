package com.desmxsoft.qntrjbcierrequiniela.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CierreQuinielasController {
	
	@PostMapping
    public ResponseEntity<String> cerrarQuinielas() {
		System.out.println("CierreQuinielasController INICIO cerrarQuinielas v2610101905");
        
    	return new ResponseEntity<String>("Hola se ejecuto OK es la v2610101905", HttpStatus.OK);
    }

}
