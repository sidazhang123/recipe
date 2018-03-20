package me.sidazhang.recipe.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface ImageService {
    Mono<Void> saveImageFile(String recipeId, MultipartFile file) throws Exception;

    ResponseEntity<byte[]> renderImage(String recipeId) throws Exception;
}
