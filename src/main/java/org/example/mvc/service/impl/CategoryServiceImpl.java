package org.example.mvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.mvc.dao.CategoryDAO;
import org.example.mvc.model.Category;
import org.example.mvc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDAO categoryDAO;
    @Override
    public List<Category> getAllCategory() {
        return categoryDAO.findAll();
    }


}
