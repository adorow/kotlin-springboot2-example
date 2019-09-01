package com.github.adorow.sample.api.data


import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class ProductEntity(
        @Id
        val id: Long?,
        val name: String,
        val price: Double
)