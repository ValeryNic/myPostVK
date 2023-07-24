//import com.sun.tools.javac.util.JCDiagnostic
import java.awt.Image
import java.lang.RuntimeException
import java.nio.charset.CoderResult
import java.security.CodeSource
import java.util.Objects
import javax.xml.stream.events.Comment
fun main () {

    var post = Post(0,0,0,0,1254,"",0,
        0,false,"post",0,false,false,
        false, false, false,false, 0,
        postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
         postReposts = RepostsPost(), postDonut = DonutPost(), attechments = arrayOf(VideoAttachment(Video(1,1,"My story", "Video", 2048)),  AudioAttachment(Audio(0,0,"Paul McCartny")))
    )
    WallService.add(post)
    var post1=WallService.copyItem(post)
    post1.ownerId=1
    WallService.add(post1)
    WallService.likedById(post.id)
    post.id = 1
    post.postComments.CommentsArray += "My comment1"
    WallService.add(post)
    WallService.createCommentPost(1, post.postComments)
    WallService.update(1, post)
    //WallService.update(2, post)
    WallService.print()
    println(VideoAttachment(Video(1,0,"My story","Video about my life",2048)))
    var note: Note = Note(0, 0,"About Netology",0,0,"", "Netology is a good site")
    var noteId = NoteService.add(0,"my Note", "I live Net",0,0)
    noteId = NoteService.add(1,"my Note1", "I live Net",0,0)
}
data class Post(
    var id: Int=0,//Идентификатор записи.
    var ownerId: Int=0,//Идентификатор владельца стены, на которой размещена запись. В версиях API ниже 5.7 это поле называется to_id.
    var fromId: Int?,//Идентификатор автора записи (от чьего имени опубликована запись).
    var createdBy: Int=0,//Идентификатор администратора, который опубликовал запись (возвращается только для сообществ при запросе с ключом доступа администратора). Возвращается в записях, опубликованных менее 24 часов назад.
    var date: Int?,//Время публикации записи в формате unixtime.
    var text: String?,//Текст записи.
    var replyOwnerId: Int=0,//Идентификатор владельца записи, в ответ на которую была оставлена текущая.
    var replyPostId:Int=0,//Идентификатор записи, в ответ на которую была оставлена текущая.
    var friendsOunly: Boolean=false,//true, если запись была создана с опцией «Только для друзей».
    var postType: String="post",//Тип записи, может принимать следующие значения: post, copy, reply, postpone, suggest.
    var signerId: Int=0,//Идентификатор автора, если запись была опубликована от имени сообщества и подписана пользователем;
    var canPin: Boolean=false,//Информация о том, может ли текущий пользователь закрепить запись .
    var canDelete: Boolean=false,//Информация о том, может ли текущий пользователь удалить запись
    var canEdit: Boolean=false,//Информация о том, может ли текущий пользователь редактировать запись.
    var isPinner: Boolean=false,//Информация о том, что запись закреплена
    var markerAsAds: Boolean=false,//Информация о том, содержит ли запись отметку «реклама»
    var isFavorite: Boolean=false,//true, если объект добавлен в закладки у текущего пользователя.
    var postPonedId: Int=0,//Идентификатор отложенной записи. Это поле возвращается тогда, когда запись стояла на таймере.
    var postDonut: DonutPost = DonutPost(),
    var postComments: CommentsPost = CommentsPost(),//Информация о комментариях к записи, объект с полями
    var postLikes: Likes=Likes(),//Информация о лайках к записи
    var postCopyright: CopyrightPost = CopyrightPost(),
    var postReposts: RepostsPost= RepostsPost(),//Информация о репостах записи («Рассказать друзьям»), объект с полями:
    //var views: Array<Int> = arrayOf(0),
    //var copyHistory:Array<Int> = arrayOf(0),//<Random.DefaultHistory>, Массив, содержащий историю репостов для записи.
    var attechments: Array<Attechment> = arrayOf(VideoAttachment(Video(1,1,"My story", "Video", 2048)),  AudioAttachment(Audio(0,0,"Paul McCartny")))
)

data class Note(
    var noteId: Int=0,
    var ownerId: Int=0,
    var title: String= "Нет заголовка",//Заголовок заметки.
    var privacy: Int = 0,//Уровень доступа к заметке
    var commentPrivacy: Int=0,//Уровень доступа к комментированию заметки
    var message: String= "Нет комментария",//Текст комментария.
    var text: String="Нет заметки"
)
data class  CommentOne(
    var id: Int=0,//Идентификатор комментария
    var fromId: Int=0,//Идентификатор автора комментария.
    var ownerId: Int=0,//идентификатор автора заметки
    var date: Int=1524,//Дата создания комментария в формате Unixtime
    var text: String="Нет комментария",//Текст комментария
    var privacyComment: Int=0,
    var deleteComment: Boolean=true
)

abstract class CrudService<T>{
    private val elems = mutableListOf<T>()
    private val id: Int=0
    abstract fun copyItem(item: T): T
    fun clear() {
        elems.clear()
    }
    fun print(){
        for (elem in elems) {
            print(elem)
        }
    }
    fun add(elem: T): T{
        elems.add(copyItem(elem))
        return elems.last()
    }
    fun update(itemId: Int, elem: T): Boolean{
        if (elems.contains(elems[itemId])){
            elems[itemId] = elem
            return true
        } else{return false}
    }
    fun delete(itemId: Int): Boolean{
        if (elems.contains(elems[itemId])){
            elems.removeAt(itemId)
            return true
        } else {return false}
    }
}
object NoteService: CrudService<Note>() {
    var notes = mutableListOf<Note>()
    var comments = mutableListOf<CommentOne>()

