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
        val post = Post(id=0,ownerId=0,fromId=0, createdBy =0,
            date = 1254, text = "Good", replyOwnerId = 0, replyPostId = 0,
            friendsOunly = false, postType = "post", signerId = 0,
            canPin = false,canDelete = false, canEdit = false, isPinner = false,
            markerAsAds = false,isFavorite = false, postPonedId = 0,
            postComments = Comments(count = 0,canPost = true,groupsCanPost = true,canClose = true,canOpen = true, newComment ="Good"),
            postLikes = Likes(count = 0,userLikes = true,canLikes = true,canPublish = true) )
        val newPost: Post = WallService.add(post)
        assertNotEquals(0,newPost.id)
    }

    @Test
    fun update1() {
        val post = Post(id=0,ownerId=0,fromId=0, createdBy =0, date = 1254,
            text = "Good", replyOwnerId = 0, replyPostId = 0, friendsOunly = false,
            postType = "post", signerId = 0,canPin = false,canDelete = false,
            canEdit = false,isPinner = false,markerAsAds = false,isFavorite = false, postPonedId = 0,
            postComments = Comments(count = 0,canPost = true,groupsCanPost = true,canClose = true,canOpen = true, newComment ="Good"),
            postLikes = Likes(count = 0,userLikes = true,canLikes = true,canPublish = true) )
        WallService.add(post)
        val result=1
        val count:Double= Math.random()
        val newId: Int = (count * (WallService.lastId-1)).toInt()
        var newPost = WallService.posts[newId]
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
        val post = Post(id=0,ownerId=0,fromId=0, createdBy =0, date = 1254, text = "Good",
            replyOwnerId = 0, replyPostId = 0, friendsOunly = false, postType = "post",
            signerId = 0,canPin = false,canDelete = false, canEdit = false,isPinner = false,
            markerAsAds = false,isFavorite = false, postPonedId = 0,
            postComments = Comments(count = 0,canPost = true,groupsCanPost = true,canClose = true,canOpen = true, newComment ="Good"),
            postLikes = Likes(count = 0,userLikes = true,canLikes = true,canPublish = true) )
        val newPost = WallService.add(post)
        val post1 = Post(id=WallService.lastId,ownerId=0,fromId=0, createdBy =0, date = 1254,
            text = "Good", replyOwnerId = 0, replyPostId = 0, friendsOunly = false, postType = "post",
            signerId = 0,canPin = false,canDelete = false, canEdit = false,isPinner = false,
            markerAsAds = false,isFavorite = false, postPonedId = 0,
            postComments = Comments(count = 0,canPost = true,groupsCanPost = true,canClose = true,canOpen = true, newComment ="Good"),
            postLikes = Likes(count = 0,userLikes = true,canLikes = true,canPublish = true) )
        assertFalse(WallService.update(post1))
    }
}