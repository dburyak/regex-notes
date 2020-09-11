package regex

import groovy.util.logging.Log4j2

import java.util.regex.Matcher
import java.util.regex.Pattern

@Log4j2
class _01_CharSequence {
    static void main(def args) {
        def text = (('a'..'z').join('')) * 3
        log.info 'text : {}', text


        // *************************
        // "a" followed by "b" followed by "c"
        // java style regex processing
        Pattern patternAbc = Pattern.compile('abc')  // Pattern - stateless object
        Matcher matcher = patternAbc.matcher(text)         // Matcher - stateful and not-thread-safe
        def found = [:]
        while (matcher.find()) {
            found << [(matcher.start()): matcher.group()]
        }
        assert found == [0: 'abc', 26: 'abc', 52: 'abc']


        // *************************
        // "d" followed by "e"
        // groovy regex syntax sugar
        def patternDe = ~/de/
        matcher = (text =~ patternDe)
        found = matcher.collect { String group ->
            assert group instanceof String
            group
        }
        assert found == ['de', 'de', 'de']


        // *******************************
        // java full text match variant
        text = 'Jane Doe'
        Pattern patternJane = Pattern.compile('Jane Doe')
        matcher = patternJane.matcher(text)
        def isMatching = matcher.matches()
        assert isMatching instanceof Boolean
        assert isMatching

        // "John Doe" full text match
        // groovy version
        text = 'John Doe'
        def patternJohn = ~/John Doe/
        isMatching = (text ==~ patternJohn)
        assert isMatching instanceof Boolean
        assert isMatching


        // ************************
        // note about groovy syntax
        def pattern = ~/this is java.regex.Pattern/  // "~/sss/" shorthand to Pattern.compile("sss")
        assert pattern instanceof Pattern

        def str = /this is java.util.String/        // "/sss/" is just a String but more friendly to regex syntax
        assert str instanceof String

        matcher = (text =~ /this is Pattern/)       // "left =~ right"  shorthand to
        // "Pattern.compile(right).matcher(left)" if right instanceof String
        assert matcher instanceof Matcher
        matcher = (text =~ ~/regex str/)            // or "right.matcher(left)" if right is instanceof Pattern
        assert matcher instanceof Matcher

        isMatching = (text ==~ pattern)         // "left ===~ right"  shorthand to "pattern.matcher(text).matches()"
        assert isMatching instanceof Boolean

        // flags for regex patterns - in groovy we can add flags in the beginning of regex, f.e. "(?i)" means
        // Pattern.CASE_INSENSITIVE
        pattern = ~/(?i)(?<=(^|[.!?]{0,3}[\s\n]{1,5}))[^.!?]*(?<repeatingWord>\b\w+\b)[^.!?]*\b\k<repeatingWord>\b[^.!?]*(?=($|[.!?]))/
    }
}
