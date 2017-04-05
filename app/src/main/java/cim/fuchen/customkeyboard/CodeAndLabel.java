package cim.fuchen.customkeyboard;

/**
 * Created by wangshuo01 on 2017/3/31.
 */

public class CodeAndLabel {

    public int code;
    public String label;

    public CodeAndLabel(int code, String label) {
        this.code = code;
        this.label = label;
    }

    @Override
    public String toString() {
        return "CodeAndLabel{" +
                "label='" + label + '\'' +
                '}';
    }
}
