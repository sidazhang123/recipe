package me.sidazhang.recipe.config;

import me.sidazhang.recipe.models.Recipe;
import me.sidazhang.recipe.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Flux;

import static org.mockito.Mockito.when;


public class RouteFuncTestTest {
    @Mock
    RecipeService recipeService;
    WebTestClient webTestClient;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        RouteFuncTest routeFuncTest = new RouteFuncTest();
        RouterFunction<?> routerFunction = routeFuncTest.routes(recipeService);
        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
    }

    @Test
    public void routes() throws Exception {
        when(recipeService.getRecipes()).thenReturn(Flux.just());
        webTestClient.get().uri("/api/recipes").accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk();
    }

    @Test
    public void routesWithData() throws Exception {
        when(recipeService.getRecipes()).thenReturn(Flux.just(new Recipe(), new Recipe()));
        webTestClient.get().uri("/api/recipes").accept(MediaType.APPLICATION_JSON)
                .exchange().expectStatus().isOk()
                .expectBodyList(Recipe.class);
    }
}