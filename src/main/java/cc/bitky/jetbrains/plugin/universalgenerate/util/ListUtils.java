package cc.bitky.jetbrains.plugin.universalgenerate.util;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author bitkylin
 */
public final class ListUtils {

    private ListUtils() {
    }

    /**
     * 从list中摘出字段生成新的list并去重和去空
     * 注：本方法包含去重，去重操作复杂，需要考虑适用场景，不要无脑使用
     */
    public static <T, R> List<R> distinctMap(List<T> rawList, Function<T, R> apply) {
        if (Objects.isNull(rawList)) {
            return Lists.newArrayList();
        }
        return rawList.stream()
                .filter(Objects::nonNull)
                .map(apply)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }

    /**
     * 从list中摘出字段生成新的list并去空
     */
    public static <T, R> List<R> map(List<T> rawList, Function<T, R> apply) {
        if (Objects.isNull(rawList)) {
            return Lists.newArrayList();
        }
        return rawList.stream()
                .filter(Objects::nonNull)
                .map(apply)
                .filter(Objects::nonNull)
                .toList();
    }

}
