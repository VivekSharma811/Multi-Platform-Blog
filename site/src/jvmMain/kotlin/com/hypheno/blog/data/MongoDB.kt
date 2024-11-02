package com.hypheno.blog.data

import com.hypheno.blog.models.Constants.POSTS_PER_PAGE
import com.hypheno.blog.models.Post
import com.hypheno.blog.models.PostWithoutDetails
import com.hypheno.blog.models.User
import com.hypheno.blog.util.Constants.DATABASE_NAME
import com.hypheno.blog.util.Constants.MAIN_POSTS_LIMIT
import com.mongodb.client.model.Filters.and
import com.varabyte.kobweb.api.data.add
import com.varabyte.kobweb.api.init.InitApi
import com.varabyte.kobweb.api.init.InitApiContext
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitLast
import org.litote.kmongo.coroutine.toList
import org.litote.kmongo.descending
import org.litote.kmongo.eq
import org.litote.kmongo.`in`
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.reactivestreams.getCollection
import org.litote.kmongo.regex
import org.litote.kmongo.setValue

@InitApi
fun initMongoDB(context: InitApiContext) {
    System.setProperty(
        "org.litote.mongo.test.mapping.service",
        "org.litote.kmongo.serialization.SerializationClassMappingTypeService"
    )
    context.data.add(MongoDB(context))
}

class MongoDB(val context: InitApiContext) : MongoRepository {

    private val client = KMongo.createClient()
    private val database = client.getDatabase(DATABASE_NAME)
    private val userCollection = database.getCollection<User>()
    private val postCollection = database.getCollection<Post>()

    override suspend fun checkUserExistence(user: User): User? {
        return try {
            userCollection
                .find(
                    and(
                        User::username eq user.username,
                        User::password eq user.password
                    )
                ).awaitFirst()
        } catch (e: Exception) {
            context.logger.error(e.message.toString())
            null
        }
    }

    override suspend fun checkUserId(id: String): Boolean {
        return try {
            val documentCount = userCollection.countDocuments(User::_id eq id).awaitFirst()
            documentCount > 0
        } catch (e: Exception) {
            context.logger.error(e.message.toString())
            false
        }
    }

    override suspend fun addPost(post: Post): Boolean {
        return postCollection.insertOne(post).awaitFirst().wasAcknowledged()
    }

    override suspend fun readMyPosts(skip: Int, author: String): List<PostWithoutDetails> {
        return postCollection
            .withDocumentClass(PostWithoutDetails::class.java)
            .find(PostWithoutDetails::author eq author)
            .sort(descending(PostWithoutDetails::date))
            .skip(skip)
            .limit(POSTS_PER_PAGE)
            .toList()
    }

    override suspend fun readMainPosts(): List<PostWithoutDetails> {
        return postCollection
            .withDocumentClass(PostWithoutDetails::class.java)
            .find(PostWithoutDetails::isMain eq true)
            .sort(descending(PostWithoutDetails::date))
            .limit(MAIN_POSTS_LIMIT)
            .toList()
    }

    override suspend fun readLatestPosts(skip: Int): List<PostWithoutDetails> {
        return postCollection
            .withDocumentClass(PostWithoutDetails::class.java)
            .find(
                and(
                    PostWithoutDetails::isPopular eq false,
                    PostWithoutDetails::isMain eq false,
                    PostWithoutDetails::isSponsored eq false
                )
            )
            .sort(descending(PostWithoutDetails::date))
            .skip(skip)
            .limit(POSTS_PER_PAGE)
            .toList()
    }

    override suspend fun readSponsoredPosts(): List<PostWithoutDetails> {
        return postCollection
            .withDocumentClass(PostWithoutDetails::class.java)
            .find(PostWithoutDetails::isSponsored eq true)
            .sort(descending(PostWithoutDetails::date))
            .limit(2)
            .toList()
    }

    override suspend fun readPopularPosts(skip: Int): List<PostWithoutDetails> {
        return postCollection
            .withDocumentClass(PostWithoutDetails::class.java)
            .find(PostWithoutDetails::isPopular eq true)
            .sort(descending(PostWithoutDetails::date))
            .skip(skip)
            .limit(POSTS_PER_PAGE)
            .toList()
    }

    override suspend fun deleteSelectedPosts(ids: List<String>): Boolean {
        return postCollection
            .deleteMany(Post::id `in` ids)
            .awaitLast()
            .wasAcknowledged()
    }

    override suspend fun searchPostsByTitle(query: String, skip: Int): List<PostWithoutDetails> {
        val regexQuery = query.toRegex(RegexOption.IGNORE_CASE)
        return postCollection
            .withDocumentClass(PostWithoutDetails::class.java)
            .find(PostWithoutDetails::title regex regexQuery)
            .sort(descending(PostWithoutDetails::date))
            .skip(skip)
            .limit(POSTS_PER_PAGE)
            .toList()
    }

    override suspend fun readSelectedPost(id: String): Post {
        return postCollection.find(Post::id eq id).toList().first()
    }

    override suspend fun updatePost(post: Post): Boolean {
        return postCollection
            .updateOne(
                Post::id eq post.id,
                mutableListOf(
                    setValue(Post::title, post.title),
                    setValue(Post::subtitle, post.subtitle),
                    setValue(Post::category, post.category),
                    setValue(Post::thumbnail, post.thumbnail),
                    setValue(Post::content, post.content),
                    setValue(Post::isMain, post.isMain),
                    setValue(Post::isPopular, post.isPopular),
                    setValue(Post::isSponsored, post.isSponsored)
                )
            )
            .awaitLast()
            .wasAcknowledged()
    }
}
