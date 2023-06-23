import java.awt.Image
import java.lang.RuntimeException
import java.security.CodeSource
import java.util.Objects
import javax.xml.stream.events.Comment
import kotlin.random.Random

fun main (){
    var post = Post(0,0,0,0,1254,"",0,
        0,false,"post",0,false,false,
        false, false, false,false, 0,
        postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
        postReposts = RepostsPost(), views = arrayOf(),
         copyHistory= arrayOf(0), postDonut = DonutPost(), attechments = arrayOf()
    )
    WallService.add(post)
    WallService.add(post)
    WallService.likedById(post.id)
    WallService.update(post)
    post.id=1
    //post.postComments.Comment[post.postComments.count]="Good update"
    WallService.update(post)
    //WallService.update(Post(1, fromId = 1, date=1254, text = "New update"))
    WallService.print()
    println(VideoAttachment(Video(1)))
}
data class Post(
    var id: Int=0,//Идентификатор записи.
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
    val postDonut: DonutPost=DonutPost(),
    var postComments: CommentsPost = CommentsPost(),//Информация о комментариях к записи, объект с полями
    var postLikes: Likes=Likes(),//Информация о лайках к записи
    var postCopyright: CopyrightPost=CopyrightPost(),//Источник материала
    var postReposts: RepostsPost= RepostsPost(),//Информация о репостах записи («Рассказать друзьям»), объект с полями:
    var views: Array<Int> = arrayOf(0),
    //val postSource: CodeSource,//Поле возвращается только для Standalone-приложений с ключом доступа, полученным в Implicit Flow.
    val copyHistory:Array<Int> = arrayOf(0),//<Random.DefaultHistory>, Массив, содержащий историю репостов для записи.
    var attechments: Array<Attechment> = arrayOf(VideoAttachment(Video(1,1,"My story", "Video", 2048)),  AudioAttachment(Audio(0,0,"Paul McCartny")))
)

 {
    override fun equals(other: Any?): Boolean {
        if(this===other) return true
        //if (javaClass!=other?.javaClass) return false

        other as Post

        if (id != other.id) return false
        if (Likes()!= other.postLikes) return false
        return  attechments.contentEquals(other.attechments)
    }
    //override fun hashCode():Int {
    //    var result = id
    //    result= 31*result+Likes().hashCode()
    //    result=31*result+attachments.contentHashCode()
    //}
}


