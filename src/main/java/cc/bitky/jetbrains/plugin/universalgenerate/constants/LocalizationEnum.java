package cc.bitky.jetbrains.plugin.universalgenerate.constants;

import lombok.Getter;

/**
 * @author bitkylin
 */
public enum LocalizationEnum {

    /**
     * localizationEnum
     */
    TEST("Test", "测试"),

    // region --------- 通知 -----------

    NOTIFICATION_TITLE("Execution Exception", "执行发生异常"),

    // endregion

    // region --------- UI -----------

    // region --------- Global -----------

    SETTING_DISPLAY_NAME("Bitkylin Universal Generate", "Bitkylin 通用生成"),

    LABEL_LANGUAGE("language", "语言"),

    RADIO_BUTTON_LANGUAGE_ENGLISH("English", "English"),

    RADIO_BUTTON_LANGUAGE_CHINESE("Chinese", "中文"),

    LABEL_SCOPE_OF_EFFECT("Scope of effect", "作用范围"),

    CHECK_BOX_SWAGGER_EFFECTED("Swagger", "Swagger"),

    CHECK_BOX_PROTOSTUFF_EFFECTED("Protostuff", "Protostuff"),

    LABEL_RIGHT_CLICK_MENU("Right-Click Menu", "右键菜单"),

    RIGHT_CLICK_ENABLED("Enabled", "启用"),

    LABEL_INTENTION_ACTION("Intention Action", "Intention Action"),

    INTENTION_ACTION_ENABLED("Enabled", "启用"),

    // endregion

    // region --------- Protostuff tab -----------

    LABEL_PROTOSTUFF_TAG_ASSIGN("Tag assign strategy", "Tag分配策略"),
    LABEL_PROTOSTUFF_TAG_ASSIGN_TOOL_TIP("Define the sorting mode of Tag Value", "指定Tag Value的排序模式"),
    RADIO_BUTTON_PROTOSTUFF_TAG_ASSIGN_NON_REPEATABLE("Non-repeatable", "不重复"),
    RADIO_BUTTON_PROTOSTUFF_TAG_ASSIGN_NON_REPEATABLE_TOOL_TIP(
            "When there are multiple scopes in a file, the tag values are sorted in overall order and are not duplicated.",
            "当文件中存在多个作用域时，tag值从整体上顺次排序，并且不会重复"),
    RADIO_BUTTON_PROTOSTUFF_TAG_ASSIGN_FROM_START_VALUE("From start value", "从起始值开始"),
    RADIO_BUTTON_PROTOSTUFF_TAG_ASSIGN_FROM_START_VALUE_TOOL_TIP(
            "The tag values in each scope are sorted from the start value.",
            "每个作用域中tag值均从起始值开始排序"),

    LABEL_PROTOSTUFF_TAG_START_VALUE("Start value", "起始值"),
    LABEL_PROTOSTUFF_TAG_START_VALUE_TOOL_TIP("Initial value of Tag Value", "Tag Value的初始值"),
    LABEL_PROTOSTUFF_TAG_SCOPE_INTERVAL("Scope interval", "作用域间隔"),
    LABEL_PROTOSTUFF_TAG_SCOPE_INTERVAL_TOOL_TIP(
            "When there are multiple scopes in a file, set the interval of tag values between scopes when the tag values are sorted as a whole.",
            "当文件中存在多个作用域时，tag值从整体上顺次排序时，设置作用域之间tag value的间隔"),

    // endregion

    // region --------- field -> Swagger tab -----------

    LABEL_SWAGGER_TAG_STAY_TUNED("Stay tuned!", "敬请期待！"),

    // endregion

    // endregion

    // region --------- 异常描述 -----------

    CLASS_NOT_FOUND("The specified class could not be found : %s", "找不到指定的类 : %s"),
    ANNOTATION_TAG_ERROR("Incorrect information about @Tag annotations", "@Tag注解信息有误"),
    CLASS_ROLE_UNSUPPORTED("Class roles are not supported", "类的角色不被支持"),
    COMMAND_SCOPE_UNSUPPORTED("The current command scope is not supported", "当前的指令作用范围不被支持"),
    ELEMENT_NOT_SELECT("No element selected, command not executed", "未选择任何元素，指令未执行"),

    // endregion

    // region --------- Action & Action Group -----------

    // region --------- Action Group -----------

    EMPTY("empty space", "空白的"),
    GLOBAL_UNIVERSAL_GENERATE("Universal Generate", "通用生成"),
    SEARCH_ELEMENT_NAME_TO_JAVA_DOC("Search JavaDoc", "查找JavaDoc"),
    ANNOTATION_TO_JAVA_DOC("Annotation to JavaDoc", "注解转JavaDoc"),
    GENERATE_ENTRY_ANNOTATION("Generate Annotation", "生成注解"),
    DELETE_ELEMENT("Delete Element", "删除元素"),
    FULL_DOCUMENT("Full Document", "整个文档"),
    CURRENT_ELEMENT("Current Element", "当前元素"),
    CURRENT_CLASS_NAME("Current ClassName", "当前类名"),
    CURRENT_FIELD("Current Field", "当前字段"),
    CURRENT_METHOD("Current Method", "当前方法"),
    NOT_SUPPORT("Not Support", "不支持"),
    DUMB_MODE("Dumb Mode", "哑模式"),

    // endregion

    // region --------- Action -----------

    RE_GENERATE_TO_JAVA_DOC("Re-Generate to JavaDoc", "合并到JavaDoc"),
    POPULATE_MISSING_ANNOTATION("Populate Missing Annotation", "填充缺失的注解"),
    POPULATE_MISSING_JAVA_DOC("Populate Missing JavaDoc", "填充缺失的JavaDoc"),
    RE_GENERATE_ANNOTATION("Re-Generate Annotation", "重新生成注解"),
    POPULATE_ELEMENT_NAME_TO_JAVA_DOC("Populate Missing JavaDoc", "填充缺失的JavaDoc"),
    RE_GENERATE_ELEMENT_NAME_TO_JAVA_DOC("Re-Generate JavaDoc", "重新生成JavaDoc"),


    DELETE_JAVA_DOC("Delete JavaDoc", "删除JavaDoc"),
    DELETE_ANNOTATION_SWAGGER("Delete Swagger Annotation", "删除Swagger注解"),
    DELETE_ANNOTATION_TAG("Delete Tag Annotation", "删除Tag注解"),
    DELETE_ELEMENT_ALL("Delete All", "删除全部元素"),
    // endregion

    // region --------- Action -----------

    SEARCH_ELEMENT_NAME_TO_JAVA_DOC_INTENTION_FAMILY("Search JavaDoc"),
    ANNOTATION_TO_JAVA_DOC_INTENTION_FAMILY("Annotation to JavaDoc"),
    GENERATE_ENTRY_ANNOTATION_INTENTION_FAMILY("Generate Annotation"),

    // endregion

    // region --------- Action -----------

    ELEMENT_NAME_SUFFIX_LIST("list", "列表"),
    ELEMENT_NAME_SUFFIX_MAP("map", "Map"),
    ELEMENT_NAME_SUFFIX_SET("set", "set"),
    ELEMENT_NAME_SUFFIX_ARRAY("array", "数组"),
    ELEMENT_NAME_SUFFIX_S("", ""),

    // endregion

    // endregion

    ;

    @Getter
    private final String english;

    @Getter
    private final String chinese;

    LocalizationEnum(String english, String chinese) {
        this.english = english;
        this.chinese = chinese;
    }

    LocalizationEnum(String english) {
        this.english = english;
        this.chinese = english;
    }
}
