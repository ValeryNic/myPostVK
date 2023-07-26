import org.junit.Assert.*
import org.junit.Test
import org.junit.Before

class WallServiceTest {
    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }
    @Test
    fun add1() {
        var post = Post(0,0,0,0,1254,"",0,
            0,false,"post",0,false,false,
            false, false, false,false, 0,
            postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
            postReposts = RepostsPost(), views = arrayOf(),
            copyHistory= arrayOf(0), postDonut = DonutPost(), attechments = arrayOf()
        )
        val newPost: Post = WallService.add(post)
        assertEquals(0,newPost.id)
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
        val count:Double= Math.random()
        val newId: Int = (count * (WallService.lastId-1)).toInt()
        val newPost = WallService.posts[newId]
        newPost.postComments.canPost = true
        //val note:Boolean = WallService.update(newPost)
        //val post1=WallService.posts[newId]
        //if (post1.postComments.newComment == "testComment") {
        //    result = 0
        //}
        //assertEquals(0,result)
        assertTrue(WallService.update(newPost))
     }
    
    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        val post = Post(0,0,0,0,1254,"",0,
            0,false,"post",0,false,false,
            false, false, false,false, 0,
            postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
            postReposts = RepostsPost(), views = arrayOf(),
            copyHistory= arrayOf(0), postDonut = DonutPost(), attechments = arrayOf()
        )
        WallService.add(post)
        val comment: String = "My next comment"
        WallService.createComment(4, comment)
    }
    @Test
    fun createComment1() {
        val post = Post(0,0,0,0,1254,"",0,
            0,false,"post",0,false,false,
            false, false, false,false, 0,
            postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
            postReposts = RepostsPost(), views = arrayOf(),
            copyHistory= arrayOf(0), postDonut = DonutPost(), attechments = arrayOf()
        )
        WallService.add(post)
        val comment: String = "My new comment"
        val comment1: String = WallService.createComment(0,comment)
        assertEquals(comment, comment1)
    }
}