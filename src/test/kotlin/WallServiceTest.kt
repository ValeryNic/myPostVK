import org.junit.Assert.*
import org.junit.Test

class WallServiceTest {

    @Test
    fun add1() {
        var post = Post()
        var result=false
        val newPost: Post = WallService.add(post)
        if(newPost.id==0) {
            result = true
        }
        assertFalse(result)
    }

    @Test
    fun update1() {
        val post = Post(id=0,owner_id=0,from_id=0, created_by =0, date = 1254, text = "Good", reply_owner_id = 0, reply_post_id = 0, friends_ounly = false, post_type = "post", signer_id = 0,can_pin = false,can_delete = false, can_edit = false,is_pinner = false,marker_as_ads = false,is_favorite = false, postponed_id = 0, postComments = comments(count = 0,can_post = true,groups_can_post = true,can_close = true,can_open = true, newComment ="Good"), postLikes = likes(count = 0,user_likes = true,can_likes = true,can_publish = true) )
        val count:Double= Math.random()
        var result=1
        val postN = WallService.add(post)
        val newId: Int = (count * (WallService.lastId-1)).toInt()
        var newPost = WallService.posts[newId]
        newPost.postComments.newComment = "testComment"
        val note:Boolean = WallService.update(newPost)
        val post1=WallService.posts[newId]
        if (post1.postComments.newComment == "testComment") {
            result = 0
        }
        assertEquals(0,result)
     }
    @Test
    fun update2() {
        val post = Post(id=0,owner_id=0,from_id=0, created_by =0, date = 1254, text = "Good", reply_owner_id = 0, reply_post_id = 0, friends_ounly = false, post_type = "post", signer_id = 0,can_pin = false,can_delete = false, can_edit = false,is_pinner = false,marker_as_ads = false,is_favorite = false, postponed_id = 0, postComments = comments(count = 0,can_post = true,groups_can_post = true,can_close = true,can_open = true, newComment ="Good"), postLikes = likes(count = 0,user_likes = true,can_likes = true,can_publish = true) )
        val newPost = WallService.add(post)
        val post1 = Post(id=WallService.lastId,owner_id=0,from_id=0, created_by =0, date = 1254, text = "Good", reply_owner_id = 0, reply_post_id = 0, friends_ounly = false, post_type = "post", signer_id = 0,can_pin = false,can_delete = false, can_edit = false,is_pinner = false,marker_as_ads = false,is_favorite = false, postponed_id = 0, postComments = comments(count = 0,can_post = true,groups_can_post = true,can_close = true,can_open = true, newComment ="Good"), postLikes = likes(count = 0,user_likes = true,can_likes = true,can_publish = true) )
        val result=WallService.update(post1)
        var resultInt=if (result==true){
            1
        } else {
            0
        }
        assertEquals(0,resultInt)
    }
}