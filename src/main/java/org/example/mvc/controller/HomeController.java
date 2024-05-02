package org.example.mvc.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.mvc.dto.UserDto;
import org.example.mvc.model.Category;
import org.example.mvc.model.Product;
import org.example.mvc.model.User;
import org.example.mvc.service.CategoryService;
import org.example.mvc.service.ProductService;
import org.example.mvc.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;

    @GetMapping("/index")
    public String home(Model model,
                       @RequestParam(defaultValue = "",name = "sort") String sort,
                       @RequestParam(defaultValue = "0" ,name = "category-id") Long categoryId,
                       @RequestParam(defaultValue = "") String keyword,
                       @RequestParam(defaultValue = "1",name = "page") int page,
                       HttpServletRequest request
    ) {

        PageRequest pageRequest = PageRequest.of(page - 1, 25,Sort.by("createdAt").descending());

        if (sort.equals("new")){
            pageRequest = PageRequest.of(page - 1, 25,Sort.by("createdAt").descending());
        }
        if (sort.equals("old")){
            pageRequest = PageRequest.of(page - 1, 25,Sort.by("createdAt").ascending());
        }
        if (sort.equals("low-high")){
            pageRequest = PageRequest.of(page - 1, 25,Sort.by("price").ascending());
        }
        if (sort.equals("high-low")){
            pageRequest = PageRequest.of(page - 1, 25,Sort.by("price").descending());
        }

        List<Category> categories = categoryService.getAllCategory();
        Page<Product> productPage = productService.getAllProductPage(keyword,pageRequest,categoryId);
        List<Product> products = productPage.getContent();
        int totalPage = productPage.getTotalPages();

        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);

        // Lấy thông tin về người dùng từ Authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Kiểm tra xem người dùng đã đăng nhập hay chưa
        if (authentication != null && authentication.isAuthenticated()) {
            // Lấy tên người dùng đã đăng nhập
            String username = authentication.getName();

            User user = userService.findByUsername(username);

            // Tạo hoặc lấy ra session từ request
            HttpSession session = request.getSession();

            // Lưu thông tin người dùng vào session
            session.setAttribute("user", user);
        }

        model.addAttribute("products", products);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("sort", sort);
        model.addAttribute("keyword", keyword);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categories", categories);
        model.addAttribute("productPage", productPage);
        model.addAttribute("page", page);
        return "home";
    }
}
