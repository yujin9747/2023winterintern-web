package com.example.kepcoweb.service

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import org.springframework.stereotype.Service

@Service
class CronService{

    private val processBuilder = ProcessBuilder()
    fun executeCron1(){
        executeCommand("2023winterintern-crawler-1")
    }

    fun executeCron2(){
        executeCommand("2023winterintern-update-recent-table-1")
    }

    internal fun executeCommand(container: String) {
        processBuilder.command("docker", "start", container)

        try {

            val process: Process = processBuilder.start()
            val reader = BufferedReader(InputStreamReader(process.inputStream))
            val line = reader.readText();
            println(line)

            val exitVal = process.waitFor()
            if (exitVal == 0) {
                println("Success!")
            } else {
                println("Something abnormal happened.")
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}