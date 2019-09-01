package com.github.adorow.sample.api.controller

import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.willReturn
import com.github.adorow.sample.api.data.ProductEntity
import com.github.adorow.sample.api.service.ProductFacade
import org.hamcrest.Matchers.equalTo
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
internal class MyApiControllerTest {

    companion object {
        const val ID = 1L
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var mockFacade: ProductFacade

    @Test
    fun `given a request by a product ID that entity is returned`() {
        given { mockFacade.findById(eq(ID)) } willReturn { ProductEntity(ID, "foo", 1.0) }

        mockMvc.perform(get("/v1/my-api/products/$ID"))
                .andExpect(status().isOk)
                .andExpect(jsonPath("$.id", equalTo(ID.toInt())))
    }

    @Test
    fun `given a request for all the data, return that full list`() {
        given { mockFacade.findAll() } willReturn { listOf(ProductEntity(1, "foo", 1.0), ProductEntity(2, "bar", 2.0))  }

        mockMvc.perform(get("/v1/my-api/products"))
                .andExpect(status().isOk)
                .andExpect(jsonPath<Collection<*>>("$", hasSize(2)))
                .andExpect(jsonPath<Int>("$[0].id", equalTo(1)))
                .andExpect(jsonPath<String>("$[0].name", equalTo("foo")))
                .andExpect(jsonPath<Double>("$[0].price", equalTo(1.0)))
                .andExpect(jsonPath<Int>("$[1].id", equalTo(2)))
                .andExpect(jsonPath<String>("$[1].name", equalTo("bar")))
                .andExpect(jsonPath<Double>("$[1].price", equalTo(2.0)))
    }

}

