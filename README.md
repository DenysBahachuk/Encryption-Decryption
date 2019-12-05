# Encryption-Decryption
This project is the part of the Hyperskill course of project-based learning. 
Hyperskill is a part of JetBrains Academy's learning experience.

Coding Language: Java.
# Introduction
In this project, is shown how to encrypt and decrypt messages and texts 
using simple algorithms. It should be noted that such algorithms are not suitable 
for industrial use because they can easily be cracked, but these algorithms 
demonstrate some general ideas about encryption.
# Description
The program works with command-line arguments instead of the standard input.
The program reads data from -data or from a file written in the -in argument.
The program parses next arguments: -mode, -key and -data. The first argument 
should determine the program’s mode (enc - encryption, dec - decryption). 
The second argument is an integer key to modify the message, and the third 
argument is a text or ciphertext within quotes to encrypt or decrypt.
The program parses also two additional arguments -in and -out to specify 
the full name of a file to read data and to write the result.
If there is no -mode, the program works in enc mode.
If there is no -key, the program considers that key = 0.
If there is no -data, and there is no -in the program assumes that the data is an empty string.
If there is no -out argument, the program prints data to the standard output.
If there are both -data and -in arguments, the program prefers -data over -in.
If there is a non-standard situation (an input file does not exist or an argument doesn’t have a value), 
the program displays a clear message about the problem and stops successfully.
There are added two algorithms to encode/decode data. The first one is shifting algorithm 
(it shifts each letter by the specified number according to its order in the alphabet in circle). 
The second one is based on Unicode table.
When starting the program, the necessary algorithm is specified by an argument (-alg).
