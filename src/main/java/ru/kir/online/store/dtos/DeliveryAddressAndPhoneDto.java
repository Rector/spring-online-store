package ru.kir.online.store.dtos;

import lombok.Data;

@Data
public class DeliveryAddressAndPhoneDto {
    private String deliveryAddress;
    private String phone;

    public DeliveryAddressAndPhoneDto(String deliveryAddress, String phone) {
        this.deliveryAddress = deliveryAddress;
        this.phone = phone;
    }

}
