package cc.bitky.jetbrains.plugin.universalgenerate.constants;

import lombok.Getter;

/**
 * @author bitkylin
 */
public enum LocalizationEnum {

    TEST("Test", "测试"),

    // region --------- 通知 -----------

    NOTIFICATION_TITLE("Execution Exception", "执行发生异常"),

    // endregion

    // region --------- UI -----------

    SETTING_DISPLAY_NAME("Bitkylin Universal Generate", "Bitkylin 通用生成"),

    LABEL_LANGUAGE("language", "语言"),

    RADIO_BUTTON_LANGUAGE_ENGLISH("English", "English"),

    RADIO_BUTTON_LANGUAGE_CHINESE("Chinese", "中文"),

    LABEL_SCOPE_OF_EFFECT("Scope of effect", "作用范围"),

    CHECK_BOX_SWAGGER_EFFECTED("Swagger", "Swagger"),

    CHECK_BOX_PROTOSTUFF_EFFECTED("Protostuff", "Protostuff"),

    LABEL_CONTEXT_MENU("Right-Click Menu", "右键菜单"),

    CHECK_BOX_SHOWED("Showed", "显示"),


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
    ELEMENT_NAME_TO_JAVA_DOC("Search JavaDoc", "查找JavaDoc"),
    ANNOTATION_TO_JAVA_DOC("Annotation to JavaDoc", "注解转JavaDoc"),
    GENERATE_ENTRY_ANNOTATION("Generate Entry Annotation", "生成入口注解"),
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
}
