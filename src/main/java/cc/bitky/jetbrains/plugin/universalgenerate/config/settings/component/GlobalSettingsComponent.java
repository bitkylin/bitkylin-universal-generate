package cc.bitky.jetbrains.plugin.universalgenerate.config.settings.component;

import cc.bitky.jetbrains.plugin.universalgenerate.config.settings.state.GlobalSettingsState;
import com.google.common.collect.Lists;
import lombok.Getter;

import javax.swing.*;
import java.util.List;

/**
 * @author bitkylin
 */

public class GlobalSettingsComponent {

    @Getter
    private JPanel mainPanel;

    private JLabel labelLanguage;

    @Getter
    private JRadioButton radioButtonLanguageEnglish;

    @Getter
    private JRadioButton radioButtonLanguageChinese;

    private JLabel labelScopeOfEffect;
    private JCheckBox checkBoxSwaggerAffected;
    private JCheckBox checkBoxProtostuffAffected;

    private JLabel labelRightClickMenu;
    private JCheckBox checkBoxRightClickMenuEnabled;
    private JLabel labelIntentionAction;
    private JCheckBox checkBoxIntentionActionEnabled;

    public GlobalSettingsState.LanguageEnum getLanguage() {
        if (radioButtonLanguageEnglish.isSelected()) {
            return GlobalSettingsState.LanguageEnum.ENGLISH;
        } else if (radioButtonLanguageChinese.isSelected()) {
            return GlobalSettingsState.LanguageEnum.CHINESE;
        } else {
            return GlobalSettingsState.LanguageEnum.ENGLISH;
        }
    }

    public void setLanguage(GlobalSettingsState.LanguageEnum language) {
        switch (language) {
            case ENGLISH -> {
                getRadioButtonLanguageEnglish().setSelected(true);
                getRadioButtonLanguageChinese().setSelected(false);
            }
            case CHINESE -> {
                getRadioButtonLanguageEnglish().setSelected(false);
                getRadioButtonLanguageChinese().setSelected(true);
            }
            default -> {
                getRadioButtonLanguageEnglish().setSelected(true);
                getRadioButtonLanguageChinese().setSelected(false);
            }
        }
    }

    public List<GlobalSettingsState.AnnotationAffectedEnum> getAnnotationAffectedList() {
        List<GlobalSettingsState.AnnotationAffectedEnum> list = Lists.newArrayList();
        if (checkBoxSwaggerAffected.isSelected()) {
            list.add(GlobalSettingsState.AnnotationAffectedEnum.SWAGGER);
        }
        if (checkBoxProtostuffAffected.isSelected()) {
            list.add(GlobalSettingsState.AnnotationAffectedEnum.PROTOSTUFF);
        }
        return list;
    }

    public void setAnnotationAffectedList(List<GlobalSettingsState.AnnotationAffectedEnum> annotationAffectedList) {
        checkBoxSwaggerAffected.setSelected(false);
        checkBoxProtostuffAffected.setSelected(false);
        for (GlobalSettingsState.AnnotationAffectedEnum annotationAffectedEnum : annotationAffectedList) {
            switch (annotationAffectedEnum) {
                case SWAGGER -> checkBoxSwaggerAffected.setSelected(true);
                case PROTOSTUFF -> checkBoxProtostuffAffected.setSelected(true);
                default -> {
                }
            }
        }
    }

    public void setRightClickMenuEnabled(boolean contextMenuShowed) {
        checkBoxRightClickMenuEnabled.setSelected(contextMenuShowed);
    }

    public boolean rightClickMenuEnabled() {
        return checkBoxRightClickMenuEnabled.isSelected();
    }

    public void setIntentionActionEnabled(boolean contextMenuShowed) {
        checkBoxIntentionActionEnabled.setSelected(contextMenuShowed);
    }

    public boolean intentionActionEnabled() {
        return checkBoxIntentionActionEnabled.isSelected();
    }

    public void setTextBlockLanguage(String labelLanguage,
                                     String radioButtonLanguageEnglish,
                                     String radioButtonLanguageChinese) {
        this.labelLanguage.setText(labelLanguage);
        this.radioButtonLanguageEnglish.setText(radioButtonLanguageEnglish);
        this.radioButtonLanguageChinese.setText(radioButtonLanguageChinese);
    }

    public void setTextBlockScopeOfEffect(String labelScopeOfEffect,
                                          String checkBoxSwaggerEffected,
                                          String checkBoxProtostuffEffected) {
        this.labelScopeOfEffect.setText(labelScopeOfEffect);
        this.checkBoxSwaggerAffected.setText(checkBoxSwaggerEffected);
        this.checkBoxProtostuffAffected.setText(checkBoxProtostuffEffected);
    }

    public void setTextBlockRightClickEnabled(String label,
                                              String enabled) {
        this.labelRightClickMenu.setText(label);
        this.checkBoxRightClickMenuEnabled.setText(enabled);
    }

    public void setTextBlockIntentionActionEnabled(String label,
                                                   String enabled) {
        this.labelIntentionAction.setText(label);
        this.checkBoxIntentionActionEnabled.setText(enabled);
    }

}
