# About

This program is a simple java program that allows you to generate faux-english words, by using a text as input.
 
![word generator long nonsense](https://user-images.githubusercontent.com/72321241/150910035-f23d7393-b037-4251-8f64-69fa0b1f8647.png)

Sometimes the generated words are coincidentally words in english. Sometimes, they sound like they could be words. On occasion they are complete nonsense, however.

# Usage

To generate some words, you must first create a `WordGenerator` object using a text `File` as a parameter. The class will then automatically extract out all of the syllables present and create the probabilities for a word to be of a certain length based on the text. To generate a word, use the `nextWord()` method on your `WordGenerator` object, and it will return the word.

To use this code you must have a java compiler such as the JVM installed.

I made this code as a side project for my computer science class, and you are welcome to use it however you like for free.
