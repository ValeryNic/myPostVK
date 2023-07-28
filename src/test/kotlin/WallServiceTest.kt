import org.junit.Test
import org.junit.Before
import org.junit.Assert.*

class WallServiceTest {
    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }
    @Test
    fun add() {
        val post = Post(0,0,0,0,1254,"",0,
            0,false,"post",0,false,false,
            false, false, false,false, 0,
            postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
            postReposts = RepostsPost(),
            postDonut = DonutPost(), attechments = arrayOf(VideoAttachment(Video(1,1,"My story", "Video", 2048)),  AudioAttachment(Audio(0,0,"Paul McCartny")))
        )
        val newPost= WallService.add(post)
        assertEquals(post, newPost)
    }


    @Test
    fun update1() {
        val post = Post(0,0,0,0,1254,"",0,
            0,false,"post",0,false,false,
            false, false, false,false, 0,
            postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
            postReposts = RepostsPost(),
           postDonut = DonutPost(), attechments = arrayOf(VideoAttachment(Video(1,1,"My story", "Video", 2048)),  AudioAttachment(Audio(0,0,"Paul McCartny")))
        )
        var post1 = WallService.add(post)
        post.friendsOunly=true
        assertTrue(WallService.update(post.id, post))
    }
//    @Test
//    fun update2() {
//        val post = Post(0,0,0,0,1254,"",0,
//            0,false,"post",0,false,false,
//            false, false, false,false, 0,
//            postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
//            postReposts = RepostsPost(), views = arrayOf(),
//            copyHistory= arrayOf(0), postDonut = DonutPost(), attechments = arrayOf()
//        )
//        val newPost = WallService.add(post)
//        val post1 = Post(WallService.posts.size,0,0,0,1254,"",0,
//            0,false,"post",0,false,false,
//            false, false, false,false, 0,
//            postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
//            postReposts = RepostsPost(), views = arrayOf(),
//            copyHistory= arrayOf(0), postDonut = DonutPost(), attechments = arrayOf()
//        )
//        assertFalse(WallService.update(post1.id, post1))
//    }
    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
    val post = Post(0,0,0,0,1254,"",0,
        0,false,"post",0,false,false,
        false, false, false,false, 0,
        postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
        postReposts = RepostsPost(),
        postDonut = DonutPost(), attechments = arrayOf(VideoAttachment(Video(1,1,"My story", "Video", 2048)),  AudioAttachment(Audio(0,0,"Paul McCartny")))
    )
        var posts = mutableMapOf<Int,Post>()
        var post1=WallService.add(post)
        val comment: String = "My comment for post"
        post1.postComments.CommentsArray += comment
        post.postComments = WallService.createCommentPost(2, post1.postComments)
    }
    @Test
    fun createComment1() {
        val post = Post(0,0,0,0,1254,"",0,
            0,false,"post",0,false,false,
            false, false, false,false, 0,
            postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
            postReposts = RepostsPost(),
            postDonut = DonutPost(), attechments = arrayOf(VideoAttachment(Video(1,1,"My story", "Video", 2048)),  AudioAttachment(Audio(0,0,"Paul McCartny")))
        )
        //var posts = mutableMapOf<Int,Post>()
        var post1=WallService.add(post)
        println(post1.id)
        println(post.id)
        val post2 = posts[0]
        val comment: String = "My comment for post"
        post.postComments.CommentsArray += comment
        post1.postComments = WallService.createCommentPost(post.id,post.postComments)
        val result = post1.postComments.CommentsArray.last()
        assertEquals(comment, result)
    }
}
