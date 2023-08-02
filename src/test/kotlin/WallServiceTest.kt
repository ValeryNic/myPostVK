import org.junit.Test
import org.junit.Before
import org.junit.Assert.*

class NoteServiceTest{
    @Before
    fun clearBeforeTestNote(){
        NoteService.clear()
    }
    @Test
    fun add1(){
        var note = Note(0, 0,"About Netology",0,0,"", "Netology is a good site",0)
        NoteService.add(note.copy())
        val note1 = NoteService.notes[NoteService.notes.size-1]!!
        assertEquals("About Netology", note1.title)
    }
    @Test
    fun  createCommentAddNotesCommentsId() {
        var note = Note(0, 0,"About Netology",0,0,"", "Netology is a good site",0)
        NoteService.add(note.copy())
        var comment = CommentOne(0,0,note.id,1524,"Yes, yes",0,false)
        var comment1 = NoteService.createComment(comment.copy())
        val note2 = NoteService.notes[0]
        assertEquals(1, note2?.commentsId )
    }
    @Test
    fun  createCommentInComments() {
        var note = Note(0, 0,"About Netology",0,0,"", "Netology is a good site",0)
        NoteService.add(note.copy())
        var comment = CommentOne(0,0,note.id,1524,"Yes, yes",0,false)
        var comment1 = NoteService.createComment(comment.copy())
        assertEquals(comment, comment1 )
    }
    @Test
    fun deleteNote1(){
        var note = Note(0, 0,"About Netology",0,0,"", "Netology is a good site",0)
        NoteService.add(note.copy())
        var comment = CommentOne(0,0,note.id,1524,"Yes, yes",0,false)
        var comment1 = NoteService.createComment(comment.copy())
        val result =NoteService.delete(0)
        assertEquals(1, result)
    }
    @Test
    fun deleteComment1(){
        var note = Note(0, 0,"About Netology",0,0,"", "Netology is a good site",0)
        NoteService.add(note.copy())
        var comment = CommentOne(0,0,note.id,1524,"Yes, yes",0,false)
        var comment1 = NoteService.createComment(comment.copy())
        val result =NoteService.deleteComment(0)
        assertEquals(1,result)
    }
    @Test
    fun edit1() {
        var note = Note(0, 0,"About Netology",0,0,"", "Netology is a good site",0)
        NoteService.add(note.copy())
        note.commentsId = 3
        NoteService.edit(0,note)
        val result =NoteService.notes[0]?.commentsId ?: throw NoteService.NoNoteException()
        assertEquals(3,result)
    }
    @Test
    fun editComment1() {
        var note = Note(0, 0,"About Netology",0,0,"", "Netology is a good site",0)
        NoteService.add(note.copy())
        var comment = CommentOne(0,0,0,1524,"Yes, yes",0,false)
        NoteService.createComment(comment.copy())
        comment.text = "It's a good site"
        NoteService.editComment(0,comment)
        val comment1 = NoteService.comments[0]?.copy() ?: throw  NoteService.NoCommentException()
        assertEquals(comment1.text, comment.text)
    }
    @Test
    fun get1() {
        val note = Note(0, 0,"About Netology",0,0,"", "Netology is a good site",0)
        NoteService.add(note.copy())
        val note1 = Note(1, 0,"About Netology site",0,0,"", "Netology is a good site",0)
        NoteService.add(note.copy())
        val notes1 = NoteService.get(0)
        assertEquals(2,notes1.size)
    }
    @Test
    fun getById1() {
        val note = Note(0, 0, "About Netology", 0, 0, "", "Netology is a good site", 0)
        NoteService.add(note.copy())
        val note1 = Note(1, 0, "About Netology site", 0, 0, "", "Netology is a good site", 0)
        NoteService.add(note.copy())
        val note2 = NoteService.getById(1)
        assertEquals(note1.text, note2.text)
    }
    @Test
    fun getComments1() {
        val note = Note(0, 0, "About Netology", 0, 0, "", "Netology is a good site", 0)
        NoteService.add(note.copy())
        val note1 = Note(1, 0, "About Netology site", 0, 0, "", "Netology is a good site", 0)
        NoteService.add(note.copy())
        val comment = CommentOne(0,0,0,1524,"Yes, yes",0,false)
        NoteService.createComment(comment.copy())
        val comment1 = CommentOne(1,0,0,1524,"It's a good site",0,false)
        NoteService.createComment(comment1.copy())
        val comment2 = CommentOne(2,0,1,1524,"Yes, yes",0,false)
        NoteService.createComment(comment2.copy())
        val commentsOfNote = NoteService.getComments(0)
        assertEquals(2,commentsOfNote.size)
    }
    @Test
    fun restoreComment1() {
        val note = Note(0, 0, "About Netology", 0, 0, "", "Netology is a good site", 0)
        NoteService.add(note.copy())
        val comment = CommentOne(0,0,0,1524,"Yes, yes",0,true)
        NoteService.createComment(comment.copy())
        val comment1 = CommentOne(1,0,0,1524,"It's a good site",0,false)
        NoteService.createComment(comment1.copy())
        assertEquals(1,NoteService.restoreComment(0))
    }
}
class WallServiceTest {
    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun add() {
        val post = Post(
            0,
            0,
            0,
            0,
            1254,
            "",
            0,
            0,
            false,
            "post",
            0,
            false,
            false,
            false,
            false,
            false,
            false,
            0,
            postComments = CommentsPost(),
            postLikes = Likes(),
            postCopyright = CopyrightPost(),
            postReposts = RepostsPost(),
            postDonut = DonutPost(),
            attechments = arrayOf(
                VideoAttachment(Video(1, 1, "My story", "Video", 2048)),
                AudioAttachment(Audio(0, 0, "Paul McCartny"))
            )
        )
        val newPost = WallService.addPost(post)
        assertEquals(post, newPost)
    }


