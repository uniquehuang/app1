package com.century.zj.http.config;

/**
 * @author yemao
 * @date 2017/4/9
 * @description 网络接口地址!
 */

public interface URLConfig {


    String LOGIN = "user/login"; //登录
    String REGISTER = "user/addUser";//注册
    String VOICE ="https://reptile.akeyn.com/voice/text2audio/";//语音ai接口
    String POST_NOTE ="note/addNote";//笔记post
    String GET_NOTE ="note/getNoteListByUserPhone";//笔记get

    String POST_READ ="user/updateUserReadByUserPhone";//read_post


    String VIDEO_LIST_ALL = "video/getVideos";//所有红色视频列表
    String VIDEO_LIST_BY_ID = "video/getVideoById";//单个红色视频
    String VIDEO_LIST_BY_PAGE = "video/getVideosByPage";//页数
    String VIDEO_UPDATE_COUNT = "video/updateVideoById";//更新点赞,收藏,评论

    String EVENT_LIST_ALL = "event/getEvents";//所有红色事件列表
    String EVENT_LIST_BY_ID = "event/getEventById";//单个红色事件
    String EVENT_LIST_BY_PAGE = "event/getEventsByPage";//页数

    String LITERATURE_LIST_ALL = "literature/getLiteratures";//所有红色文献列表
    String LITERATURE_LIST_BY_ID = "literature/getLiteratureById";//单个红色文献
    String LITERATURE_LIST_BY_PAGE = "literature/getLiteraturesByPage";//页数

    String PERSON_LIST_ALL = "person/getPersons";//所有红色人物列表
    String PERSON_LIST_BY_ID = "person/getPersonById";//单个红色人物
    String PERSON_LIST_BY_PAGE = "person/getPersonsByPage";//页数

    String WORK_LIST_ALL = "work/getWorks";//所有红色作品列表
    String WORK_LIST_BY_ID = "work/getWorkById";//单个红色作品
    String WORK_LIST_BY_PAGE = "work/getWorksByPage";//页数


    String GAME_RANK_LIST="user/getUserCodeList";//获取用户排名列表
    String FORUM_TALK_DOWN="forum/multiUpload";//上传论坛
    String FORUM_TALK_UP="forum/getForumItemList";//上传论坛


    String COLLECT_ATTRACTION="collect/addAttraction";//景点收藏
    String COLLECT_GET="collect/getAttractionByUserPhone";//景点收藏
    String FIRSTPLACE_LIST_ALL = "firstroad/getFirstRoad";//获取第一条路的坐标
    String SECONDPLACE_LIST_ALL = "secondroad/getSecondRoad";//获取第二条路的坐标
    String THIRDPLACE_LIST_ALL = "thirdroad/getThirdRoad";//获取第三条路的坐标
    String FOURTHPLACE_LIST_ALL = "fourthroad/getFourthRoad";//获取第四条路的坐标


    String GETCOMMENT ="comment/getComment";//获取评论接口
    String POSTCOMMENT="comment/addComment";//评论上传接口
}
