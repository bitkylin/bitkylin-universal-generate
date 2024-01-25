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

    // region --------- global -----------

    // region --------- Language -----------

    private JLabel labelLanguage;
    private JRadioButton radioButtonLanguageEnglish;
    private JRadioButton radioButtonLanguageChinese;

    // endregion

    // region --------- scope of effect -----------

    private JLabel labelScopeOfEffect;
    private JCheckBox checkBoxSwaggerAffected;
    private JCheckBox checkBoxProtostuffAffected;

    // endregion

    // region --------- right click menu -----------

    private JLabel labelRightClickMenu;
    private JCheckBox checkBoxRightClickMenuEnabled;

    // endregion

    // region --------- intention action -----------

    private JLabel labelIntentionAction;
    private JCheckBox checkBoxIntentionActionEnabled;

    // endregion

    // endregion

    // region --------- Protostuff tab -----------

    // region --------- tag assign -----------

    private JLabel labelProtostuffTagAssign;
    private JRadioButton radioButtonProtostuffTagAssignNonRepeatable;
    private JRadioButton radioButtonProtostuffTagAssignFromStartValue;

    // endregion

    // region --------- start value -----------

    private JLabel labelProtostuffStartValue;
    private JSpinner spinnerProtostuffStartValue;

    // endregion

    // endregion

    // region --------- Swagger tab -----------

    private JLabel labelSwaggerUnfinished;

    // endregion


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
                radioButtonLanguageEnglish.setSelected(true);
                radioButtonLanguageChinese.setSelected(false);
            }
            case CHINESE -> {
                radioButtonLanguageEnglish.setSelected(false);
                radioButtonLanguageChinese.setSelected(true);
            }
            default -> {
                radioButtonLanguageEnglish.setSelected(true);
                radioButtonLanguageChinese.setSelected(false);
            }
        }
    }

    public GlobalSettingsState.ProtostuffTagAssignEnum getProtostuffTagAssign() {
        if (radioButtonProtostuffTagAssignNonRepeatable.isSelected()) {
            return GlobalSettingsState.ProtostuffTagAssignEnum.NON_REPEATABLE;
        } else if (radioButtonProtostuffTagAssignFromStartValue.isSelected()) {
            return GlobalSettingsState.ProtostuffTagAssignEnum.FROM_START_VALUE;
        } else {
            return GlobalSettingsState.ProtostuffTagAssignEnum.NON_REPEATABLE;
        }
    }

    public void setProtostuffTagAssign(GlobalSettingsState.ProtostuffTagAssignEnum tagAssignEnum) {
        switch (tagAssignEnum) {
            case NON_REPEATABLE -> {
                radioButtonProtostuffTagAssignNonRepeatable.setSelected(true);
                radioButtonProtostuffTagAssignFromStartValue.setSelected(false);
            }
            case FROM_START_VALUE -> {
                radioButtonProtostuffTagAssignNonRepeatable.setSelected(false);
                radioButtonProtostuffTagAssignFromStartValue.setSelected(true);
            }
            default -> {
                radioButtonProtostuffTagAssignNonRepeatable.setSelected(true);
                radioButtonProtostuffTagAssignFromStartValue.setSelected(false);
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

    public void setTextBlockProtostuffTagAssign(String labelProtostuffTagAssign,
                                                String radioButtonProtostuffTagSetNonRepeatable,
                                                String radioButtonProtostuffTagSetFromStartValue) {
        this.labelProtostuffTagAssign.setText(labelProtostuffTagAssign);
        this.radioButtonProtostuffTagAssignNonRepeatable.setText(radioButtonProtostuffTagSetNonRepeatable);
        this.radioButtonProtostuffTagAssignFromStartValue.setText(radioButtonProtostuffTagSetFromStartValue);
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
