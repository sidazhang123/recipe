package me.sidazhang.recipe.services;

import lombok.extern.slf4j.Slf4j;
import me.sidazhang.recipe.exceptions.ImageFormatException;
import me.sidazhang.recipe.exceptions.NotFoundException;
import me.sidazhang.recipe.models.Recipe;
import me.sidazhang.recipe.repositories.reactive.RecipeReactiveRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService {
    private final RecipeReactiveRepository recipeReactiveRepository;

    public ImageServiceImpl(RecipeReactiveRepository recipeReactiveRepository) {
        this.recipeReactiveRepository = recipeReactiveRepository;
    }

    @Override
    public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {
        Optional<Recipe> optionalRecipe = recipeReactiveRepository.findById(recipeId).blockOptional();
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            try {
                byte[] img = file.getBytes();
                Byte[] ByteObj = new Byte[img.length];
                Arrays.setAll(ByteObj, n -> img[n]);
                recipe.setImage(ByteObj);
                recipeReactiveRepository.save(recipe).block();
            } catch (IOException e) {
                log.warn("Failed to convert Image to byte array");
                throw new ImageFormatException("Failed to convert Image to byte array");
            }

        } else {
            throw new NotFoundException("Recipe with ID value " + recipeId);
        }
        return Mono.empty();

    }

    @Override
    @ResponseBody
    public ResponseEntity<byte[]> renderImage(String recipeId) {
        Optional<Recipe> optionalRecipe = recipeReactiveRepository.findById(recipeId).blockOptional();
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            Byte[] bytes = recipe.getImage();
            if (bytes == null) {
                return ResponseEntity.notFound().build();
            }
            byte[] bytes1 = new byte[bytes.length];
            int i = 0;
            for (Byte b : recipe.getImage())
                bytes1[i++] = b;
            return ResponseEntity.ok().contentLength(bytes.length).body(bytes1);
        } else {
            throw new NotFoundException("Recipe with ID value " + recipeId);
        }
    }
}
