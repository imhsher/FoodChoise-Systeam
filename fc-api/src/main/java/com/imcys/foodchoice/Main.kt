package com.imcys.foodchoice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication


@SpringBootApplication
open class FoodChoiceApplication


fun main(args: Array<String>) {
    runApplication<FoodChoiceApplication>(*args)
}
