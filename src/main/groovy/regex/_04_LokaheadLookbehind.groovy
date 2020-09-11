package regex

import org.junit.runners.Parameterized

class _04_LokaheadLookbehind {
    static void main(def args) {
        // all the look-xxxx are known as "lookaround" features and are basically "followed by block X" or "preceded
        // by block X" relationships but without including that "block X" in result match

        // useful when you are going to use the entire match result (i.e. "matcher.group(0)" or "matcher.group()" )

        // positive lookarounds work can be done with capturing groups, so they are not very useful. it's easier to use
        // capturing group most of the times

        // however, *negative* lookarounds are much more useful and allow to have "NOT followed by" or "NOT preceded by"
        // requisites, which is not possible to do with any other mechanism

        positiveLookahead()
        3.times { println() }
        negativeLookahead()
        3.times { println() }
        positiveLookbehind()
        3.times { println() }
        negativeLookbehind()
    }

    static void positiveLookahead() {
        // example: find all last words in text, i.e. words that are followed by end of sentence mark

        def lastWordPattern = ~/\w+(?=[.!?])/
        // alternative way - use capturing group to capture the word without point: ~/(\w+)\./

        def text = '''
Saturday morning was come, and all the summer world was bright and fresh, and brimming with life. There was a song in
 every heart; and if the heart was young the music issued at the lips. There was cheer in every face and a spring in 
 every step. The locust trees were in bloom and the fragrance of the blossoms filled the air. Cardiff Hill, beyond 
 the village and above it, was green with vegetation, and it lay just far enough away to seem a Delectable Land, 
 dreamy, reposeful and inviting. Tom Sawyer appeared on the sidewalk with a bucket of whitewash and a long-handled 
 brush. He surveyed the fence, and all gladness left him and a deep melancholy settled down upon his spirit. He had 
 been caught sneaking in late last evening, and now Aunt Polly was determined to punish him by turning his Saturday 
 into captivity at hard labor, whitewashing a fence. Thirty yards of board fence nine feet high. Sighing, he dipped 
 his brush and passed it along the topmost plank; repeated the operation; did it again; compared the insignificant 
 whitewashed streak with the far-reaching continent of the unwhitewashed fence, and sat down discouraged.Soon the 
 free boys would come tripping along on all sorts of delicious expeditions, and they would make a world of fun of him
  for having to work—the very thought of it burnt him like fire. At this dark and hopeless moment, an inspiration 
  burst upon him!
He took up his brush and went calmly to work. Ben Rogers hove in sight presently—the very boy, of all boys, whose 
ridicule he had been dreading. Ben walked with a hop-skip-and-jump—proof enough that his heart was light and his 
anticipations high. He was eating an apple, and giving a long, melodious whoop, at intervals, followed by a 
deep-toned ding-dong-dong, ding-dong-dong; he was impersonating a steamboat. As he drew near, he slackened speed, 
took the middle of the street, leaned far over to starboard and rounded to ponderously and with laborious pomp and 
circumstance—for he was personating the “Big Missouri,” and considered himself to be drawing nine feet of water. He 
was boat and captain and engine-bells combined, so he had to imagine himself standing on his own hurricane deck 
giving the orders and executing them.
'''

        def matcher = (text =~ lastWordPattern)
        def lastWords = matcher.collect()

        // for detailed step-by-step illustration
        matcher = (text =~ lastWordPattern)
        while (matcher.find()) {
            println "match found : ${matcher.group()}"
            // println "group 1 : ${matcher.group(1)}"   ----- throws IndexOutOfBoundsException, so lookahead part is
            // not captured
        }

        println "last words : ${lastWords}"
    }

    static void negativeLookahead() {
        // example: find class names that do NOT extend from other classes

        def notExtendsPattern = ~/class\s+(?<className>\w+)\s+(?!extends)/

        def text = '''
class Test {
}
class Test extends BaseTest {
}
class Hello {
}
'''
        def matcher = (text =~ notExtendsPattern)
        def classes = []
        while (matcher.find()) {
            classes << matcher.group('className')
        }
        println "not extending classes : ${classes}"
    }

    static void positiveLookbehind() {
        // same as lookahead, but positioned before the block we want to add predicate "preceded by block X"
        // the lookbehind construct itself is not captured

        // NOTE: due to "forward only" nature of regex mechanics, lookbehinds can not use unbounded quantifiers (+ or *)
    }

    static void negativeLookbehind() {
    }
}
