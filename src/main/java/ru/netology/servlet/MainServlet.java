package ru.netology.servlet;

import ru.netology.controller.PostController;
import ru.netology.repository.PostRepository;
import ru.netology.service.PostService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {
  private PostController controller;
  private static String PATH_WITH_DIGITS = "/api/posts/\\d+";
  private static String API_PATH = "/api/posts";

  @Override
  public void init() {
    final var repository = new PostRepository();
    final var service = new PostService(repository);
    controller = new PostController(service);
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    final var path = req.getRequestURI();
    final var method = req.getMethod();
    if (path.equals(API_PATH)) {
      controller.all(resp);
      return;
    }
    if (path.matches(PATH_WITH_DIGITS)) {
      final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
      controller.getById(id, resp);
      return;
    }
    super.doGet(req, resp);
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    if (req.getRequestURI().equals(API_PATH)) {
      controller.save(req.getReader(), resp);
    }
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    final var path = req.getRequestURI();
    if(path.matches(PATH_WITH_DIGITS)) {
      final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
      controller.removeById(id, resp);
      return;
    }
  }
}

