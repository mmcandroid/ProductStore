package com.malek.apitest.app


import org.springframework.data.repository.CrudRepository

interface ProductRepository : CrudRepository<Product, Long> {
    fun findByCategoryId(id: Long): Iterable<Product>
}

interface CategoriesRepository : CrudRepository<Category, Long>
interface CommandRepository : CrudRepository<Command, Long>
interface OrderRepository : CrudRepository<Orders, Long> {
    fun findByCommandId(id: Long): Iterable<Orders>
}
