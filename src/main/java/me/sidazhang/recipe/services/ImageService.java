package me.sidazhang.recipe.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    void saveImageFile(Long recipeId, MultipartFile file) throws Exception;

    ResponseEntity<byte[]> renderImage(Long recipeId) throws Exception;
}
