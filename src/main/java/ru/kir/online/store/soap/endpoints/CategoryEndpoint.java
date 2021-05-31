package ru.kir.online.store.soap.endpoints;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.kir.online.store.soap.generated_data.categories.GetCategoryByTitleRequest;
import ru.kir.online.store.soap.generated_data.categories.GetCategoryByTitleResponse;
import ru.kir.online.store.soap.services.CategoryServiceSoap;

@Endpoint
@RequiredArgsConstructor
public class CategoryEndpoint {
    private static final String NAMESPACE_URI = "http://www.kir.ru/spring/ws/categories";
    private final CategoryServiceSoap categoryServiceSoap;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getCategoryByTitleRequest")
    @ResponsePayload
    @Transactional
    public GetCategoryByTitleResponse getCategoryByTitle(@RequestPayload GetCategoryByTitleRequest request){
        GetCategoryByTitleResponse response = new GetCategoryByTitleResponse();
        response.setCategory(categoryServiceSoap.getCategoryByTitle(request.getTitle()));
        return response;
    }

}
