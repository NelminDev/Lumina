package dev.nelmin.logger

// From https://gist.github.com/JBlond/2fea43a3049b38287e5e9cefc87b2124
object ANSI {
    // Regular Colors
    const val BLACK: String = "\u001B[0;30m"
    const val RED: String = "\u001B[0;31m"
    const val GREEN: String = "\u001B[0;32m"
    const val YELLOW: String = "\u001B[0;33m"
    const val BLUE: String = "\u001B[0;34m"
    const val PURPLE: String = "\u001B[0;35m"
    const val CYAN: String = "\u001B[0;36m"
    const val WHITE: String = "\u001B[0;37m"

    // Bold
    const val BOLD_BLACK: String = "\u001B[1;30m"
    const val BOLD_RED: String = "\u001B[1;31m"
    const val BOLD_GREEN: String = "\u001B[1;32m"
    const val BOLD_YELLOW: String = "\u001B[1;33m"
    const val BOLD_BLUE: String = "\u001B[1;34m"
    const val BOLD_PURPLE: String = "\u001B[1;35m"
    const val BOLD_CYAN: String = "\u001B[1;36m"
    const val BOLD_WHITE: String = "\u001B[1;37m"

    // Underline
    const val UNDERLINE_BLACK: String = "\u001B[4;30m"
    const val UNDERLINE_RED: String = "\u001B[4;31m"
    const val UNDERLINE_GREEN: String = "\u001B[4;32m"
    const val UNDERLINE_YELLOW: String = "\u001B[4;33m"
    const val UNDERLINE_BLUE: String = "\u001B[4;34m"
    const val UNDERLINE_PURPLE: String = "\u001B[4;35m"
    const val UNDERLINE_CYAN: String = "\u001B[4;36m"
    const val UNDERLINE_WHITE: String = "\u001B[4;37m"

    // Background
    const val BACKGROUND_BLACK: String = "\u001B[40m"
    const val BACKGROUND_RED: String = "\u001B[41m"
    const val BACKGROUND_GREEN: String = "\u001B[42m"
    const val BACKGROUND_YELLOW: String = "\u001B[43m"
    const val BACKGROUND_BLUE: String = "\u001B[44m"
    const val BACKGROUND_PURPLE: String = "\u001B[45m"
    const val BACKGROUND_CYAN: String = "\u001B[46m"
    const val BACKGROUND_WHITE: String = "\u001B[47m"

    // High Intensity
    const val HIGH_INTENSITY_BLACK: String = "\u001B[0;90m"
    const val HIGH_INTENSITY_RED: String = "\u001B[0;91m"
    const val HIGH_INTENSITY_GREEN: String = "\u001B[0;92m"
    const val HIGH_INTENSITY_YELLOW: String = "\u001B[0;93m"
    const val HIGH_INTENSITY_BLUE: String = "\u001B[0;94m"
    const val HIGH_INTENSITY_PURPLE: String = "\u001B[0;95m"
    const val HIGH_INTENSITY_CYAN: String = "\u001B[0;96m"
    const val HIGH_INTENSITY_WHITE: String = "\u001B[0;97m"

    // Bold High Intensity
    const val BOLD_HIGH_INTENSITY_BLACK: String = "\u001B[1;90m"
    const val BOLD_HIGH_INTENSITY_RED: String = "\u001B[1;91m"
    const val BOLD_HIGH_INTENSITY_GREEN: String = "\u001B[1;92m"
    const val BOLD_HIGH_INTENSITY_YELLOW: String = "\u001B[1;93m"
    const val BOLD_HIGH_INTENSITY_BLUE: String = "\u001B[1;94m"
    const val BOLD_HIGH_INTENSITY_PURPLE: String = "\u001B[1;95m"
    const val BOLD_HIGH_INTENSITY_CYAN: String = "\u001B[1;96m"
    const val BOLD_HIGH_INTENSITY_WHITE: String = "\u001B[1;97m"

