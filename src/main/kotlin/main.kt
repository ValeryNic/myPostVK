import java.util.Objects
import javax.xml.stream.events.Comment

fun main (){
    var post = Post()
    WallService.add(post)
    WallService.add(post)
    WallService.update(post.postLikes() )
}
data class Post(
    val id: Int=0,//Идентификатор записи.
    val owner_id: Int=0,//Идентификатор владельца стены, на которой размещена запись. В версиях API ниже 5.7 это поле называется to_id.
    val from_id: Int=0,//Идентификатор автора записи (от чьего имени опубликована запись).
    val created_by: Int=0,//Идентификатор администратора, который опубликовал запись (возвращается только для сообществ при запросе с ключом доступа администратора). Возвращается в записях, опубликованных менее 24 часов назад.
    val date: Int=1254,//Время публикации записи в формате unixtime.
    val text: String="Good",//Текст записи.
    val reply_owner_id: Int=0,//Идентификатор владельца записи, в ответ на которую была оставлена текущая.
    val reply_post_id:Int=0,//Идентификатор записи, в ответ на которую была оставлена текущая.
    val friends_ounly: Boolean=false,//true, если запись была создана с опцией «Только для друзей».
    val post_type: String="post",//Тип записи, может принимать следующие значения: post, copy, reply, postpone, suggest.
    val signer_id: Int=0,//Идентификатор автора, если запись была опубликована от имени сообщества и подписана пользователем;
    val can_pin: Boolean=false,//Информация о том, может ли текущий пользователь закрепить запись .
    val can_delete: Boolean=false,//Информация о том, может ли текущий пользователь удалить запись
    val can_edit: Boolean=false,//Информация о том, может ли текущий пользователь редактировать запись.
    val is_pinner: Boolean=false,//Информация о том, что запись закреплена
    val marker_as_ads: Boolean=false,//Информация о том, содержит ли запись отметку «реклама»
    val is_favorite: Boolean=false,//true, если объект добавлен в закладки у текущего пользователя.
    val postponed_id: Int=0,//Идентификатор отложенной записи. Это поле возвращается тогда, когда запись стояла на таймере.
    var postComments: comments = comments(),//Информация о комментариях к записи, объект с полями
    var postlikes: likes=likes()//Информация о лайках к записи, объект с полями
)


object WallService{
    private var posts = emptyArray<Post>()
    private var lastId = 0
    fun add (post: Post): Post {
        //post.id= lastId++
        posts += post.copy(id= lastId++,postComments=post.postComments.copy())
        return  posts.last()
    }
    fun likedById(id: Int){
        for ((index, post) in posts.withIndex()){
            if (post.id==id){
                posts[index]=post.copy(postComments = post.postComments.copy())
            }
        }
    }
    fun update(newPost:Post): Boolean {
        for ((index, post) in posts.withIndex()){
            if (post.id==newPost.id){
                posts[index]=newPost.copy(postComments = newPost.postComments.copy())
                return true
            }
        }
        return false
    }
}
data class comments (
    val count: Int=0,
    val can_post: Boolean=false,
    val groups_can_post: Boolean=false,
    val can_close: Boolean=false,
    val can_open: Boolean=false,
    val newComment: String ="New comment"
)
data class likes(
    val count: Int=0,
    val user_likes: Boolean=false,
    val can_likes: Boolean=false,
    val can_publish: Boolean=false
)
