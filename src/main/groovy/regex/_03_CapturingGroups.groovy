package regex

import groovy.util.logging.Log4j2

import java.util.regex.Pattern

@Log4j2
class _03_CapturingGroups {
    static void main(def args) {
        simpleCapturingGroups()
        3.times { println() }
    }

    static void simpleCapturingGroups() {
        // "\b\w-(\d)+\b" - capturing group is !surprisingly! a group of symbols that are captured so you can retrieve it
        // here we want to match words of format "test-123", ones that have huphen+number suffix
        // AND we want to know that number because we want to use it somehow, f.e. save it to database

        // everything that is between the "(" and ")" braces is captured

        // example:
        // we have voucher of format "abcz-1234-5678-9abc-defe", where first arbitrary number of letters are "series",
        // and the rest is the voucher code itself in guid format. we need to check if the format is OK, and extract
        // series and the code separately
//        def voucherPattern = ~/^([a-zA-Z]+)-([\da-fA-f\-]+)$/
        def voucherPattern = Pattern.compile('^(?<series>[a-z]+)-(?<code>[\\da-f\\-]+)$', Pattern.MULTILINE | Pattern.CASE_INSENSITIVE)

        // NOTE: "-" inside char classes (between "[" and "]") is interpreted differently, so if you just want it to
        // be treated as a simple symbol (not a control symbol), just put it at the end of the list or escape it by "\"

        def voucherStr = '''
AACQPS-447d698f-65b9-4e8b-80aa-6e61784bf435
BACQPS-d11399d1-b5aa-4ccd-ab9c-daf043c9bddf
CACQPS-82d00ea6-d243-418a-bb45-84259f1896f1
DACQPS-ecbe52ef-b2eb-4da5-84a5-c9fc759d03b7
'''
        def matcher = (voucherPattern.matcher(voucherStr))
        while (matcher.find()) {
            // matching groups are accessed by index, "0" - always refer to the "whole regex match"
            def allVoucher = matcher.group(0)

            // actual matching groups start from index "1"
            def series = matcher.group(1)

            def code = matcher.group(2)

            // ... or you can access it by capturing group name:
            series = matcher.group('series')
            code = matcher.group('code')

            println "found : ${series} : ${code}"
        }
    }
}
