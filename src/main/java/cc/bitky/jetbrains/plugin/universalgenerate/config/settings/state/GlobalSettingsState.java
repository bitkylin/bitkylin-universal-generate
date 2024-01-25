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

    private String language = "ENGLISH";
    private String protostuffTagAssign = "IN_TURN";

    private List<String> annotationAffectedList = Lists.newArrayList(
            AnnotationAffectedEnum.SWAGGER.name(),
            AnnotationAffectedEnum.PROTOSTUFF.name()
    );

    private Boolean contextMenuShowed = true;

    private Boolean intentionActionEnabled = true;

    @Override
    public GlobalSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(GlobalSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }


    public enum LanguageEnum {
        ENGLISH,
        CHINESE,
        ;
    }

    public enum AnnotationAffectedEnum {
        SWAGGER,
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