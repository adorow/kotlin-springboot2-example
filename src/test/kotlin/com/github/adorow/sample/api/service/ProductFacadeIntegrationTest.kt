package com.github.adorow.sample.api.service

import com.github.adorow.sample.api.controller.NotFoundException
import com.github.adorow.sample.api.data.ProductEntity
import com.github.adorow.sample.api.data.ProductRepository
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.emptyIterable
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension


@ExtendWith(SpringExtension::class)
@SpringBootTest
@DisplayName("The product Facade")
internal class ProductFacadeIntegrationTest {

    private companion object Util {
        const val SAMPLE_ID: Long = 1
    }

    @Autowired
    private lateinit var repository: ProductRepository

    @Autowired
    private lateinit var facade: ProductFacade

    @Test
    @DisplayName("findAll is initially empty")
    fun `findAll is initially empty`() {
        assertThat(facade.findAll(), emptyIterable())
    }

    @Test
    @DisplayName("then findById fails when empty and id is missing")
    fun `findById fails when empty and id is missing`() {
        try {
            assertThrows<NotFoundException> { facade.findById(SAMPLE_ID) }
        } catch (ex: Exception) {
            println(ex)
        }
    }

    @Nested
    @DisplayName("when a new entry is added")
    inner class WhenNewEntryAdded {

        @BeforeEach
        fun `add one entry`() {
            this@ProductFacadeIntegrationTest.repository.save(ProductEntity(SAMPLE_ID, "foo", 1.0))
        }

        @Test
        @DisplayName("findAll returns one entry")
        fun `findAll returns one entry`() {
            assertThat(facade.findAll(), hasSize(1))
        }

        @Test
        @DisplayName("findById returns sample entity")
        fun `findById returns sample entity`() {
            assertThat(facade.findById(SAMPLE_ID), notNullValue())
        }

    }

}