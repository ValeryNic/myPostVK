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
        val post = Post(
            0,
            0,
            0,
            0,
            1254,
            "",
            0,
            0,
            false,
            "post",
            0,
            false,
            false,
            false,
            false,
            false,
            false,
            0,
            postComments = CommentsPost(),
            postLikes = Likes(),
            postCopyright = CopyrightPost(),
            postReposts = RepostsPost(),
            postDonut = DonutPost(),
            attechments = arrayOf(
                VideoAttachment(Video(1, 1, "My story", "Video", 2048)),
                AudioAttachment(Audio(0, 0, "Paul McCartny"))
            )
        )
        val newPost = WallService.add(post)
        assertEquals(post, newPost)
    }


    @Test
    fun update1() {
        val post = Post(
            0,
            0,
            0,
            0,
            1254,
            "",
            0,
            0,
            false,
            "post",
            0,
            false,
            false,
            false,
            false,
            false,
            false,
            0,
            postComments = CommentsPost(),
            postLikes = Likes(),
            postCopyright = CopyrightPost(),
            postReposts = RepostsPost(),
            postDonut = DonutPost(),
            attechments = arrayOf(
                VideoAttachment(Video(1, 1, "My story", "Video", 2048)),
                AudioAttachment(Audio(0, 0, "Paul McCartny"))
            )
        )
        var post1 = WallService.add(post)
        post.friendsOunly = true
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
        val post = Post(
            1, 0, 0, 0, 1254, "", 0,
            0, false, "post", 0, false, false,
            false, false, false, false, 0,
            postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
            postReposts = RepostsPost(),
            postDonut = DonutPost(), attechments = arrayOf()
        )
        WallService.addPost(post)
        val comment: String = "My next comment"
        WallService.createComment(4, comment)
    }


    @Test
    fun createComment1() {
        val post = Post(0,0,0,0,1254,"",0,
            0,false,"post",0,false,false,
            false, false, false,false, 0,
            postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
            postReposts = RepostsPost(),
            postDonut = DonutPost(), attechments = arrayOf()
        )
        WallService.addPost(post)
        val comment: String = "My new comment"
        val comment1: String = WallService.createComment(0,comment)
        assertEquals(comment, comment1)
    }

}
