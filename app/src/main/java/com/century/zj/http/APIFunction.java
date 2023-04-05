package com.century.zj.http;


import com.century.zj.entity.BaseEntity;
import com.century.zj.entity.CollectAttraction;
import com.century.zj.entity.Comment;
import com.century.zj.entity.EventEntity;
import com.century.zj.entity.LiteratureEntity;
import com.century.zj.entity.NoteEntity;
import com.century.zj.entity.PersonEntity;
import com.century.zj.entity.PlaceEntity;
import com.century.zj.entity.Rank_Form;
import com.century.zj.entity.ReadEntity;
import com.century.zj.entity.Register;
import com.century.zj.entity.Scenery;
import com.century.zj.entity.Talk_Form;
import com.century.zj.entity.UpdateCountEntity;
import com.century.zj.entity.User;
import com.century.zj.entity.User_Form;
import com.century.zj.entity.VideoEntity;
import com.century.zj.entity.VoiceEntity;
import com.century.zj.entity.Weather;
import com.century.zj.entity.WorkEntity;
import com.century.zj.http.config.URLConfig;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author yemao
 * @date 2017/4/9
 * @description API接口!
 */

public interface APIFunction {
    //登录接口
    @POST(URLConfig.LOGIN)
    Observable<BaseEntity<User_Form>> login(@Body User user);
    //注册接口
    @POST(URLConfig.REGISTER)
    Observable<BaseEntity<String>> register(@Body Register register);

    //语音ai接口
    @GET(URLConfig.VOICE)
    Observable<VoiceEntity> getVoice(@Query("content") String content);

    //note
    @POST(URLConfig.POST_NOTE)
    Observable<BaseEntity<NoteEntity>> postNote(@Body NoteEntity noteEntity);
    @GET(URLConfig.GET_NOTE)
    Observable<BaseEntity<List<NoteEntity>>> getNote(@Query("userphone") String userphone);


    //read
    @POST(URLConfig.POST_READ)
    Observable<BaseEntity<String>> updateUserRead(@Body ReadEntity readEntity);

    //景点收藏
    @POST(URLConfig.COLLECT_ATTRACTION)
    Observable<BaseEntity<CollectAttraction>> collectAttraction(@Body CollectAttraction collectAttraction);
    @GET(URLConfig.COLLECT_GET)
    Observable<BaseEntity<List<CollectAttraction>>> collectGet(@Query("userphone") String userphone);

    //所有红色事件列表
    @GET(URLConfig.EVENT_LIST_ALL)
    Observable<BaseEntity<List<EventEntity>>> getEventListAll();
    //单个红色事件
    @GET(URLConfig.EVENT_LIST_BY_ID)
    Observable<BaseEntity<EventEntity>> getEventListById(@Query("id") int id,@Query("userphone") String userphone );
    //分页
    @GET(URLConfig.EVENT_LIST_BY_PAGE)
    Observable<BaseEntity<List<EventEntity>>> getEventListByPage(@Query("page") int page);
    //红色事件列表
    @GET("event/getEventByTitle")
    Observable<BaseEntity<List<EventEntity>>> getTitle(@Query("title") String title);

    //所有红色视频列表
    @GET(URLConfig.VIDEO_LIST_ALL)
    Observable<BaseEntity<List<VideoEntity>>> getVideoListAll();
    //单个红色视频
    @GET(URLConfig.VIDEO_LIST_BY_ID)
    Observable<BaseEntity<VideoEntity>> getVideoListById(@Query("id") int id);
    //分页
    @GET(URLConfig.VIDEO_LIST_BY_PAGE)
    Observable<BaseEntity<List<VideoEntity>>> getVideoListByPage(@Query("page") int page);

    //更新点赞,收藏,评论
    @POST(URLConfig.VIDEO_UPDATE_COUNT)
    Observable<BaseEntity<String>> updateCount(@Body UpdateCountEntity updateCountEntity);

