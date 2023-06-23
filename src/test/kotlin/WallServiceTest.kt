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
        assertEquals(1,newPost.id)
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
        val post1 = Post(id=WallService.lastId+1,0,0,0,1254,"",0,
            0,false,"post",0,false,false,
            false, false, false,false, 0,
            postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
            postReposts = RepostsPost(), views = arrayOf(),
            copyHistory= arrayOf(0), postDonut = DonutPost(), attechments = arrayOf()
        )
        assertFalse(WallService.update(post1))
    }
    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        WallService.createComment(4, comment = WallService.comments[4])
    }
    @Test
    fun createComment1() {
        val comment: Comments = Comments()
        val comment1: Comments = WallService.createComment(0,comment)
        assertEquals(comment, comment1)
    }
}