    // High Intensity backgrounds
    const val BACKGROUND_HIGH_INTENSITY_BLACK: String = "\u001B[0;100m"
    const val BACKGROUND_HIGH_INTENSITY_RED: String = "\u001B[0;101m"
    const val BACKGROUND_HIGH_INTENSITY_GREEN: String = "\u001B[0;102m"
    const val BACKGROUND_HIGH_INTENSITY_YELLOW: String = "\u001B[0;103m"
    const val BACKGROUND_HIGH_INTENSITY_BLUE: String = "\u001B[0;104m"
    const val BACKGROUND_HIGH_INTENSITY_PURPLE: String = "\u001B[0;105m"
    const val BACKGROUND_HIGH_INTENSITY_CYAN: String = "\u001B[0;106m"
    const val BACKGROUND_HIGH_INTENSITY_WHITE: String = "\u001B[0;107m"

    // Reset
    const val RESET: String = "\u001B[0m"

    // Text Styles
    const val BOLD: String = "\u001B[1m"
    const val ITALIC: String = "\u001B[3m"
    const val UNDERLINE: String = "\u001B[4m"
    const val STRIKETHROUGH: String = "\u001B[9m"

    /**
     * Retrieves the ANSI color code corresponding to the given color character.
     *
     * @param colorChar The character representing a color.
     * @return The ANSI color code as a string if the character is valid, or null if the character is unsupported.
     */
    private fun getANSICode(colorChar: Char): String? = when (colorChar) {
        '0' -> BLACK
        '1' -> BLUE
        '2' -> GREEN
        '3' -> CYAN
        '4' -> RED
        '5' -> PURPLE
        '6' -> YELLOW
        '7' -> WHITE
        '8' -> HIGH_INTENSITY_BLACK
        '9' -> HIGH_INTENSITY_BLUE
        'a' -> HIGH_INTENSITY_GREEN
        'b' -> HIGH_INTENSITY_CYAN
        'c' -> HIGH_INTENSITY_RED
        'd' -> HIGH_INTENSITY_PURPLE
        'e' -> HIGH_INTENSITY_YELLOW
        'f' -> HIGH_INTENSITY_WHITE
        'g' -> YELLOW
        'r' -> RESET
        else -> null
    }

    /**
     * Translates a given string by converting specific characters into their corresponding
     * ANSI escape codes or other specified transformations. The method also processes escaped
     * characters, optimizing special cases.
     * Escape the translating by using \& instead of &
     *
     * @param text The input string to be processed.
     * @param char The character to be interpreted as a prefix for special codes or transformations. Defaults to '&'.
     * @return A new string with applied translation based on specified transformations or unmodified if no transformations apply.
     */
    fun translateToANSI(text: String, char: Char = '&'): String {
        if (char !in text && '\\' !in text) return text // Early exit optimization

        val result = StringBuilder(text.length + 32)
        var i = 0
        var lastPos = 0
        val length = text.length

        while (i < length) {
            // Handle escaped special character (e.g., `\&` → `&`)
            if (text[i] == '\\' && i + 1 < length && text[i + 1] == char) {
                if (lastPos < i) {
                    result.append(text, lastPos, i)
                }
                result.append(char)
                i += 2
                lastPos = i
                continue
            }

            if (text[i] == char && i + 1 < length) {
                val nextChar = text[i + 1]
                val colorChar = if (nextChar in 'A'..'Z') (nextChar.code + 32).toChar() else nextChar
                val ansiCode = getANSICode(colorChar)

                if (ansiCode != null) {
                    if (lastPos < i) {
                        result.append(text, lastPos, i)
                    }
                    result.append(ansiCode)
                    i += 2
                    lastPos = i
                    continue
                }
            }

            i++
        }

        if (lastPos < length) {
            result.append(text, lastPos, length)
        }

        return result.toString()
    }
}