package sk.stefan.enums;

/**
 * Relativní dny.
 *
 * @author Stefan
 *
 */
public enum ZBD_RelativeDays {

    TODAY("dnes"), YESTERDAY("včera"), TOMORROW("zítra");

    public String name;

    ZBD_RelativeDays(String name) {
        this.name = name;
    }

}
