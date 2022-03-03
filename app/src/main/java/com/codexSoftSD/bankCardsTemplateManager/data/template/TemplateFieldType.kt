package com.codexSoftSD.bankCardsTemplateManager.data.template

enum class TemplateFieldType {
    SOLID("\\w"),
    AMOUNT("\\d+"),
    CURRENCY("\\w{1,3}"),
    CARD("\\w{1,4}"),
    ACCOUNT("\\w{1,4}"),
    FROM("\\w+"),
    DATE,
    ON_ACCOUNT,
    ADDITIONAL_INFO;
    constructor(regex: String = "")
}