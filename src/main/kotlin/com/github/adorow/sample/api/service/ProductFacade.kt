package com.github.adorow.sample.api.service

import com.github.adorow.sample.api.controller.NotFoundException
import com.github.adorow.sample.api.data.ProductEntity
import com.github.adorow.sample.api.data.ProductRepository
import org.springframework.cache.annotation.Cacheable
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
@Transactional(readOnly = true)
class ProductFacade(
        private val repository: ProductRepository
) {

    @Cacheable("ProductFacade.findById")
    fun findById(id: Long): ProductEntity? =
            try {
                val res: ProductEntity? = repository.getOne(id)
                res ?: throw NotFoundException("Missing product with ID $id")
            } catch (ex: EntityNotFoundException) {
                throw NotFoundException(ex.message ?: "Entity noy found", ex)
            } catch (ex: JpaObjectRetrievalFailureException) {
                throw NotFoundException(ex.message ?: "Entity noy found", ex)
            }

    fun findAll(): List<ProductEntity> =
            repository.findAll()

    @Cacheable("ProductFacade.findAllByPriceHigherThan")
    fun findAllByPriceHigherThan(minimumPriceExclusive: Double): List<ProductEntity> =
            repository.findAllByPriceGreaterThan(minimumPriceExclusive)

    fun totalValue(): Double =
            repository.findAll()
                    .sumByDouble{ it.price }


}