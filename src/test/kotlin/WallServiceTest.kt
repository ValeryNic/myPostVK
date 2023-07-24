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
        val newPost: Post = WallService.add(post)
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
        WallService.add(post)
        //val result=1
        val count:Double= Math.random()
        val newId: Int = (count * (WallService.posts.size-1)).toInt()
        val newPost = WallService.posts[newId]
        newPost.postComments.canPost = true
        assertTrue(WallService.update(newId, newPost))
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
        var posts = mutableListOf<Post>()
        val post1=WallService.add(post)
        val comment: String = "My comment for post"
        val postComment: CommentsPost = CommentsPost(0,false,false,false,false, CommentsArray = arrayOf())
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
        var posts = mutableListOf<Post>()
        var post1=WallService.add(post)
        val comment: String = "My comment for post"
        //var postComment: CommentsPost = CommentsPost(0,false,false,false,false, CommentsArray = arrayOf())
        post1.postComments.CommentsArray += comment
        //post.postComments.CommentsArray[0]=comment
        post.postComments = WallService.createCommentPost(0,post1.postComments)
        val result = post.postComments.CommentsArray.last()
        assertEquals(comment, result)
    }
}
