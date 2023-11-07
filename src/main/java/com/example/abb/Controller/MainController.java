package com.example.abb.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class MainController {
  List<Article> articles = new ArrayList<>(
      Arrays.asList(
          new Article("제목", "내용"),
          new Article("제목", "내용")
      )
  );
  @GetMapping("/saveSessionAge/{name}/{value}")
  @ResponseBody
  public String saveSessionAge(@PathVariable String name,@PathVariable String value, HttpServletRequest req){
    HttpSession session = req.getSession();
    session.setAttribute(name, value);

    return "세션변수 %s 의값은 %s로 설정되었습니다.".formatted(name, value);
  }

  @GetMapping("/getSessionAge/{name}")
  @ResponseBody
  public String getSessionAge(@PathVariable String name, HttpSession session){
    String value = (String) session.getAttribute(name);
    return "세션변수 %s 의 값은 %s입니다.".formatted(name,value);
  }


  @GetMapping("/addarticle")
  @ResponseBody
  public String addarticle(String title, String body) {
    Article article = new Article;
    articles.add(article);

    return "%d번 게시물이 생성되었습니다.".formatted(article.getId());
  }

  @GetMapping("/article/{id}")
  @ResponseBody
  public Article article(@PathVariable int id) {

    Article article= articles
        .stream()
        .filter(a -> a.getId() == id)
        .findFirst()
        .orElse(null);

    return article;
  }

  @GetMapping("/modifyarticle/{id]")
  @ResponseBody
  public String modifyarticle(@PathVariable int id, String title, String body) {
    Article article= articles
        .stream()
        .filter(a -> a.getId() == id)
        .findFirst()
        .orElse(null);

    if(article == null){
      return "%d번 게시물은 존재하지 않습니다.".formatted(id);
    }
    article.setTitle(title);
    article.setBody(body);

    return "%d번 게시물이 수정되었습니다.".formatted(article.getId());
  }

  @GetMapping("/deletearticle/{id]")
  @ResponseBody
  public String deletearticle(@PathVariable int id) {
    Article article= articles
        .stream()
        .filter(a -> a.getId() == id)
        .findFirst()
        .orElse(null);

    if(article == null){
      return "%d번 게시물은 존재하지 않습니다.".formatted(id);
    }
    articles.remove(article);
    return "%d번 게시물이 삭제되었습니다.".formatted(article.getId());
  }


  @AllArgsConstructor
  @Getter
  @Setter
  private class Article {
    private static int lastId = 0;
    private int id;
    private String title;
    private String body;

    public Article(String title, String body) {
      this(++lastId, title, body);
    }
  }
}
