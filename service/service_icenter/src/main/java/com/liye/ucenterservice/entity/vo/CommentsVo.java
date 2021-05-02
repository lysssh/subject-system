package com.liye.ucenterservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author ly
 * @project_name subject-system
 * @package_name com.liye.ucenterservice.entity.vo
 * @create_time 2021/4/13 10:06
 */

@Data
public class CommentsVo {

    private String id;

    @ApiModelProperty(value = "评论用户Id")
    private String userCommentsId;

    @ApiModelProperty(value = "昵称")
    private String userCommentsNickname;

    @ApiModelProperty(value = "用户头像")
    private String Commentsavatar;

    @ApiModelProperty(value = "评论Id")
    private String commentsId;

    @ApiModelProperty(value = "回复用户Id")
    private String userReplyId;

    @ApiModelProperty(value = "回复用户昵称")
    private String userReplyNickName;

    @ApiModelProperty(value = "评论内容")
    private String content;

    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;
}
