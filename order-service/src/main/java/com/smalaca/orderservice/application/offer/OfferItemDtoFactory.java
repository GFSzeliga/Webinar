package com.smalaca.orderservice.application.offer;

import com.smalaca.orderservice.clock.Clock;
import com.smalaca.orderservice.infrastructure.warehouse.rest.ItemDto;

class OfferItemDtoFactory {
    private final DiscountStrategy discountStrategy;

    private OfferItemDtoFactory(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    static OfferItemDtoFactory create(Clock clock) {
        return new OfferItemDtoFactory(new DiscountStrategy(clock));
    }

    OfferItemDto create(ItemDto item) {
        return new OfferItemDto(item.getId(), item.getName(), withDiscount(item));
    }

    private OfferItemPriceDto withDiscount(ItemDto itemDto) {
        return new OfferItemPriceDto(amount(itemDto), itemDto.getCurrency());
    }

    private double amount(ItemDto itemDto) {
        return discountStrategy.withDiscount(itemDto.getAmount());
    }
}
