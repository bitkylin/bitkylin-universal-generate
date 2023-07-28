package cc.bitky.jetbrains.plugin.universalgenerate.factory.commandtype.base;

import cc.bitky.jetbrains.plugin.universalgenerate.pojo.PsiClassWrapper;
import cc.bitky.jetbrains.plugin.universalgenerate.pojo.WriteContext;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static cc.bitky.jetbrains.plugin.universalgenerate.service.tag.AnnotationTagUtils.calcCurrentGroupBeginNum;

/**
 * @author bitkylin
 */
public abstract class CommandTypeAbstractWriteTagProcessor extends AbstractCommandTypeProcessor {

    protected WriteContext writeContext;

    /**
     * 初始值
     */
    protected int beginNumValue;

    /**
     * 步长
     */
    protected int stepNumValue;

    protected Set<Integer> tagExistedSet;


    protected AtomicInteger initAvailableBeginNum(int groupTagNum) {
        int groupBeginNum = calcCurrentGroupBeginNum(beginNumValue, stepNumValue, groupTagNum);

        AtomicInteger currentNum = new AtomicInteger(groupBeginNum);
        updateCurrentNum(currentNum, beginNumValue, tagExistedSet);
        return currentNum;
    }

    protected void updateCurrentNum(AtomicInteger currentNum, Integer psiFieldTagValue, Set<Integer> tagExistedSet) {
        currentNum.set(Math.max(currentNum.get(), psiFieldTagValue));
        while (tagExistedSet.contains(currentNum.get())) {
            currentNum.incrementAndGet();
        }
    }

    protected boolean canWriteAnnotationTag(PsiClassWrapper psiClassWrapper) {
        if (psiClassWrapper.getClassRole() != PsiClassWrapper.ClassRoleEnum.POJO) {
            return false;
        }
//        if (psiClassWrapper.getClassLocation() != PsiClassWrapper.ClassLocationEnum.INTERFACE_ENTRANCE) {
//            return false;
//        }
        if (psiClassWrapper.getPsiClass().isEnum()) {
            return false;
        }
        return true;
    }
}
