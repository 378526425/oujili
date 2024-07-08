package com.wxmblog.nostalgia.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wxmblog.base.common.entity.BaseEntity;
import com.wxmblog.base.common.enums.FrUserStatusEnum;
import com.wxmblog.base.common.enums.GenderEnum;
import com.wxmblog.nostalgia.common.enums.user.*;
import com.wxmblog.nostalgia.common.handle.AdditionalHandler;
import com.wxmblog.nostalgia.common.handle.CharacterTypeHandler;
import com.wxmblog.nostalgia.common.handle.UserImageListTypeHandler;
import com.wxmblog.nostalgia.common.rest.response.front.fruser.AdditionalResponse;
import com.wxmblog.nostalgia.common.rest.response.front.fruser.CharacterTypeResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * 前台用户
 *
 * @author wanglei
 * @email 378526425@qq.com
 * @date 2022-12-01 20:27:09
 */
@Data
@TableName(value = "fr_user", autoResultMap = true)
public class FrUserEntity extends BaseEntity {

    /**
     * 登陆账号
     */
    @TableField("open_id")
    private String openId;
    /**
     * 用户状态 启用/停用/注销
     */
    @TableField("status")
    private FrUserStatusEnum status;
    /**
     * 头像
     */
    @TableField("head_portrait")
    private String headPortrait;
    /**
     * 昵称
     */
    @TableField("nick_name")
    private String nickName;
    /**
     * 出生日期
     */
    @TableField("birthday")
    private Date birthday;
    /**
     * 身高
     */
    @TableField("height")
    private Integer height;
    /**
     * 职业
     */
    @TableField("profession")
    private String profession;
    /**
     * 性别
     */
    @TableField("gender")
    private GenderEnum gender;
    /**
     * 毕业学校
     */
    @TableField("school")
    private String school;
    /**
     * 最高学历
     */
    @TableField("education")
    private HighestEducationEnum education;

    /**
     * @Description: 学历类型
     */
    @TableField("educational_type")
    private EducationalTypeEnum educationalType;

    /**
     * 关于我
     */
    @TableField("about_me")
    private String aboutMe;
    /**
     * 兴趣爱好
     */
    @TableField("interest")
    private String interest;
    /**
     * 择偶要求
     */
    @TableField("love_requirement")
    private String loveRequirement;

    /**
     * 经度
     */
    @TableField("lon")
    private String lon;

    /**
     * 纬度
     */
    @TableField("lat")
    private String lat;

    /**
     * 城市
     */
    @TableField("city")
    private String city;
    /**
     * 婚姻状况
     */
    @TableField("marriage")
    private MaritalStatusEnum marriage;
    /**
     * 脱单目标
     */
    @TableField("love_goal")
    private LoveGoalEnum loveGoal;
    /**
     * 感情状况
     */
    @TableField("emotional")
    private EmotionalStatus emotional;
    /**
     * 年薪
     */
    @TableField("annual_salary")
    private AnnualSalaryEnum annualSalary;

    /**
     * 微信号
     */
    @TableField("we_chat_number")
    private String weChatNumber;
    /**
     * 相册
     */
    @TableField(value = "img_list", typeHandler = UserImageListTypeHandler.class)
    private List<String> imgList;

    /**
     * 用户资料状态
     */
    @TableField("auth_status")
    private AuthStatusEnum authStatus;
    /**
     * 待审核相册
     */
    @TableField(value = "wait_approved_img", typeHandler = UserImageListTypeHandler.class)
    private List<String> waitApprovedImg;
    /**
     * 金币余额
     */
    @TableField("gold_balance")
    private Integer goldBalance;
    /**
     * 用户类型
     */
    @TableField("user_type")
    private UserTypeEnum userType;

    @TableField("session_key")
    private String sessionKey;

    @TableField("union_id")
    private String unionId;

    @TableField(value = "character_type", typeHandler = CharacterTypeHandler.class)
    private CharacterTypeResponse characterType;

    @TableField("lately_time")
    private Date latelyTime;

    @TableField(value = "additional", typeHandler = AdditionalHandler.class)
    private AdditionalResponse additional;
}
