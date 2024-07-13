package com.jithinj.spring_reactive_mongo_crud.utils;

import com.jithinj.spring_reactive_mongo_crud.dto.ProductDto;
import com.jithinj.spring_reactive_mongo_crud.entity.Product;
import org.springframework.beans.BeanUtils;

public class AppUtils {

    public static ProductDto entityToDto(Product product) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(product, productDto);
        return productDto;
    }

    public static Product dtoToEntity(ProductDto productDto) {
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        return product;
    }
}