    override fun copyItem(item: Note): Note = item.copy()
    fun add(ownerId: Int, title: String, text: String, privacy: Int, commentPrivacy: Int): Int {
        val note = Note(notes.size, ownerId, title, privacy, commentPrivacy, "Нет комментария", text)
        notes.add(note)
        return note.noteId
    }

    fun createComment(commentId: Int, fromId: Int, ownerId: Int, date: Int, message: String, privacyComment: Int): Int {
        var result = -1
        for ((index, comment) in comments.withIndex()) {
            if (comments[index].id == commentId) {
                comments.add(CommentOne(comments.size, fromId, ownerId, date, message, privacyComment, false))
                result = comments.lastIndex
            }
        }
        return result
    }

    fun deleteNote(noteId: Int): Boolean {
        var result = false
        for ((index, note) in notes.withIndex()) {
            if (notes[index].noteId == noteId) {
                result = delete(index)
            }
        }
        return result
    }

    fun deleteComment(commentId: Int): Boolean {
        var result = false
        for ((index, comment) in comments.withIndex()) {
            if (comments[index].id == commentId) {
                comments[index].deleteComment = true
                result = true
            }
        }
        return result
    }

    fun edit(noteId: Int, note: Note): Boolean { //Редактирует заметку текущего пользователя.
        var result = false
        for ((index, note) in notes.withIndex()) {
            if (notes[index].noteId == noteId) {
                result = NoteService.update(noteId, note)
            }
        }
        return result
    }

    fun editComment(commentId: Int, comment: CommentOne): Boolean {//Редактирует указанный комментарий у заметки.
        var result = false
        for ((index, comment) in comments.withIndex()) {
            if (comments[index].id == commentId) {
                comments[index] = comment
                result = true
            }
        }
        return result
    }

    fun get(ownerId: Int): MutableList<Note>? {
        var notesOwnerId = mutableListOf<Note>()
        for ((index, note) in notes.withIndex()) {
            if (note.ownerId == ownerId) {
                notesOwnerId.add(note)
            }
        }
        return notesOwnerId
    }

    fun getById(noteId: Int): Note {
        var result = notes[notes.size]
        for ((index, note) in notes.withIndex()) {
            if (notes[index].noteId == noteId) {
                result = notes[index]
            }
        }
        return result
    }

    fun getComments(noteId: Int): Array<CommentOne>? {
        var commentArray = emptyArray<CommentOne>()
        for ((index, note) in notes.withIndex()) {
            if (notes[index].noteId == noteId) {
                for ((indexComment, comment) in comments.withIndex()) {
                    if (comments[indexComment].ownerId == notes[index].ownerId) {
                        commentArray += comments[indexComment]
                    }
                }
            }
        }
        return commentArray
    }

    fun restoreComment(commentId: Int): Int {
        var result = -1
        for ((index, comment) in comments.withIndex()) {
            if (comments[index].id == commentId) {
                comments[index].deleteComment = false
                result = 1
            }
        }
        return result
    }
}
object WallService: CrudService<Post>() {
    var posts = mutableListOf<Post>()
    //var comments = mutableListOf<CommentOne>()
    override fun copyItem(item: Post): Post = item.copy()




    fun likedById(id: Int): Int {
        var result = 0
        for ((index, post) in posts.withIndex()) {
            if (posts[index].id == id && posts[index].postLikes.canLikes == true) {
                posts[index].postLikes.count++
                result = posts[index].postLikes.count
                //posts[index] = post.copy(postLikes = post.postLikes.copy())
            }
        }
        return  result
    }

    fun createCommentPost(postId: Int, postComments: CommentsPost ): CommentsPost {
        var result = CommentsPost()
        for ((index, post) in posts.withIndex()) {
                if (posts[index].id == postId) {
                    posts[index].postComments = postComments
                }
            result = postComments
        }
        return result
    }
}
class PostNotFoundException(message: String): RuntimeException(message)

data class Likes(
    var count: Int=0,//число пользователей, которым понравилась запись
    var userLikes: Boolean=false,//наличие отметки «Мне нравится» от текущего пользователя;
    var canLikes: Boolean=false,//информация о том, может ли текущий пользователь поставить отметку «Мне нравится» ;
    var canPublish: Boolean=false//информация о том, может ли текущий пользователь сделать репост записи .
)
data class CommentsPost (
    var count: Int=0,//количество комментариев
    var canPost: Boolean=false,//информация о том, может ли текущий пользователь комментировать запись
    var groupsCanPost: Boolean=false,//информация о том, могут ли сообщества комментировать запись
    var canClose: Boolean=false,// может ли текущий пользователь закрыть комментарии к записи
    var canOpen: Boolean=false,//может ли текущий пользователь открыть комментарии к записи
    var CommentsArray: Array <String> = arrayOf("No comment")
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
data class AudioAttachment(val audio: Audio): Attechment() {
    override val type: String = "Audio"
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
