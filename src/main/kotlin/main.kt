import com.sun.jdi.Type

data class Post(
    val id: Int,//Идентификатор записи
    val ownerId: Int = 122,//Идентификатор владельца стены, на которой размещена запись
    val fromId: Int = 123,//Идентификатор автора записи (от чьего имени опубликована запись)
    val date: Int? = null,//Время публикации записи
    val string: String,//Текст записи
    val friendsOnly: Boolean? = null,//если запись была создана с опцией «Только для друзей»
    val canPin: Boolean = false,//Информация о том, может ли текущий пользователь закрепить запись
    val canDelete: Boolean = false,//Информация о том, может ли текущий пользователь удалить запись
    val canEdit: Boolean = false,//Информация о том, может ли текущий пользователь редактировать запись
    val isPinned: Boolean = true,//Информация о том, что запись закреплена
    val likes: Likes,//Информация о лайках
    val attachments: Array<Attachment> = emptyArray()
)

data class Likes(
    val count: Int,//число пользователей, которым понравилась запись;
    val userLikes: Boolean = true,//наличие отметки «Мне нравится» от текущего пользователя
    val canLike: Boolean = true,// информация о том, может ли текущий пользователь поставить отметку «Мне нравится»
    val canPublish: Boolean = false//информация о том, может ли текущий пользователь сделать репост записи
)

interface Attachment {
    val type: String
}

data class Photo(val id: Int, val ownerId: Int, val photo130: String, val photo604: String)
data class PhotoAttachment(val photo: Photo) : Attachment {
    override val type: String = "photo"
}

data class Video(val id: Int, val ownerId: Int, val title: String, val duration: Int)
data class VideoAttachment(val video: Video) : Attachment {
    override val type: String = "video"
}

data class Audio(val id: Int, val ownerId: Int, val artist: String, val title: String)
data class AudioAttachment(val audio: Audio) : Attachment {
    override val type: String = "audio"
}

data class Doc(val id: Int, val ownerId: Int, val title: String)
data class DocAttachment(val doc: Doc) : Attachment {
    override val type: String = "doc"
}

data class Sticker(val productId: Int, val stickerId: Int, val animationUrl: String)
data class StickerAttachment(val sticker: Sticker) : Attachment {
    override val type: String = "sticker"
}


object WallService {
    private var posts = emptyArray<Post>()
    private var nextId = 0

    fun add(post: Post): Post {
        posts += post.copy(++nextId, likes = post.likes.copy())
        return posts.last()
    }

    fun update(newPost: Post): Boolean {
        for ((index, oldPost) in posts.withIndex()) {
            if (newPost.id == oldPost.id) {
                posts[index] = newPost.copy(likes = oldPost.likes.copy())
                return true
            }
        }
        return false
    }

    fun clear() {
        posts = emptyArray()
        nextId = 0
    }
}

fun main() {

    val post1 = Post(id = 0, string = "Первый пост", likes = Likes(count = 5))
    val addPost1 = WallService.add(post1)
    println("Добавлен пост: ${addPost1.string} c ID: ${addPost1.id} и количеством лайков ${addPost1.likes.count}")

    val post2 = Post(id = 1, string = "Второй пост", likes = Likes(count = 10))
    val addPost2 = WallService.add(post2)
    println("Добавлен пост: ${addPost2.string} c ID: ${addPost2.id} и количеством лайков ${addPost2.likes.count}")

    val updatePost1 = addPost1.copy(string = "Обновленный первый пост")
    val result = WallService.update(updatePost1)
    println("Обновление прошло успешно - $result, новый текст поста ${addPost1.id}: ${updatePost1.string} и количеством лайков ${updatePost1.likes.count}")

    println(PhotoAttachment(Photo(1, 1, "https1_1", "https1_2")))
    println(AudioAttachment(Audio(2, 2, "Nirvana", "Rape me")))
    println(VideoAttachment(Video(3, 3, "movie", 30)))
    println(DocAttachment(Doc(4, 4, "Document")))
    println(StickerAttachment(Sticker(5, 5, "URL")))

}
