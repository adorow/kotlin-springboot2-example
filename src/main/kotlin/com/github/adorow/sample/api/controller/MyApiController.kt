package com.github.adorow.sample.api.controller

import com.github.adorow.sample.api.data.ProductEntity
import com.github.adorow.sample.api.service.ProductFacade
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/my-api")
class MyApiController(
        private val facade: ProductFacade
) {


    @ApiOperation(value = "Finds a product by its ID")
    @ApiResponses(value = [ApiResponse(code = 200, message = "return a product object", response = ProductEntity::class)])
    @GetMapping(value = ["/products/{id}"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getProductById(@PathVariable("id") id: Long): ProductEntity? =
            facade.findById(id)


    @ApiOperation(value = "Gets a list of products, with the possibility of filtering ")
    @ApiResponses(value = [ApiResponse(code = 200, message = "returns a list of products", response = ProductEntity::class)])
    @GetMapping(value = ["/products"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun getProductList(
            @RequestParam(name = "minPrice", required = false) minPrice: Double?
    ): List<ProductEntity> =
            when (minPrice) {
                null -> facade.findAll()
                else -> facade.findAllByPriceHigherThan(minPrice)
            }


    @ApiOperation(value = "Get the inbound cost for the specified fs ids")
    @ApiResponses(value = [ApiResponse(code = 200, message = "return a list of inbound cost objects")])
    @GetMapping(value = ["/products/totalPrice"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun totalPrice(): Double =
            facade.totalValue()


}