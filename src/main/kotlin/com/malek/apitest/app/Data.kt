package com.malek.apitest.app

import com.fasterxml.jackson.annotation.JsonIdentityReference
import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.util.*
import javax.persistence.*

@Entity
data class Product(
        @Id @GeneratedValue val productId: Long,
        val name: String,
        val image: String?,
        val price: Float,
        val description: String?,
        val available: Boolean,
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "category_id", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JsonIgnore
        var category: Category?
)

data class User(val id: Int, val name: String, val command: ArrayList<Command>)
@Entity
data class Command(@Id @GeneratedValue val id: Long,
                   var price: Float,
                   val date: Long)

@Entity
data class Orders(
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "product_id", nullable = false)
        val product: Product,
        val quantity: Int,
        @Id @GeneratedValue val id: Int,
        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "command_id", nullable = false)
        @OnDelete(action = OnDeleteAction.CASCADE)
        @JsonIgnore
        val command: Command)


@Entity
data class Category(val name: String,
                    val image: String?,
                    @Id
                    @GeneratedValue
                    val id: Long)

data class Catalogue(val categories: ArrayList<Category>)