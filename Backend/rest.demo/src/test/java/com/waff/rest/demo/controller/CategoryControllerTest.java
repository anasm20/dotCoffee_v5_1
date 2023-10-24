package com.waff.rest.demo.controller;

import com.waff.rest.demo.dto.CategoryDto;
import com.waff.rest.demo.model.Category;
import com.waff.rest.demo.service.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    }

    @Test
    public void testGetCategories() throws Exception {
        List<Category> categories = List.of(new Category(), new Category());
        when(categoryService.getCategories()).thenReturn(categories);

        mockMvc.perform(get("/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(categories.size()));

        verify(categoryService, times(1)).getCategories();
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void testGetCategoryById() throws Exception {
        String categoryId = "1";
        Category category = new Category();
        category.setId(categoryId);

        when(categoryService.getCategoryById(categoryId)).thenReturn(Optional.of(category));

        mockMvc.perform(get("/category/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(categoryId));

        verify(categoryService, times(1)).getCategoryById(categoryId);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void testDeleteCategoryById() throws Exception {
        String categoryId = "1";
        when(categoryService.deleteCategoryById(categoryId)).thenReturn(true);

        mockMvc.perform(delete("/admin/category/{id}", categoryId))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).deleteCategoryById(categoryId);
        verifyNoMoreInteractions(categoryService);
    }

    @Test
    public void testDeleteCategories() throws Exception {
        mockMvc.perform(delete("/admin/category/"))
                .andExpect(status().isOk());

        verify(categoryService, times(1)).deleteCategories();
        verifyNoMoreInteractions(categoryService);
    }
}
