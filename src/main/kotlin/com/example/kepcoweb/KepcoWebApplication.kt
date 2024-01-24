package com.example.kepcoweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.web.servlet.ServletComponentScan

@SpringBootApplication
class KepcoWebApplication

fun main(args: Array<String>) {
    runApplication<KepcoWebApplication>(*args)
}
