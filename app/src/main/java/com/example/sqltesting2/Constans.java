package com.example.sqltesting2;

import com.example.sqltesting2.Pojo.User;

import java.util.List;

public class Constans {

    public static List<User> LIST_RESPONSE = null;
   /* public static List<Post> LIST_RESPONSE_POSTS = null;
    public static List<Comment> LIST_RESPONSE_COMMENTS = null;
    public static List<Album> LIST_RESPONSE_ALBUMS = null;
    public static List<Photo> LIST_RESPONSE_PHOTOS = null;*/


    public static final String KEY_USER_ID = "id";
    public static final String KEY_USER_NAME = "name";
    public static final String KEY_USER_NICK = "username";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ADDRESS = "address";
    public static final String KEY_STREET = "street";
    public static final String KEY_SUITE= "suite";
    public static final String KEY_CITY = "city";
    public static final String KEY_ZIPCODE = "zipcode";
    public static final String KEY_COMPANY = "company";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_WEBSITE = "website";
    public static final String KEY_COMPANY_NAME = "name";
    public static final String KEY_PHRASE = "catchPhrase";
    public static final String KEY_BS = "bs";


    public static final String KEY_USER_ID_POST = "userId";
    public static final String KEY_ID_POST = "id";
    public static final String KEY_TITLE_POST = "title";
    public static final String KEY_BODY_POST = "body";


    public static final String KEY_POST_ID = "postId";
    public static final String KEY_COMMENT_ID = "id";
    public static final String KEY_COMMENT_NAME = "name";
    public static final String KEY_COMMENTATOR_EMAIL = "email";
    public static final String KEY_COMMENT_BODY = "body";


    public static final String KEY_USER_ID_ALBUMS = "userId";
    public static final String KEY_ALBUMS_ID = "id";
    public static final String KEY_ALBUMS_TITLE = "title";

    public static final String KEY_ALBUMS_ID_PHOTO = "albumId";
    public static final String KEY_ID_PHOTO = "id";
    public static final String KEY_TITLE_PHOTO = "title";
    public static final String KEY_URL_PHOTO = "url";

    public static class URL {
        private static final String HOST = "https://jsonplaceholder.typicode.com/";
        public static final String GET_USERS = HOST + "users";
        public static final String GET_USER_POSTS = HOST + "posts";
        public static final String GET_POSTS_COMMENTS = HOST + "comments";
        public static final String GET_USERS_ALBUMS = HOST + "albums";
        public static final String GET_ALBUMS_PHOTOS = HOST + "photos";
    }
}