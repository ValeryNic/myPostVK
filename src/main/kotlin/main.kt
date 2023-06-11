import java.util.Objects
import javax.xml.stream.events.Comment

fun main (){
    var post = Post(0,0,0,0,1254,"",0,
        0,false,"post",0,false,false,
        false, false, false,false, 0,
        Comments(),Likes(), Copyright(), Reposts(), arrayOf(0))
    WallService.add(post)
    WallService.add(post)
    WallService.likedById(post.id)
    WallService.update(post)
}
data class Post(
    val id: Int=0,//Идентификатор записи.
    val ownerId: Int=0,//Идентификатор владельца стены, на которой размещена запись. В версиях API ниже 5.7 это поле называется to_id.
    val fromId: Int?,//Идентификатор автора записи (от чьего имени опубликована запись).
    val createdBy: Int=0,//Идентификатор администратора, который опубликовал запись (возвращается только для сообществ при запросе с ключом доступа администратора). Возвращается в записях, опубликованных менее 24 часов назад.
    val date: Int?,//Время публикации записи в формате unixtime.
    val text: String?,//Текст записи.
    val replyOwnerId: Int=0,//Идентификатор владельца записи, в ответ на которую была оставлена текущая.
    val replyPostId:Int=0,//Идентификатор записи, в ответ на которую была оставлена текущая.
    val friendsOunly: Boolean=false,//true, если запись была создана с опцией «Только для друзей».
    val postType: String="post",//Тип записи, может принимать следующие значения: post, copy, reply, postpone, suggest.
    val signerId: Int=0,//Идентификатор автора, если запись была опубликована от имени сообщества и подписана пользователем;
    val canPin: Boolean=false,//Информация о том, может ли текущий пользователь закрепить запись .
    val canDelete: Boolean=false,//Информация о том, может ли текущий пользователь удалить запись
    val canEdit: Boolean=false,//Информация о том, может ли текущий пользователь редактировать запись.
    val isPinner: Boolean=false,//Информация о том, что запись закреплена
    val markerAsAds: Boolean=false,//Информация о том, содержит ли запись отметку «реклама»
    val isFavorite: Boolean=false,//true, если объект добавлен в закладки у текущего пользователя.
    val postPonedId: Int=0,//Идентификатор отложенной записи. Это поле возвращается тогда, когда запись стояла на таймере.
    var postComments: Comments = Comments(),//Информация о комментариях к записи, объект с полями
    var postLikes: Likes=Likes(),//Информация о лайках к записи
    var postCopyright: Copyright=Copyright(),//Источник материала
    var postReposts: Reposts= Reposts(),//Информация о репостах записи («Рассказать друзьям»), объект с полями:
    var views: Array<Int> = arrayOf(0)
)


object WallService{
    var posts = emptyArray<Post>()
    var lastId = 0
    //val ErrorLimit=-1
    fun clear(){
        posts = emptyArray()
        lastId = 0
    }
    fun add (post: Post): Post {
        if (lastId>2147483646){
            println("Переполнение счётчика постов")
        } else {
            posts += post.copy(id= ++lastId, postComments = post.postComments.copy())
        }
        return  posts.last()
    }
    fun likedById(id: Int){
        for ((index, post) in posts.withIndex()){
            if (post.id==id && post.postLikes.canLikes==true && post.postLikes.userLikes==true && post.postLikes.canPublish==true ){
                post.postLikes.count = post.postLikes.count++
                posts[index]=post.copy(postLikes = post.postLikes.copy())
            }
        }
    }
    fun update(newPost:Post): Boolean {
        for ((index, post) in posts.withIndex()){
            if (post.id==newPost.id){
                posts[index]=post.copy(postComments = newPost.postComments.copy())
                return true
            }
        }
        return false
    }
}
data class Comments (
    var count: Int=0,//количество комментариев
    var canPost: Boolean=false,//информация о том, может ли текущий пользователь комментировать запись
    var groupsCanPost: Boolean=false,//информация о том, могут ли сообщества комментировать запись
    var canClose: Boolean=false,// может ли текущий пользователь закрыть комментарии к записи
    var canOpen: Boolean=false,//может ли текущий пользователь открыть комментарии к записи
    var newComment: String ="New comment"
)
data class Likes(
    var count: Int=0,//число пользователей, которым понравилась запись
    var userLikes: Boolean=false,//наличие отметки «Мне нравится» от текущего пользователя;
    var canLikes: Boolean=false,//информация о том, может ли текущий пользователь поставить отметку «Мне нравится» ;
    var canPublish: Boolean=false//информация о том, может ли текущий пользователь сделать репост записи .
)
data class Copyright(
    var id: Int=0,
    var link: String="Link",
    var name: String="Name",
    var type: String="Type"
)
data class Reposts(//Информация о репостах записи («Рассказать друзьям»), объект с полями:
    var count: Int=0,//число пользователей, скопировавших запись;
    var userReposted: Int=0//наличие репоста от текущего пользователя
)

