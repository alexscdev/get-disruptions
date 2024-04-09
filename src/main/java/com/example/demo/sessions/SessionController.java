package com.example.demo.sessions;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class SessionController {
	
	
	@GetMapping("/save-layer")
    public String saveData(@RequestParam(name = "Capa") String data, HttpSession session, HttpServletRequest request) {
        // Guarda el nombre de la capa seleccionada en la sesión HTTP
        session.setAttribute("selectedLayer", data);
        System.out.println(data);
     // Obtiene la URL actual
        String url = request.getRequestURL().toString();

        // Devuelve la misma URL
        return "redirect:" + url;
    }
    
    @GetMapping("/show")
    public String showData(HttpSession session, Model model) {
        String data = (String) session.getAttribute("selectedLayer");
        model.addAttribute("data", data);
        
        return "show-data";
    }
}