    @Test
    fun update1() {
        val post = Post(
            0,
            0,
            0,
            0,
            1254,
            "",
            0,
            0,
            false,
            "post",
            0,
            false,
            false,
            false,
            false,
            false,
            false,
            0,
            postComments = CommentsPost(),
            postLikes = Likes(),
            postCopyright = CopyrightPost(),
            postReposts = RepostsPost(),
            postDonut = DonutPost(),
            attechments = arrayOf(
                VideoAttachment(Video(1, 1, "My story", "Video", 2048)),
                AudioAttachment(Audio(0, 0, "Paul McCartny"))
            )
        )
        var post1 = WallService.addPost(post)
        post.friendsOunly = true
        assertTrue(WallService.updatePost(post.id, post))
    }

    //    @Test
//    fun update2() {
//        val post = Post(0,0,0,0,1254,"",0,
//            0,false,"post",0,false,false,
//            false, false, false,false, 0,
//            postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
//            postReposts = RepostsPost(), views = arrayOf(),
//            copyHistory= arrayOf(0), postDonut = DonutPost(), attechments = arrayOf()
//        )
//        val newPost = WallService.add(post)
//        val post1 = Post(WallService.posts.size,0,0,0,1254,"",0,
//            0,false,"post",0,false,false,
//            false, false, false,false, 0,
//            postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
//            postReposts = RepostsPost(), views = arrayOf(),
//            copyHistory= arrayOf(0), postDonut = DonutPost(), attechments = arrayOf()
//        )
//        assertFalse(WallService.update(post1.id, post1))
//    }
    @Test(expected = PostNotFoundException::class)
    fun shouldThrow() {
        val post = Post(
            1, 0, 0, 0, 1254, "", 0,
            0, false, "post", 0, false, false,
            false, false, false, false, 0,
            postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
            postReposts = RepostsPost(),
            postDonut = DonutPost(), attechments = arrayOf()
        )
        WallService.addPost(post)
        val comment: String = "My next comment"
        WallService.createComment(4, comment)
    }


    @Test
    fun createComment1() {
        val post = Post(0,0,0,0,1254,"",0,
            0,false,"post",0,false,false,
            false, false, false,false, 0,
            postComments = CommentsPost(), postLikes = Likes(), postCopyright = CopyrightPost(),
            postReposts = RepostsPost(),
            postDonut = DonutPost(), attechments = arrayOf()
        )
        WallService.addPost(post)
        val comment: String = "My new comment"
        val comment1: String = WallService.createComment(0,comment)
        assertEquals(comment, comment1)
    }

}