    //所有红色文献列表
    @GET(URLConfig.LITERATURE_LIST_ALL)
    Observable<BaseEntity<List<LiteratureEntity>>> getLiteratureListAll();
    //单个红色文献
    @GET(URLConfig.LITERATURE_LIST_BY_ID)
    Observable<BaseEntity<LiteratureEntity>> getLiteratureListById(@Query("id") int id);
    //分页
    @GET(URLConfig.LITERATURE_LIST_BY_PAGE)
    Observable<BaseEntity<List<LiteratureEntity>>> getLiteratureListByPage(@Query("page") int page);

    //所有红色人物列表
    @GET(URLConfig.PERSON_LIST_ALL)
    Observable<BaseEntity<List<PersonEntity>>> getPersonListAll();
    //单个红色人物
    @GET(URLConfig.PERSON_LIST_BY_ID)
    Observable<BaseEntity<PersonEntity>> getPersonListById(@Query("id") int id);
    //分页
    @GET(URLConfig.PERSON_LIST_BY_PAGE)
    Observable<BaseEntity<List<PersonEntity>>> getPersonListByPage(@Query("page") int page);

    //所有红色作品列表
    @GET(URLConfig.WORK_LIST_ALL)
    Observable<BaseEntity<List<WorkEntity>>> getWorkListAll();
    //单个红色作品
    @GET(URLConfig.WORK_LIST_BY_ID)
    Observable<BaseEntity<WorkEntity>> getWorkListById(@Query("id") int id);
    //分页
    @GET(URLConfig.WORK_LIST_BY_PAGE)
    Observable<BaseEntity<List<WorkEntity>>> getWorkListByPage(@Query("page") int page);

    //带@Path参数的GET请求（URL带参数）/会变成%，加上encoded = true
    @GET("{road}")
    Observable<BaseEntity<List<PlaceEntity>>> getPlaceList(@Path(value = "road", encoded = true) String road);
    //相当于常规的写法：http://192.168.11.112/api/{username}

    //获取用户排名的接口
    @GET(URLConfig.GAME_RANK_LIST)
    Observable<BaseEntity<List<Rank_Form>>> getRankList();
    //论坛上传
    @Multipart
    @POST(URLConfig.FORUM_TALK_DOWN)
    Observable<Object> downForum(@Query("userphone") String s, @Part ("title") String title, @Part("article") String article, @Part List<MultipartBody.Part> file);
    //论坛获取
    @GET(URLConfig.FORUM_TALK_UP)
    Observable<BaseEntity<List<Talk_Form>>> upForum();
    //获取自己的发布
    @GET("forum/getForumItemListByUserPhone")
    Observable<BaseEntity<List<Talk_Form>>> getMineForum(@Query("userphone") String phone);
    //论坛点赞
    @POST("forum/updateForumByUserPhone")
    Observable<Object> DZ(@Query("dzflag") int dz,@Query("id") int id,@Query("userphone") String root);

    //拼图
    @POST("user/updateUserLevelById")
    Observable<Object> postPuzzleGame(@Query("id") int id,@Query("level") int level);

    //答题积分
    @POST("updateUserReplyCodeById")
    Observable<Object> postReplyCode(@Query("id") int id,@Query("replycode") int code);


    /**
     * 用户数据
     */
    //上传多张图片
    @POST("user/updateUserDZCountByUserPhone")
    Observable<Object> postDZ(@Query("dzcount") int dz,@Query("userphone") String root);

    //根据cityid获取城市的天气情况
    @GET("https://www.tianqiapi.com/free/day?appid=26726571&appsecret=n1l1nOnI&unescape=1")
    Observable<Weather> getWeather(@Query("cityid") String cityid);

    @GET("scenery/getSceneryByTitle")
    Observable<BaseEntity<Scenery>> getScenery(@Query("title")String title);


    //获取评论接口
    @GET(URLConfig.GETCOMMENT)
    Observable<BaseEntity<List<Comment>>> getComment(@Query("title")String title);

    @POST(URLConfig.POSTCOMMENT)
    Observable<BaseEntity<String>> postComment(@Body Comment comment);
}
