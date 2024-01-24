package com.example.kepcoweb.service

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class CronService (
    @Value("\${custome.container-prefix}")
    val containerPrefix: String
){

    private val processBuilder = ProcessBuilder()
    fun executeCron1(){
        executeCommand("$containerPrefix-crawler-1")
    }

    fun executeCron2(){
        executeCommand("$containerPrefix-update-recent-table-1")
    }

    internal fun executeCommand(container: String) {
        processBuilder.command("docker", "start", container)

        val process: Process = processBuilder.start()
        val reader = BufferedReader(InputStreamReader(process.inputStream))
        val line = reader.readText();
        println(line)

        val exitVal = process.waitFor()
        if (exitVal == 0) {
            println("cron을 성공적으로 실행했습니다.")
        } else {
            println("cron을 성공적으로 실행하지 못했습니다.")
            throw RuntimeException("Please check the value of \"container-prefix\" (now: $containerPrefix)")
        }
    }
}