package org.example.mvc.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.mvc.dao.ProductDAO;
import org.example.mvc.dto.CreateProductRequest;
import org.example.mvc.model.Product;
import org.example.mvc.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductDAO productDAO;
    @Override
    public List<String> addProduct(CreateProductRequest createProductRequest) throws IOException {
        MultipartFile file = createProductRequest.getImage();
        List<String> erros = new ArrayList<>();
        // kiem tra kich thuoc file
        if (file.getSize() > 10*1024*1024){
            erros.add("Kích thước file quá lớn");
        }
        // Kiem tra dinh dang co phai la file anh ko
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")){
            erros.add("Định dạng file không đúng");
        }
        //Luu file va cap nhat thumbnail trong DTO
        String fileName = storeFile(file);

        Product product = new Product();
        product.setName(createProductRequest.getName());
        product.setDescription(createProductRequest.getDescription());
        product.setPrice(createProductRequest.getPrice());
        product.setImage(fileName);
        product.setCategory(createProductRequest.getCategory());
        product.setBrand(createProductRequest.getBrand());
        product.setSale(createProductRequest.getSale());
        product.setOrigin(createProductRequest.getOrigin());
        product.setColor(createProductRequest.getColor());
        productDAO.save(product);
        return erros;
    }

    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        // Thêm UUID vào trước tên file để dảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString()+"_"+filename;

        // Đường dẫn đến thư mục mà ban muốn lưu file
        java.nio.file.Path uploadDir = Paths.get("/static/uploads");

        //Kiểm tra và tạo thư mục ếu không tồn tại
        if (!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        // Đường dẫn đầy đủ đến file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(),uniqueFilename);

        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(),destination, StandardCopyOption.REPLACE_EXISTING);
        return destination.toString();
    }

}
