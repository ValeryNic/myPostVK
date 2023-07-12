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
        var post = Post(0,0,0,0,1254,"",0,
            0,false,"post",0,false,false,
            false, false, false,false, 0,
            postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
            postReposts = RepostsPost(), views = arrayOf(),
            copyHistory= arrayOf(0), postDonut = DonutPost(), attechments = arrayOf()
        )
        val newPost: Post = WallService.add(post)
        assertEquals(post, newPost)
    }


    @Test
    fun update1() {
        var post = Post(0,0,0,0,1254,"",0,
            0,false,"post",0,false,false,
            false, false, false,false, 0,
            postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
            postReposts = RepostsPost(), views = arrayOf(),
            copyHistory= arrayOf(0), postDonut = DonutPost(), attechments = arrayOf()
        )
        WallService.add(post)
        //val result=1
        val count:Double= Math.random()
        val newId: Int = (count * (WallService.posts.size-1)).toInt()
        var newPost = WallService.posts[newId]
        newPost.postComments.canPost = true
        assertTrue(WallService.update(newId, newPost))
    }
    @Test
    fun update2() {
        val post = Post(0,0,0,0,1254,"",0,
            0,false,"post",0,false,false,
            false, false, false,false, 0,
            postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
            postReposts = RepostsPost(), views = arrayOf(),
            copyHistory= arrayOf(0), postDonut = DonutPost(), attechments = arrayOf()
        )
        val newPost = WallService.add(post)
        val post1 = Post(WallService.posts.size,0,0,0,1254,"",0,
            0,false,"post",0,false,false,
            false, false, false,false, 0,
            postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
            postReposts = RepostsPost(), views = arrayOf(),
            copyHistory= arrayOf(0), postDonut = DonutPost(), attechments = arrayOf()
        )
        assertFalse(WallService.update(post1.id, post1))
    }
    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        val comment: String = "My next comment"
        val postComment: CommentsPost = CommentsPost(0,false,false,false,false, Comments = arrayOf())
        postComment.Comments[0]=comment
        WallService.createCommentPost(4, postComment)
    }
    @Test
    fun createComment1() {
        val comment: String = "My comment for post"
        val postComment: CommentsPost = CommentsPost(0,false,false,false,false, Comments = arrayOf())
        postComment.Comments[0]=comment
        val postComment1 = WallService.createCommentPost(0,postComment)
        assertEquals(comment, postComment1.Comments[0])
    }
}
