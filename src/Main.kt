package encryptdecrypt

import java.io.File

var userText = ""
var numberKey = ""
var inputFile = ""
var outputFile = ""
var userChoice = "enc"
var encryptedText = ""
var decryptedText = ""
var algorithmString = "shift"
val smallLetters = CharArray(26) { 'a' + it }
val capLetters = CharArray(26) { 'A' + it }
val regexText = "[a-zA-Z]".toRegex()


fun main(args: Array<String>) {
    getValues(args)
    encryptDecrypt()
}

fun getValues(args: Array<String>) {
    for (index in args.indices) {
        if (args[index] == "-mode") userChoice = args[index + 1]
        if (args[index] == "-key") numberKey = args[index + 1]
        if (args[index] == "-data") userText = args[index + 1]
        if (args[index] == "-in") inputFile = args[index + 1]
        if (args[index] == "-out") outputFile = args[index + 1]
        if (args[index] == "-alg") algorithmString = args[index + 1]
    }
    if (userText.isEmpty()) readFile()
}

fun readFile() {
    val inputFileName = File(inputFile)
    if (inputFileName.exists()) userText += inputFileName.readText() else println("Error")
}

fun encryptDecrypt() {
    if (algorithmString == "shift") encryptDecryptWitShift() else {
        when (userChoice) {
            "enc" -> numberKeyValue(1)
            "dec" -> numberKeyValue(-1)
            else -> print("Error")
        }
    }
}

fun numberKeyValue(sign: Int) {
    for (c in userText) encryptedText += c + sign * numberKey.toInt()
    outputToPrintOrFile(encryptedText)
}

fun encryptDecryptWitShift() {
    when (userChoice) {
        "enc" -> encBody()
        "dec" -> decBody()
        else -> print("Error")
    }
}

fun encBody() {
    for (c in userText) encryptedText += if (c.toString().matches(regexText)) {
        if (c in smallLetters) {
            val positionOfC = smallLetters.indexOf(c) + numberKey.toInt()
            if (smallLetters.indexOf(c) + numberKey.toInt() <= 25) smallLetters[positionOfC] else {
                smallLetters[positionOfC - 26]
            }
        } else {
            val positionOfC = capLetters.indexOf(c) + numberKey.toInt()
            if (capLetters.indexOf(c) + numberKey.toInt() <= 25) capLetters[positionOfC]  else {
                capLetters[positionOfC - 26]
            }
        }
    } else c
    outputToPrintOrFile(encryptedText)
}

fun decBody() {
    for (c in userText) {
        decryptedText += if (c.toString().matches(regexText)) {
            if (c in smallLetters) {
                val positionOfC = smallLetters.indexOf(c) - numberKey.toInt()
                if (smallLetters.indexOf(c) - numberKey.toInt() < 0) smallLetters[26 + positionOfC] else {
                    smallLetters[positionOfC]
                }
            } else {
                val positionOfC = capLetters.indexOf(c) - numberKey.toInt()
                if (capLetters.indexOf(c) - numberKey.toInt() < 0) capLetters[26 + positionOfC] else {
                    capLetters[positionOfC]
                }
            }
        } else c
    }
    outputToPrintOrFile(decryptedText)
}

fun outputToPrintOrFile(text: String) {
    if (inputFile.isEmpty()) println(text) else writeFile(text)
}

fun writeFile(Text: String) {
    val outputFileName = File(outputFile)
    outputFileName.writeText(Text)
}