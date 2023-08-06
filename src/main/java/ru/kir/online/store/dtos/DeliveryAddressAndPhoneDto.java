package ru.kir.online.store.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DeliveryAddressAndPhoneDto {
    private String deliveryAddress;
    private String phone;

    public DeliveryAddressAndPhoneDto(String deliveryAddress, String phone) {
        this.deliveryAddress = deliveryAddress;
        this.phone = phone;
    }

}
