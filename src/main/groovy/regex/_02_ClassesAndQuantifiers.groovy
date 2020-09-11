package regex

import groovy.util.logging.Log4j2

import java.time.Duration
import java.time.Instant

@Log4j2
class _02_ClassesAndQuantifiers {
    static void main(def args) {
        classes()
        3.times { println() }
        predefinedClasses()
        3.times { println() }
        quantifiers()
    }

    static void classes() {
        def vowelsPattern = ~/[aeijouy]/    // read as "one character from the following set [a, e, j, o, u, y]
        def vowelsIgnoreCasePattern = ~/[aeijouyAEIJOUY]/   // means just the same as above, but more chars in set


        // find all vowels in text and their frequency
        def text = 'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut ' +
                'labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris ' +
                'nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit ' +
                'esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in' +
                ' culpa qui officia deserunt mollit anim id est laborum. Sed ut perspiciatis unde omnis iste natus ' +
                'error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo ' +
                'inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo. Nemo enim ipsam ' +
                'voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos ' +
                'qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor sit ' +
                'amet, consectetur, adipisci velit, sed quia non numquam eius modi tempora incidunt ut labore et ' +
                'dolore magnam aliquam quaerat voluptatem. Ut enim ad minima veniam, quis nostrum exercitationem ' +
                'ullam corporis suscipit laboriosam, nisi ut aliquid ex ea commodi consequatur? Quis autem vel eum ' +
                'iure reprehenderit qui in ea voluptate velit esse quam nihil molestiae consequatur, vel illum qui ' +
                'dolorem eum fugiat quo voluptas nulla pariatur?'
        def matcher = (text =~ vowelsIgnoreCasePattern)
        def vowelsFound = [:]
        matcher.each { String vowel ->
            vowel = vowel.toLowerCase()
            def num = vowelsFound[vowel] ?: 0
            vowelsFound[vowel] = ++num
        }
        def vowelFrequencies = vowelsFound.collectEntries { v, num ->
            [v, num / text.size()]
        }
        vowelFrequencies.each { v, f ->
            def pcnt = f * 100
            def num = vowelsFound[v]
            println "${v} : ${num}/${text.size()} : ${pcnt}%"
        }
    }

    static void quantifiers() {
        // "*" - block on the left repeated zero or more times; "a*" - symbol "a" repeated zero or more times

        def numberPattern = ~/\d+/

        // example:
        // "abcd*" - "a" followed by "b" followed by "c" followed by ("d" repeated zero or more times)
        // previous regex should be imagined as "a  b  c  (d*)" - quantifier applies only to the block on the left

        // "+" - repeated one or more times


        // example: find all simple verbs in past in text
        def simpleVerbsPattern = ~/\w+ed/
        // any word character repeated one or more times followed by "e" followed by "d"
        // "followed" - should match
        // "did" - won't match
        // "    ed" - won't match
        // "       fed" - match
        def text = '''
The old lady pulled her spectacles down and looked over them, about the room; then she put them up and looked out under them. She seldom or never looked through them for so small a thing as a boy; they were her state pair, the pride of her heart, and were built for "style," not service;-she could have seen through a pair of stove lids just as well. She looked perplexed for a moment, and then said, not fiercely, but still loud enough for the furniture to hear:
"Well, I lay if I get hold of you I'll-"
She did not finish, for by this time she was bending down and punching under the bed with the broom-and so she needed breath to punctuate the punches with. She resurrected nothing but the cat.
"I never did see the beat of that boy!"
'''
        def matcher = (text =~ simpleVerbsPattern)
        def verbs = matcher.collect()
        println "verbs found : ${verbs}"
    }

    static void predefinedClasses() {
        // reads as "letter repeating at least once followed by "-" followed by digit repeating at least once
        // followed by letter repeating zero or more times
        // F-16, Su-27, Su-27F
        def jetModelPattern = ~/\b[a-zA-Z]+?-\d+?[a-zA-Z]*?\b/

        def text = '''
Most people use the term 'jet aircraft' to denote gas turbine based airbreathing jet engines, but rockets and scramjets are both also propelled by jet propulsion.
Cruise missiles are single-use unmanned jet aircraft, powered predominately by ramjets or turbojets or sometimes turbofans, but they will often have a rocket propulsion system for initial propulsion.
The fastest airbreathing jet aircraft is the unmanned X-43 scramjet at around Mach 9–10.
The fastest manned (rocket) aircraft is the X-15 at Mach 6.85.
The Space Shuttle, while far faster than the X-4356745 or X-15, was not regarded as an aircraft during ascent as it was carried ballistically by rocket thrust, rather than the air. During re-entry it was classed (like a glider) as an unpowered aircraft. The first flight was in 1981.
The Bell 533 (1964), Lockheed XH-51 (1965), and Sikorsky S-69 (1977-1981) are examples of compound helicopter designs where jet exhaust added to forward thrust.[9] The Hiller YH-32 Hornet and Fairey Ultra-light Helicopter were among the many helicopters where the rotors were driven by tip jets.
Jet-powered wingsuits exist - powered by model aircraft jet engines - but of short duration and needing to be launched at height.

Of these, the Fighter-bomber, reconnaissance fighter and strike fighter classes are dual-role, possessing qualities of the fighter alongside some other battlefield role. Some fighter designs may be developed in variants performing other roles entirely, such as ground attack or unarmed reconnaissance. This may be for political or national security reasons, for advertising purposes, or other reasons.[2]
The Sopwith Camel and other "fighting scouts" of World War I performed a great deal of ground-attack work. In World War II, the USAAF and RAF often favored fighters over dedicated light bombers or dive bombers, and types such as the Republic P-47 Thunderbolt and Hawker Hurricane that were no longer competitive as aerial combat fighters were relegated to ground attack. Several aircraft, such as the F-111 and F-117, have received fighter designations though they had no fighter capability due to political or other reasons. The F-111B variant was originally intended for a fighter role with the U.S. Navy, but it was canceled. This blurring follows the use of fighters from their earliest days for "attack" or "strike" operations against ground targets by means of strafing or dropping small bombs and incendiaries. Versatile multirole fighter-bombers such as the McDonnell Douglas F/A-18 Hornet are a less expensive option than having a range of specialized aircraft types.
Some of the most expensive fighters such as the US Grumman F-14 Tomcat, McDonnell Douglas F-15 Eagle, Lockheed Martin F-22 Raptor and Russian Sukhoi Su-27 were employed as all-weather interceptors as well as air superiority fighter aircraft, while commonly developing air-to-ground roles late in their careers. An interceptor is generally an aircraft intended to target (or intercept) bombers and so often trades maneuverability for climb rate.[3]
As a part of military nomenclature, a letter is often assigned to various types of aircraft to indicate their use, along with a number to indicate the specific aircraft. The letters used to designate a fighter differ in various countries – in the English-speaking world, "F" is now used to indicate a fighter (e.g. Lockheed Martin F-35 Lightning II or Supermarine Spitfire F.22), though when the pursuit designation was used in the US, they were "P" types (e.g. Curtiss P-40 Warhawk). In Russia "I" was used (Polikarpov I-16), while the French continue to use "C" (Nieuport 17 C.1). 
''' * 5_000
        def start = Instant.now()
        def jetModels = (text =~ jetModelPattern).collect().unique()
        println "jet models found : ${jetModels}"
        println "time: ${Duration.between(start, Instant.now())}"
    }
}
