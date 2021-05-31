package ru.kir.online.store.soap.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.kir.online.store.soap.generated_data.products.GetAllProductsRequest;
import ru.kir.online.store.soap.generated_data.products.GetAllProductsResponse;
import ru.kir.online.store.soap.generated_data.products.GetProductByTitleRequest;
import ru.kir.online.store.soap.generated_data.products.GetProductByTitleResponse;
import ru.kir.online.store.soap.services.ProductServiceSoap;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {
    private static final String NAMESPACE_URI = "http://www.kir.ru/spring/ws/products";
    private final ProductServiceSoap productServiceSoap;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductByTitleRequest")
    @ResponsePayload
    public GetProductByTitleResponse getProductByTitle(@RequestPayload GetProductByTitleRequest request) {
        GetProductByTitleResponse response = new GetProductByTitleResponse();
        response.setProduct(productServiceSoap.getProductByTitle(request.getTitle()));
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductsRequest")
    @ResponsePayload
    public GetAllProductsResponse getAllProducts(@RequestPayload GetAllProductsRequest request) {
        GetAllProductsResponse response = new GetAllProductsResponse();
        productServiceSoap.getAllProducts().forEach(response.getProducts()::add);
        return response;
    }

}