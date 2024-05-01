package org.example.mvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.mvc.dao.CategoryDAO;
import org.example.mvc.dao.ProductDAO;
import org.example.mvc.dto.ProductDto;
import org.example.mvc.model.Product;
import org.example.mvc.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductDAO productDAO;
    private final CategoryDAO categoryDAO;
    @Override
    public void addProduct(ProductDto productDto){
        MultipartFile file = productDto.getImage();

        String fileName = storeFile(file);

        Product product = new Product();
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setImage(fileName);
        product.setCategory(categoryDAO.findById(productDto.getCategoryId()).orElseThrow());
        product.setBrand(productDto.getBrand());
        product.setSale(productDto.getSale());
        product.setOrigin(productDto.getOrigin());
        product.setColor(productDto.getColor());
        productDAO.save(product);
    }

    @Override
    public List<Product> getAllProduct() {
        return productDAO.findAll();
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productDAO.findById(id).orElseThrow();

        productDAO.delete(product);

        Path imagePath = Paths.get("src/main/resources/static/assets/img/"+product.getImage());

        try {
            Files.delete(imagePath);
        }catch (Exception e){
            System.out.println("Exception: " + e.getMessage());
        }
    }

    @Override
    public Page<Product> getAllProductPage(String keyword, PageRequest pageRequest, Long categoryId) {
        Page<Product> productsPage;
        productsPage = productDAO.searchProducts(categoryId, keyword, pageRequest);
        return productsPage;
    }



    private String storeFile(MultipartFile file) {
        String filename = file.getOriginalFilename();

        // Thêm UUID vào trước tên file để dảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString()+"_"+filename;


        try {
            String uploadDir = "src/main/resources/static/assets/img";
            // Đường dẫn đến thư mục mà ban muốn lưu file
            Path uploadPath = Paths.get(uploadDir);

            //Kiểm tra và tạo thư mục ếu không tồn tại
            if (!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            // Đường dẫn đầy đủ đến file
            java.nio.file.Path destination = Paths.get(uploadPath.toString(),uniqueFilename);


            try (InputStream inputStream = file.getInputStream()){
                Files.copy(inputStream, destination,StandardCopyOption.REPLACE_EXISTING);
            }

        }catch (Exception e){
            System.out.println("Exception: " + e.getMessage());
        }
        return uniqueFilename;
    }

    private final int PRODUCT_NAME_COLUMN = 0;
    private final int PRODUCT_DESCRIPTION_COLUMN = 1;
    private final int PRODUCT_PRICE_COLUMN = 2;
    private final int PRODUCT_SALE_COLUMN = 3;
    private final int PRODUCT_COLOR_COLUMN = 4;
    private final int PRODUCT_BRAND_COLUMN = 5;
    private final int PRODUCT_ORIGIN_COLUMN = 6;
    private final int PRODUCT_CATEGORY_ID_COLUMN = 7;


    @Override
    public void fakeProductWithExcel(MultipartFile file) throws IOException {
        List<Product> products = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();

        while (rowIterator.hasNext()){
            Row row = rowIterator.next();
            String name = row.getCell(PRODUCT_NAME_COLUMN).getStringCellValue();
            String description = row.getCell(PRODUCT_DESCRIPTION_COLUMN).getStringCellValue();
            Float price = (float) row.getCell(PRODUCT_PRICE_COLUMN).getNumericCellValue();
            Float sale = (float) row.getCell(PRODUCT_SALE_COLUMN).getNumericCellValue();
            String color = row.getCell(PRODUCT_COLOR_COLUMN).getStringCellValue();
            String brand = row.getCell(PRODUCT_BRAND_COLUMN).getStringCellValue();
            String origin = row.getCell(PRODUCT_ORIGIN_COLUMN).getStringCellValue();
            Long categoryId = (long) row.getCell(PRODUCT_CATEGORY_ID_COLUMN).getNumericCellValue();

            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setSale(sale);
            product.setColor(color);
            product.setBrand(brand);
            product.setOrigin(origin);
            product.setCategory(categoryDAO.findById(categoryId).orElseThrow());

            products.add(product);
        }
        productDAO.saveAll(products);
    }

    @Override
    public Product findById(Long id) {
        return productDAO.findById(id).orElseThrow();
    }
}
