package com.github.adorow.sample.api.service

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.willReturn
import com.nhaarman.mockito_kotlin.willThrow
import com.github.adorow.sample.api.controller.NotFoundException
import com.github.adorow.sample.api.data.ProductEntity
import com.github.adorow.sample.api.data.ProductRepository
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import javax.persistence.EntityNotFoundException


@ExtendWith(MockitoExtension::class)
internal class ProductFacadeTest {

    private companion object Util {
        const val SAMPLE_ID: Long = 1
    }

    @Mock
    private lateinit var repository: ProductRepository

    @InjectMocks
    private lateinit var facade: ProductFacade

    @Test
    fun `on EntityNotFoundException the error is adapted`() {
        given { repository.getOne(SAMPLE_ID) } willThrow { EntityNotFoundException() }

        assertThrows<NotFoundException> { facade.findById(SAMPLE_ID) }
    }

    @Test
    fun `on found by Id then the entity is returned`() {
        val result = ProductEntity(1, "foo", 1.0)

        given { repository.getOne(SAMPLE_ID) } willReturn { result }

        assertThat(facade.findById(SAMPLE_ID), equalTo(result))
    }

    @Test
    fun `findAlll returns what repository provides`() {
        val result = listOf(ProductEntity(1, "foo", 1.0))

        given { repository.findAll() } willReturn { result }

        assertThat(facade.findAll(), equalTo(result))
    }

}