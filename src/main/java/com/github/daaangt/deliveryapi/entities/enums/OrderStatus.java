package com.github.daaangt.deliveryapi.entities.enums;

import com.github.daaangt.deliveryapi.entities.Order;

public enum OrderStatus {
    PENDING_PAYMENT(1),
    PROCESSING(2),
    PAID(3),
    SHIPPED(4),
    DELIVERED(5),
    CANCELED(6);

    private int code;

    private OrderStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static OrderStatus valueOf(int code) {
        for(OrderStatus value : OrderStatus.values()) {
            if(value.getCode() == code) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid OrderStatus code");
    }
}
