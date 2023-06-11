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
        val post = Post(0,0,0,0,1254,"",0,
            0,false,"post",0,false,false,
            false, false, false,false, 0,
            Comments(),Likes(), Copyright(), Reposts(), arrayOf(0))
        val newPost: Post = WallService.add(post)
        assertEquals(1,newPost.id)
    }

    @Test
    fun update1() {
        val post = Post(0,0,0,0,1254,"",0,
            0,false,"post",0,false,false,
            false, false, false,false, 0,
            Comments(),Likes(), Copyright(), Reposts(), arrayOf(0))
        WallService.add(post)
        //val result=1
        val count:Double= Math.random()
        val newId: Int = (count * (WallService.lastId-1)).toInt()
        val newPost = WallService.posts[newId]
        newPost.postComments.newComment = "testComment"
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
            Comments(),Likes(), Copyright(), Reposts(), arrayOf(0))
        val newPost = WallService.add(post)
        val post1 = Post(id=WallService.lastId+1,0,0,0,1254,"",0,
            0,false,"post",0,false,false,
            false, false, false,false, 0,
            Comments(),Likes(), Copyright(), Reposts(), arrayOf(0))
        assertFalse(WallService.update(post1))
    }
}