import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun addPost() {
        val post = Post(id = 0, string = "Hello", likes = Likes(count = 10))
        val addedPost = WallService.add(post)
        assertEquals(1,addedPost.id)
    }

    @Test
    fun updatedPostTrue() {
        val service = WallService
        service.add(Post(id = 0, string = "Hello1", likes = Likes(count = 10)))
        service.add(Post(id = 1, string = "Hello2", likes = Likes(count = 20)))
        service.add(Post(id = 2, string = "Hello3", likes = Likes(count = 30)))
        val update = Post(id =1, string = "Goodbay", likes = Likes(count = 0))
        val updatedPost = WallService.update(update)
        assertTrue(updatedPost)
    }

    @Test
    fun updatedPostFalse() {
        val service = WallService
        service.add(Post(id = 0, string = "Hello1", likes = Likes(count = 10)))
        service.add(Post(id = 1, string = "Hello2", likes = Likes(count = 20)))
        service.add(Post(id = 2, string = "Hello3", likes = Likes(count = 30)))
        val update = Post(id =4, string = "Goodbay", likes = Likes(count = 0))
        val updatedPost = WallService.update(update)
        assertFalse(updatedPost)
    }

    @Test
    fun addPostTrue() {
        val service = WallService
        service.add(Post(id = 0, string = "Hello1", likes = Likes(count = 10)))
        val photo1 = Attachment.Photo(1, 1, "https1", "https2")
        val addPhoto = service.addAttachment(1,photo1)
        assertTrue(addPhoto)
    }

    @Test
    fun addPostFalse() {
        val service = WallService
        service.add(Post(id = 0, string = "Hello1", likes = Likes(count = 10)))
        val photo1 = Attachment.Photo(1, 1, "https1", "https2")
        val addPhoto = service.addAttachment(2,photo1)
        assertFalse(addPhoto)
    }
}