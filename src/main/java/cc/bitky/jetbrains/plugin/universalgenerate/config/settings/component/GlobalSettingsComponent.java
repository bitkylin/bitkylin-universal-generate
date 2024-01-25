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

    // region --------- field -> global -----------

    // region --------- field -> Language -----------

    private JLabel labelLanguage;
    private JRadioButton radioButtonLanguageEnglish;
    private JRadioButton radioButtonLanguageChinese;

    // endregion

    // region --------- field -> scope of effect -----------

    private JLabel labelScopeOfEffect;
    private JCheckBox checkBoxSwaggerAffected;
    private JCheckBox checkBoxProtostuffAffected;

    // endregion

    // region --------- field -> right click menu -----------

    private JLabel labelRightClickMenu;
    private JCheckBox checkBoxRightClickMenuEnabled;

    // endregion

    // region --------- field -> intention action -----------

    private JLabel labelIntentionAction;
    private JCheckBox checkBoxIntentionActionEnabled;

    // endregion

    // endregion

    // region --------- field -> Protostuff tab -----------

    // region --------- field -> tag assign -----------

    private JLabel labelProtostuffTagAssign;
    private JRadioButton radioButtonProtostuffTagAssignNonRepeatable;
    private JRadioButton radioButtonProtostuffTagAssignFromStartValue;

    // endregion

    // region --------- field -> start value -----------

    private JLabel labelProtostuffStartValue;
    private JSpinner spinnerProtostuffStartValue;

    // endregion

    // region --------- field -> scope interval -----------

    private JLabel labelProtostuffScopeInterval;
    private JSpinner spinnerProtostuffScopeInterval;

    // endregion

    // endregion

    // region --------- field -> Swagger tab -----------

    private JLabel labelSwaggerStayTuned;

    // endregion

    // region --------- method -> settings state -----------

    public GlobalSettingsComponent() {
        updateUiForScopeIntervalVisible();
        radioButtonProtostuffTagAssignNonRepeatable.addChangeListener(e -> {
            updateUiForScopeIntervalVisible();
        });

        spinnerProtostuffStartValue.addChangeListener(e -> {
            if (outsideIntegerBound(spinnerProtostuffStartValue.getValue(), GlobalSettingsState.DEFAULT_PROTOSTUFF_TAG_START_VALUE)) {
                spinnerProtostuffStartValue.setValue(GlobalSettingsState.DEFAULT_PROTOSTUFF_TAG_START_VALUE);
            }
        });
        spinnerProtostuffScopeInterval.addChangeListener(e -> {
            if (outsideIntegerBound(spinnerProtostuffScopeInterval.getValue(), GlobalSettingsState.DEFAULT_PROTOSTUFF_TAG_SCOPE_INTERVAL)) {
                spinnerProtostuffScopeInterval.setValue(GlobalSettingsState.DEFAULT_PROTOSTUFF_TAG_SCOPE_INTERVAL);
            }
        });
    }

    private static boolean outsideIntegerBound(Object valueObj, int initValue) {
        if (valueObj instanceof Number number) {
            long value = number.longValue();
            return value < initValue || value > Integer.MAX_VALUE;
        }
        return true;
    }

    // region --------- method -> settings state -> global -----------

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

    // endregion

    // region --------- method -> settings state -> protostuff tag -----------

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
            case NON_REPEATABLE -> updateUiForProtostuffTagAssignNonRepeatable();

            case FROM_START_VALUE -> updateUiForProtostuffTagAssignFromStartValue();

            default -> updateUiForProtostuffTagAssignNonRepeatable();

        }
    }

    private void updateUiForProtostuffTagAssignNonRepeatable() {
        radioButtonProtostuffTagAssignNonRepeatable.setSelected(true);
        radioButtonProtostuffTagAssignFromStartValue.setSelected(false);
    }

    private void updateUiForProtostuffTagAssignFromStartValue() {
        radioButtonProtostuffTagAssignNonRepeatable.setSelected(false);
        radioButtonProtostuffTagAssignFromStartValue.setSelected(true);
    }

    private void updateUiForScopeIntervalVisible() {
        if (radioButtonProtostuffTagAssignNonRepeatable.isSelected()) {
            labelProtostuffScopeInterval.setEnabled(true);
            spinnerProtostuffScopeInterval.setEnabled(true);
        } else {
            labelProtostuffScopeInterval.setEnabled(false);
            spinnerProtostuffScopeInterval.setEnabled(false);
        }
    }

    public int getProtostuffTagStartValue() {
        Object valueObj = spinnerProtostuffStartValue.getValue();
        if (valueObj instanceof Integer number) {
            return number;
        }
        return GlobalSettingsState.DEFAULT_PROTOSTUFF_TAG_START_VALUE;
    }

    public void setProtostuffTagStartValue(int startValue) {
        spinnerProtostuffStartValue.setValue(startValue);
    }

    public int getProtostuffTagScopeInterval() {
        Object valueObj = spinnerProtostuffScopeInterval.getValue();
        if (valueObj instanceof Integer number) {
            return number;
        }
        return GlobalSettingsState.DEFAULT_PROTOSTUFF_TAG_SCOPE_INTERVAL;
    }

    public void setProtostuffTagScopeInterval(int scopeInterval) {
        spinnerProtostuffScopeInterval.setValue(scopeInterval);
    }

    public boolean protostuffTagModified(int startValue, int scopeInterval) {
        Object uiStartValue = spinnerProtostuffStartValue.getValue();
        Object uiScopeInterval = spinnerProtostuffScopeInterval.getValue();

        return !uiStartValue.equals(startValue) || !uiScopeInterval.equals(scopeInterval);
    }

    // endregion

    // endregion

    // region --------- method -> ui text -----------

    // region --------- method -> ui text -> global -----------

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

    // endregion

    // region --------- method -> ui text -> protostuff tag -----------

    public void setTextBlockProtostuffTagAssign(String labelProtostuffTagAssign,
                                                String labelProtostuffTagAssignToolTip,
                                                String radioButtonProtostuffTagSetNonRepeatable,
                                                String radioButtonProtostuffTagSetNonRepeatableToolTip,
                                                String radioButtonProtostuffTagSetFromStartValue,
                                                String radioButtonProtostuffTagSetFromStartValueToolTip) {
        this.labelProtostuffTagAssign.setText(labelProtostuffTagAssign);
        this.labelProtostuffTagAssign.setToolTipText(labelProtostuffTagAssignToolTip);
        this.radioButtonProtostuffTagAssignNonRepeatable.setText(radioButtonProtostuffTagSetNonRepeatable);
        this.radioButtonProtostuffTagAssignNonRepeatable.setToolTipText(radioButtonProtostuffTagSetNonRepeatableToolTip);
        this.radioButtonProtostuffTagAssignFromStartValue.setText(radioButtonProtostuffTagSetFromStartValue);
        this.radioButtonProtostuffTagAssignFromStartValue.setToolTipText(radioButtonProtostuffTagSetFromStartValueToolTip);
    }

    public void setTextBlockProtostuffTagStartValue(String labelProtostuffStartValue,
                                                    String labelProtostuffStartValueToolTip,
                                                    String spinnerProtostuffStartValueToolTip) {
        this.labelProtostuffStartValue.setText(labelProtostuffStartValue);
        this.labelProtostuffStartValue.setToolTipText(labelProtostuffStartValueToolTip);
        this.spinnerProtostuffStartValue.setToolTipText(spinnerProtostuffStartValueToolTip);
    }

    public void setTextBlockProtostuffTagScopeInterval(String labelProtostuffScopeInterval,
                                                       String labelProtostuffScopeIntervalToolTip,
                                                       String spinnerProtostuffScopeIntervalToolTip) {
        this.labelProtostuffScopeInterval.setText(labelProtostuffScopeInterval);
        this.labelProtostuffScopeInterval.setToolTipText(labelProtostuffScopeIntervalToolTip);
        this.spinnerProtostuffScopeInterval.setToolTipText(spinnerProtostuffScopeIntervalToolTip);
    }

    // endregion

    // region --------- method -> ui text -> Swagger tag -----------

    public void setTextBlockSwaggerTagStayTuned(String labelSwaggerTagStayTuned) {
        this.labelSwaggerStayTuned.setText(labelSwaggerTagStayTuned);
    }

    // endregion

}