object WallService{
    var posts = emptyArray<Post>()
    var lastId = 0
    var comments = emptyArray<Comments>()
    //val ErrorLimit=-1
    fun clear(){
        posts = emptyArray()
        lastId = 0
    }
    fun add (post: Post): Post {
        if (lastId>2147483646){
            println("Переполнение счётчика постов")
        } else {
            posts += post.copy()//(id= ++lastId, postComments += post.postComments.copy(count=1,), postLikes = post.postLikes.copy())
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
                //posts[index]=post.copy(postComments = newPost.postComments.copy())
                return true
            }
        }
        return false
    }
    fun print(){
        for (post in posts) {
            print(post)
        }
    }
    fun createComment(postId: Int, comment: Comments): Comments {
        try {
            for ((index, post) in posts.withIndex()) {
                if (post.id == postId) {

                    comments[++lastId]=comment
                }
            }
        }catch (e: RuntimeException ){
            println("PostNotFoundException")
        }
        return comments[lastId]
    }

}
class PostNotFoundException(message: String): RuntimeException(message)
data class CommentsPost (
    var count: Int=0,//количество комментариев
    var canPost: Boolean=false,//информация о том, может ли текущий пользователь комментировать запись
    var groupsCanPost: Boolean=false,//информация о том, могут ли сообщества комментировать запись
    var canClose: Boolean=false,// может ли текущий пользователь закрыть комментарии к записи
    var canOpen: Boolean=false//может ли текущий пользователь открыть комментарии к записи
    //var Comment: Array <String> = arrayOf()
)
data class Comments (
    var id: Int = 0,//Идентификатор комментария
    var fromId: Int = 0,//Идентификатор автора комментария.
    var date: Int = 1254,//Дата создания комментария в формате Unixtime
    var text: String = "No comments"//Текст комментария
)
data class Likes(
    var count: Int=0,//число пользователей, которым понравилась запись
    var userLikes: Boolean=false,//наличие отметки «Мне нравится» от текущего пользователя;
    var canLikes: Boolean=false,//информация о том, может ли текущий пользователь поставить отметку «Мне нравится» ;
    var canPublish: Boolean=false//информация о том, может ли текущий пользователь сделать репост записи .
)
data class CopyrightPost(
    var id: Int=0,
    var link: String="Link",
    var name: String="Name",
    var type: String="Type"
)
data class DonutPost(
    var isDonut: Boolean=false,//запись доступна только платным подписчика
    var paidDuration: Int=0,//время, в течение которого запись будет доступна только платным подписчикам VK Donut;
    //var placeholder: Objects,// заглушка для пользователей, которые не оформили подписку VK Donut. Отображается вместо содержимого записи.
    var canPublishFreeCopy: Boolean=false,//можно ли открыть запись для всех пользователей, а не только подписчиков VK Donut;
    var editMode: String= "No typing"//информация о том, какие значения VK Donut можно изменить в записи.
)
data class RepostsPost(//Информация о репостах записи («Рассказать друзьям»), объект с полями:
    var count: Int=0,//число пользователей, скопировавших запись;
    var userReposted: Int=0//наличие репоста от текущего пользователя
)

//interface  Attechment {
//    val tupe: String
//}
abstract  class Attechment {
    abstract val type: String
}
data class Video(
    var id: Int=0,//Идентификатор видеозаписи.
    var ownerId: Int=0,//Идентификатор владельца видеозаписи.
    var title: String= "My story",//Название видеозаписи
    var description: String= "Video about my life",//Текст описания видеозаписи
    var duration: Int=2048//Длительность ролика в секундах

)
//data class  VideoAttachment(override  val type: String="video", val video:Video ): Attechment
data class VideoAttachment(val video: Video): Attechment() {
    override val type: String = "video"

    //get() = TODO("Not yet implemented")
    override fun toString(): String {
        return "$type with $video"//super.toString()
    }
}
data class Audio(
    var id: Int = 0,//Идентификатор аудиозаписи
    var ownerId:Int = 0,//Идентификатор владельца аудиозаписи
    var artist: String = "Paul McCarthy",//Исполнитель
    var title: String = "My love",
    var duration: Int = 390,
    var date: Int = 1254
)
data class AudioAttachment(val audio: Audio): Attechment(){
    override  val type: String = "Audio"
    override fun toString(): String {
        return "$type with $audio"
    }
}
data class File(
    var id: Int = 0,//Идентификатор
    var ownerId:Int = 0,//Идентификатор пользователя, загрузившего файл
    var title: String = "Help",
    var size: Int = 4520,
    var ext: String = ".doc"
)
data class FileAttachment( val file: File): Attechment(){
    override  val type: String = "File"
    override fun toString(): String {
        return "$type with $file"
    }
}
data class Sticker(
    var productId: Int = 0,//Идентификатор набора
    var stickerId:Int = 0,//Идентификатор стикера
    var images: Array<Image> = arrayOf(),
    var size: Int = 4520,
    var ext: String = ".doc"
)
data class StickerAttachment( val sticker: Sticker): Attechment(){
    override  val type: String = "Sticker"
    override fun toString(): String {
        return "$type with $sticker"
    }
}
data class Image(
    var url: String = "https://netology.ru/profile",
    var width: Int = 2800,
    var height: Int = 4200
)
data class ImageAttachment( val image: Image): Attechment(){
    override  val type: String = "Image"
    override fun toString(): String {
        return "$type with $image"
    }
}