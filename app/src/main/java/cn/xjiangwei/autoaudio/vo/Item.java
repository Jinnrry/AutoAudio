package cn.xjiangwei.autoaudio.vo;

public class Item {

    private long id;
    private String rule;
    private String value;

    public Item() {
    }

    public Item(String rule, String value) {
        this.rule = rule;
        this.value = value;
    }

    public Item(long id, String rule, String value) {
        this.id = id;
        this.rule = rule;
        this.value = value;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
