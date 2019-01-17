package com.malek.apitest

import com.fasterxml.jackson.databind.ObjectMapper
import com.malek.apitest.app.*
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@SpringBootApplication
class ApitestApplication {
    @Bean
    fun dataBaseInit(productRepository: ProductRepository, categoriesRepository: CategoriesRepository) = CommandLineRunner {
        productRepository.deleteAll()
        categoriesRepository.deleteAll()
        val foods = categoriesRepository.save(Category(id = 0, image = "", name = "Foods"))
        val drinks = categoriesRepository.save(Category(id = 0, image = "", name = "drinks"))
        val bigBurger = Product(image = "", productId = 0, name = "big burger malek", description = "", price = 2.5f, available = true, category = foods)
        val bigBurger1 = Product(image = "", productId = 0, name = "big burger malek 1", description = "", price = 200.0f, available = true, category = foods)
        productRepository.save(bigBurger)
        productRepository.save(bigBurger1)

        val bigDrink = Product(image = "", productId = 0, name = "big drink", description = "", price = 300f, available = true, category = drinks)
        productRepository.save(bigDrink)
    }

}

fun main(args: Array<String>) {
    runApplication<ApitestApplication>(*args)
}

@Configuration
@EnableSwagger2
class SpringFoxConfig {
    @Bean
    fun apiDocket(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
    }


}
