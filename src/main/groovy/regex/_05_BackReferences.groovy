package regex

import java.util.regex.Pattern

class _05_BackReferences {
    static void main(def args) {
        backReferences()
        3.times { println() }
        dingDing()
    }

    static void backReferences() {
        // expresses "followed by exactly what was captured by some particular capturing group"

        // example: find sentences with repeating words

        def pattern = ~/(?i)(?<=(^|[.!?]{0,3}[\s\n]{1,10}))[^.!?]*(?<repeatingWord>\b\w+\b)[^.!?]*\b\k<repeatingWord>\b[^.!?]*(?=($|[.!?]))/
//        def pattern = Pattern.compile('(?<=(^|[.!?]{0,3}[\\s\\n]{1,5}))[^.!?]*(?<repeatingWord>\\b\\w+\\b)[^.!?]*\\b\\k<repeatingWord>\\b[^.!?]*(?=($|[.!?]))', Pattern.CASE_INSENSITIVE)

        def text = '''\
Saturday morning was come, and all the summer world was bright and fresh, and brimming with life. There was a song in
 every heart; and if the heart was young the music issued at the lips. There was cheer in Every face and a spring in 
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
        def matcher = (text =~ pattern)
        while (matcher.find()) {
            println "repeating sentence found:"
            println "      sentence : ${matcher.group()}"
            println()
            println "      repeating word : ${matcher.group('repeatingWord')}"
            3.times { println() }
        }
    }

    static void dingDing() {
        // example: we need to match words like "ding-ding", "dong-dong"
        def pattern = ~/(?<!\w)\b(?<left>\w+)-\k<left>\b/
        def text = '''\
Saturday morning was come, and all the summer world was bright and fresh, and brimming with life. There was a song in
 every heart; and if the heart was young the music issued at the lips. There was cheer in Every face and a spring in 
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
        def words = []
        def matcher = (text =~ pattern)
        while (matcher.find()) {
            words << matcher.group()
        }
        println "ding-dong words : ${words}"
    }
}
