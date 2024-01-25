package cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state;

/**
 * @author bitkylin
 */

import com.google.common.collect.Lists;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@State(
        name = "cc.bitky.jetbrains.plugin.universalgenerate.config.setting.state.globalSettingsState",
        storages = @Storage("bitkylinUniversalGenerateSettings.xml")
)
@Getter
@Setter
public class GlobalSettingsState implements PersistentStateComponent<GlobalSettingsState> {

    public static final int DEFAULT_PROTOSTUFF_TAG_START_VALUE = 101;
    public static final int DEFAULT_PROTOSTUFF_TAG_SCOPE_INTERVAL = 100;

    private String language = LanguageEnum.ENGLISH.name();

    private List<String> annotationAffectedList = Lists.newArrayList(
            AnnotationAffectedEnum.SWAGGER.name(),
            AnnotationAffectedEnum.PROTOSTUFF.name()
    );

    private Boolean contextMenuShowed = true;

    private Boolean intentionActionEnabled = true;

    private String protostuffTagAssign = ProtostuffTagAssignEnum.NON_REPEATABLE.name();

    private Integer protostuffTagStartValue = DEFAULT_PROTOSTUFF_TAG_START_VALUE;

    private Integer protostuffTagScopeInterval = DEFAULT_PROTOSTUFF_TAG_SCOPE_INTERVAL;

    @Override
    public GlobalSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(GlobalSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }


    public enum LanguageEnum {

        /**
         * 英文
         */
        ENGLISH,

        /**
         * 中文
         */
        CHINESE,
        ;
    }

    public enum AnnotationAffectedEnum {

        /**
         * swagger
         */
        SWAGGER,

        /**
         * protostuff
         */
        PROTOSTUFF,
        ;
    }

    public enum ProtostuffTagAssignEnum {

        /**
         * 不重复的
         */
        NON_REPEATABLE,

        /**
         * 从初始值开始
         */
        FROM_START_VALUE,
        ;
    }

}