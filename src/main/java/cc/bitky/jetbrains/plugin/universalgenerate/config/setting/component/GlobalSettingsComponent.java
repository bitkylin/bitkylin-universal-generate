package cc.bitky.jetbrains.plugin.universalgenerate.config.setting.component;

import cc.bitky.jetbrains.plugin.universalgenerate.config.setting.state.GlobalSettingsState;
import lombok.Getter;

import javax.swing.*;

/**
 * @author bitkylin
 */
@Getter
public class GlobalSettingsComponent {
    private JRadioButton radioButtonLanguageEnglish;
    private JRadioButton radioButtonLanguageChinese;
    private JLabel labelLanguage;
    private JPanel mainPanel;

    public JPanel getMainPanel() {
        return mainPanel;
    }

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

    public void setLabelLanguage(String text) {
        this.labelLanguage.setText(text);
    }

    public void setRadioButtonLanguageEnglish(String text) {
        this.radioButtonLanguageEnglish.setText(text);
    }

    public void setRadioButtonLanguageChinese(String text) {
        this.radioButtonLanguageChinese.setText(text);
    }
}
