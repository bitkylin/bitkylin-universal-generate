package cc.bitky.jetbrains.plugin.universalgenerate.config;

import cc.bitky.jetbrains.plugin.universalgenerate.constants.LocalizationEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author bitkylin
 */
public final class ElementNameSuffixConfig {

    private ElementNameSuffixConfig() {
    }

    private static final Map<String, LocalizationEnum> FUZZY_SUFFIX_NAME_MAP = Maps.newLinkedHashMap();

    private static final Set<String> FUZZY_SUFFIX_NAME_SET = Sets.newHashSet();

    static {
        FUZZY_SUFFIX_NAME_MAP.put("List", LocalizationEnum.ELEMENT_NAME_SUFFIX_LIST);
        FUZZY_SUFFIX_NAME_MAP.put("Map", LocalizationEnum.ELEMENT_NAME_SUFFIX_MAP);
        FUZZY_SUFFIX_NAME_MAP.put("Set", LocalizationEnum.ELEMENT_NAME_SUFFIX_SET);
        FUZZY_SUFFIX_NAME_MAP.put("Array", LocalizationEnum.ELEMENT_NAME_SUFFIX_ARRAY);
        FUZZY_SUFFIX_NAME_MAP.put("s", LocalizationEnum.ELEMENT_NAME_SUFFIX_S);

        FUZZY_SUFFIX_NAME_SET.add(LocalizationEnum.ELEMENT_NAME_SUFFIX_LIST.getEnglish());
        FUZZY_SUFFIX_NAME_SET.add(LocalizationEnum.ELEMENT_NAME_SUFFIX_MAP.getEnglish());
        FUZZY_SUFFIX_NAME_SET.add(LocalizationEnum.ELEMENT_NAME_SUFFIX_SET.getEnglish());
        FUZZY_SUFFIX_NAME_SET.add(LocalizationEnum.ELEMENT_NAME_SUFFIX_ARRAY.getEnglish());
        FUZZY_SUFFIX_NAME_SET.add(LocalizationEnum.ELEMENT_NAME_SUFFIX_S.getEnglish());

        FUZZY_SUFFIX_NAME_SET.add(LocalizationEnum.ELEMENT_NAME_SUFFIX_LIST.getChinese());
        FUZZY_SUFFIX_NAME_SET.add(LocalizationEnum.ELEMENT_NAME_SUFFIX_MAP.getChinese());
        FUZZY_SUFFIX_NAME_SET.add(LocalizationEnum.ELEMENT_NAME_SUFFIX_SET.getChinese());
        FUZZY_SUFFIX_NAME_SET.add(LocalizationEnum.ELEMENT_NAME_SUFFIX_ARRAY.getChinese());
        FUZZY_SUFFIX_NAME_SET.add(LocalizationEnum.ELEMENT_NAME_SUFFIX_S.getChinese());

    }

    public static List<String> fuzzySuffixNameList() {
        return Lists.newArrayList(FUZZY_SUFFIX_NAME_MAP.keySet().iterator());
    }

    public static LocalizationEnum fuzzySuffixNameLocalization(String suffix) {
        return FUZZY_SUFFIX_NAME_MAP.get(suffix);
    }

    public static Set<String> fuzzySuffixNameSet() {
        return FUZZY_SUFFIX_NAME_SET;
    }
}