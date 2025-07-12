package hw.zako.multichat.exception;

public class ConfigSectionInvalid extends RuntimeException {
    public ConfigSectionInvalid(String nameSection) {
        super(nameSection + " config is invalid");
    }
}
