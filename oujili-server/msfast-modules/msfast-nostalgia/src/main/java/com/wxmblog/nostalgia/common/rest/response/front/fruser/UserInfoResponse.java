package com.wxmblog.nostalgia.common.rest.response.front.fruser;

import com.wxmblog.base.common.enums.GenderEnum;
import com.wxmblog.nostalgia.common.enums.user.*;
import com.wxmblog.nostalgia.common.rest.response.front.article.UserArticleViewResponse;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: wxm-fast
 * @description:
 * @author: Mr.Wang
 * @create: 2023-01-05 16:05
 **/

@Data
public class UserInfoResponse {

    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "资料认证状态 EXAMINE(\"审核中\"),\n" +
            "    PASS(\"通过\"),\n" +
            "    REFUSE(\"拒绝\")")
    private AuthStatusEnum authStatus;

    @ApiModelProperty(value = "身份认证  EXAMINE(\"审核中\"),\n" +
            "    PASS(\"通过\"),\n" +
            "    REFUSE(\"拒绝\")")
    private AuthStatusEnum identityAuth;

    @ApiModelProperty(value = "学历认证  EXAMINE(\"审核中\"),\n" +
            "    PASS(\"通过\"),\n" +
            "    REFUSE(\"拒绝\")")
    private AuthStatusEnum educationAuth;

    /**
     * 毕业学校
     */
    @ApiModelProperty(value = "毕业学校")
    private String school;
    /**
     * 最高学历
     */
    @ApiModelProperty(value = "最高学历 Specialty(\"专科\"),\n" +
            "    Undergraduate(\"本科\"),\n" +
            "    Master(\"硕士\"),\n" +
            "    Doctor(\"博士\"")
    private HighestEducationEnum education;

    /**
     * @Description: 学历类型
     */
    @ApiModelProperty(value = "学历类型 FullTime(\"全日制\"),\n" +
            "    NOFullTime(\"非全日制\")")
    private EducationalTypeEnum educationalType;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    /**
     * 身高
     */
    @ApiModelProperty(value = "身高")
    private Integer height;

    @ApiModelProperty(value = "星座")
    private String constellation;

    /**
     * 职业
     */
    @ApiModelProperty(value = "职业")
    private String profession;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别 MALE(\"男\"),\n" +
            "    FEMALE(\"女\")")
    private GenderEnum gender;


    /**
     * 关于我
     */
    @ApiModelProperty(value = "关于我")
    private String aboutMe;

    /**
     * 兴趣爱好
     */
    @ApiModelProperty(value = "兴趣爱好")
    private String interest;

    /**
     * 择偶要求
     */
    @ApiModelProperty(value = "心议的他")
    private String loveRequirement;


    /**
     * 城市
     */
    @ApiModelProperty(value = "城市")
    private String city;

    /**
     * 婚姻状况
     */
    @ApiModelProperty(value = "婚姻状况 Unmarried(\"未婚\"),\n" +
            "    Divorce(\"离异\"),\n" +
            "    Widow(\"丧偶\")")
    private MaritalStatusEnum marriage;

    /**
     * 感情状况
     */
    @ApiModelProperty(value = "感情状况 Single(\"单身\"),\n" +
            "    Love(\"恋爱\")")
    private EmotionalStatus emotional;

    /**
     * 年薪
     */
    @ApiModelProperty(value = "年薪 Less_Ten(\"小于10w\"),\n" +
            "    Ten_Two(\"10w到20w\"),\n" +
            "    Two_Three(\"20w到30w\"),\n" +
            "    Three_Five(\"30w到50w\"),\n" +
            "    Five_Hundred(\"50w到100w\"),\n" +
            "    Greater_Hundred(\"大于100w\"),\n" +
            "    Secrecy(\"保密\")")
    private AnnualSalaryEnum annualSalary;


    @ApiModelProperty(value = "相册")
    private List<String> imgList;

    @ApiModelProperty(value = "性格测试")
    private CharacterTypeResponse characterType;

    @ApiModelProperty(value = "匹配状态 SUCCESS(\"匹配成功\"),\n" +
            "    LIKE_ME(\"喜欢我的\"),\n" +
            "    LIKE(\"我喜欢的\"),\n" +
            "    NOT_LIKE(\"我不喜欢\"),\n" +
            "    NOT_LIKE_ME(\"对我无感\"),\n" +
            "    NULL(\"未操作\")")
    private MatchingStatusEnum matchingStatus;

    @ApiModelProperty(value = "朋友圈概览信息")
    private UserArticleViewResponse userArticleViewResponse;
}
