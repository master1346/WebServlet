package ru.netology.servlet;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.netology.controller.PostController;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MainServlet extends HttpServlet {

  private PostController controller ;
  private final static String PATH_WITH_DIGITS = "/api/posts/\\d+";
  private final static String API_PATH = "/api/posts";

  @Override
  public void init() {
      final var context = new AnnotationConfigApplicationContext("ru.netology");
      controller = (PostController) context.getBean("postController");
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    final var path = req.getRequestURI();
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
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    if (req.getRequestURI().equals(API_PATH)) {
      controller.save(req.getReader(), resp);
    }
    super.doPost(req,resp);
  }

  @Override
  protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    final var path = req.getRequestURI();
    if(path.matches(PATH_WITH_DIGITS)) {
      final var id = Long.parseLong(path.substring(path.lastIndexOf("/")));
      controller.removeById(id, resp);
      return;
    }
    super.doDelete(req, resp);
  }

}

