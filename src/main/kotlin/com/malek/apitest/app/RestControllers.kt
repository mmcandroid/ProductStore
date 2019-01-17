package com.malek.apitest.app

import jdk.nashorn.internal.runtime.regexp.joni.Config.log
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException
import java.util.*

@RestController
@RequestMapping("/api/products")
class ProductController(val repository: ProductRepository, val categoriesRepository: CategoriesRepository) {

    val logger = LoggerFactory.getLogger(ProductController::class.java)
    @GetMapping("/")
    fun findAll() = repository.findAll()

    @GetMapping("/{id}/")
    fun findOne(@PathVariable id: Long) = repository.findById(id)
            .orElseThrow {
                ElementNotFound(id)
            }

    @PostMapping("/add/")
    fun addNewProduct(@RequestBody product: Product) {
        repository.save(product)
    }

}


@RestController
@RequestMapping("/api/categories")
class CategoriesController(val categoriesRepository: CategoriesRepository, val productRepository: ProductRepository) {
    @GetMapping("/")
    fun findAll() = categoriesRepository.findAll()
            .map {
                val responseCategory = ResponseCategory(name = it.name, image = it.image.toString(), product = ArrayList())
                responseCategory.product.addAll(productRepository.findByCategoryId(it.id))
                return@map responseCategory
            }

    @GetMapping("/{id}")
    fun findOne(@PathVariable id: Long) {
        categoriesRepository.findById(id).orElseThrow {
            ElementNotFound(id)
        }

    }

    @GetMapping("/{id}/products/")
    fun findProductsByCategory(@PathVariable id: Long) =
            productRepository.findByCategoryId(id)

    @PostMapping("/{id}/products/")
    fun addProductToCategory(@PathVariable id: Long, @RequestBody product: Product) {
        categoriesRepository.findById(id)
                .map { cat ->
                    product.category = cat
                    productRepository.save(product)
                }
    }


}


@RestController
@RequestMapping("/api/commands")
class CommandsControllers(val commandRepository: CommandRepository, val productRepository: ProductRepository, val orderRepository: OrderRepository) {
    @GetMapping("/")
    fun findAll() = commandRepository.findAll()

    @GetMapping("/{id}/orders/")
    fun findOne(@PathVariable id: Long) = orderRepository.findByCommandId(id)


    @PostMapping("/")
    fun addCommand(@RequestBody requestPostCommand: RequestPostCommand) {
        val command = commandRepository.save(Command(0, requestPostCommand.price, requestPostCommand.timeStamp))
        requestPostCommand.ordersRequests.map {
            val order = Orders(
                    product = productRepository.findById(it.idProduct).get(),
                    quantity = it.quantity,
                    command = command,
                    id = 0
            )
            orderRepository.save(order)
        }
    }
}

data class ResponseCategory(val name: String, val image: String, val product: ArrayList<Product>)
data class OrdersRequest(val idProduct: Long, val quantity: Int)
data class RequestPostCommand(val ordersRequests: List<OrdersRequest>, val price: Float, val timeStamp: Long)

