package com.hypheno.blog.api

import com.hypheno.blog.data.MongoDB
import com.hypheno.blog.models.ApiListResponse
import com.hypheno.blog.models.ApiResponse
import com.hypheno.blog.models.Constants.AUTHOR_PARAM
import com.hypheno.blog.models.Constants.POST_ID_PARAM
import com.hypheno.blog.models.Constants.QUERY_PARAM
import com.hypheno.blog.models.Constants.SKIP_PARAM
import com.hypheno.blog.models.Post
import com.varabyte.kobweb.api.Api
import com.varabyte.kobweb.api.ApiContext
import com.varabyte.kobweb.api.data.getValue
import com.varabyte.kobweb.api.http.Request
import com.varabyte.kobweb.api.http.Response
import com.varabyte.kobweb.api.http.setBodyText
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.litote.kmongo.id.ObjectIdGenerator

@Api(routeOverride = "addpost")
suspend fun addPost(context: ApiContext) {
    try {
        val post = context.req.getBody<Post>()
        val newPost =
            post?.copy(id = ObjectIdGenerator.newObjectId<String>().id.toHexString())
        context.res.setBody(newPost?.let {
            context.data.getValue<MongoDB>().addPost(it)
        })
    } catch (e: Exception) {
        context.res.setBody(e.message)
    }
}

@Api(routeOverride = "readmyposts")
suspend fun readMyPosts(context: ApiContext) {
    try {
        val skip = context.req.params[SKIP_PARAM]?.toInt() ?: 0
        val author = context.req.params[AUTHOR_PARAM] ?: ""
        val myPosts = context.data.getValue<MongoDB>().readMyPosts(
            skip = skip,
            author = author
        )
        context.res.setBody(ApiListResponse.Success(data = myPosts))
    } catch (e: Exception) {
        context.res.setBody(ApiListResponse.Error(message = e.message.toString()))
    }
}

@Api(routeOverride = "deleteselectedposts")
suspend fun deleteSelectedPosts(context: ApiContext) {
    try {
        val request = context.req.getBody<List<String>>()
        context.res.setBody(request?.let {
            context.data.getValue<MongoDB>().deleteSelectedPosts(ids = it)
        })
    } catch (e: Exception) {
        context.res.setBody(e.message)
    }
}

@Api(routeOverride = "searchposts")
suspend fun searchPostsByTitle(context: ApiContext) {
    try {
        val query = context.req.params[QUERY_PARAM] ?: ""
        val skip = context.req.params[SKIP_PARAM]?.toInt() ?: 0
        val posts = context.data.getValue<MongoDB>().searchPostsByTitle(
            query = query,
            skip = skip
        )
        context.res.setBody(ApiListResponse.Success(data = posts))
    } catch (e: Exception) {
        context.res.setBody(ApiListResponse.Error(message = e.message.toString()))
    }
}

@Api(routeOverride = "readselectedpost")
suspend fun readSelectedPost(context: ApiContext) {
    val postId = context.req.params[POST_ID_PARAM]
    if (!postId.isNullOrEmpty()) {
        try {
            val selectedPost =
                context.data.getValue<MongoDB>().readSelectedPost(id = postId)
            context.res.setBody(ApiResponse.Success(data = selectedPost))
        } catch (e: Exception) {
            context.res.setBody(ApiResponse.Error(message = e.message.toString()))
        }
    } else {
        context.res.setBody(ApiResponse.Error(message = "Selected Post does not exist."))
    }
}

@Api(routeOverride = "updatepost")
suspend fun updatePost(context: ApiContext) {
    try {
        val updatedPost = context.req.getBody<Post>()
        context.res.setBody(
            updatedPost?.let {
                context.data.getValue<MongoDB>().updatePost(it)
            }
        )
    } catch (e: Exception) {
        context.res.setBody(e.message)
    }
}

inline fun <reified T> Response.setBody(data: T) {
    setBodyText(Json.encodeToString(data))
}

inline fun <reified T> Request.getBody(): T? {
    return body?.decodeToString()?.let { return Json.decodeFromString(it) }
}
