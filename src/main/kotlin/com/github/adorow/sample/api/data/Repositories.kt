package com.github.adorow.sample.api.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(propagation = Propagation.MANDATORY)
interface ProductRepository : JpaRepository<ProductEntity, Long> {

    fun findAllByPriceGreaterThan(minPriceExclusive: Double): List<ProductEntity>

}