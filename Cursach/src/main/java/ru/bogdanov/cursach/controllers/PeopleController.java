package ru.bogdanov.cursach.controllers;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import org.apache.commons.io.IOUtils;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.bogdanov.cursach.dao.PeopleDAO;
import ru.bogdanov.cursach.models.Graph;
import ru.bogdanov.cursach.models.Pair;
import ru.bogdanov.cursach.models.Person;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Map;

import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
@Controller
@RequestMapping("/cursach")
public class PeopleController {
    @Autowired
    private Graph graph;
   // private final PeopleDAO peopleDAO;

    public PeopleController(PeopleDAO dao) {

    }

    @GetMapping("/auth")
    public String auth(HttpServletRequest req, HttpServletResponse res, Model model) throws IOException, ClientException, ApiException, ParseException, InterruptedException {
        if (req.getParameterMap().size() == 0)
            return "redirect:https://oauth.vk.com/authorize?client_id=7618754&response_type=code&display=page&redirect_uri=http://localhost:8080/0/cursach/auth";
        else{
            graph.loadData(req.getParameter("code"));
            graph.cutFriends();
            graph.createEdges();
         // peopleDAO.init(req.getParameter("code"));
         //   peopleDAO.loadData();
            ArrayList<Person> l = new ArrayList<>();
            for (Map.Entry<Integer, Person> pers : graph.getList().entrySet()){
                l.add(pers.getValue());
            }
            LinkedList<Pair> l2 = new LinkedList<>();
            for (Pair p : graph.getEdges())
                l2.add(p);
            model.addAttribute("graphList",l);
            model.addAttribute("graphEdges",l2);
            model.addAttribute("rootId",graph.getRootId());
            return "graphPage";
            }
    }
   /* @GetMapping("/images")
    public String img(){
        return "test";
    }
    @GetMapping("img/close.png")
    public byte[] testphoto() throws IOException {
        InputStream in = getClass()
                .getResourceAsStream("/close.png");
        return IOUtils.toByteArray(in);
    }*/
    @GetMapping("/adddata")
    public String newData(HttpServletRequest req,Model model) throws IOException, InterruptedException, ClientException, ApiException {
          ArrayList<Person> l = new ArrayList<>();
        String id = req.getParameter("id");
        graph.appendData(id);
        for (Map.Entry<Integer, Person> pers : graph.getAppendedList().entrySet()){
            l.add(pers.getValue());
        }
        graph.appendEdges();
        LinkedList<Pair> l2 = new LinkedList<>();
        for (Pair p : graph.getAppendedEdges())
            l2.add(p);


        model.addAttribute("appendedList",l);
        model.addAttribute("appendedEdges",l2);
      Integer s = 1;
        model.addAttribute("a",1);
        return "test";
    }

}
