
package re.alwyn974.minecraftserverping;

import java.util.List;

public class MotdExtra {
    private List<Extra> extra;

    public List<Extra> getExtra() {
        return extra;
    }

    public class Extra {
        private String color;
        private boolean bold;
        private boolean underline;
        private boolean italic;
        private boolean strikethrough;
        private boolean obfuscated;
        private String text;

        public String getColor() {
            return color;
        }

        public boolean isBold() {
            return bold;
        }

        public boolean isUnderline() {
            return underline;
        }

        public boolean isItalic() {
            return italic;
        }

        public boolean isStrikethrough() {
            return strikethrough;
        }

        public boolean isObfuscated() {
            return obfuscated;
        }

        public String getText() {
            return text;
        }
    }

    public enum Enum {
        BLACK('0'), DARK_BLUE('1'), DARK_GREEN('2'), DARK_AQUA('3'), DARK_RED('4'), DARK_PURPLE('5'), GOLD('6'),
        GRAY('7'), DARK_GRAY('8'), BLUE('9'), GREEN('a'), AQUA('b'), RED('c'), LIGHT_PURPLE('d'), YELLOW('e'),
        WHITE('f'), BOLD('l'), UNDERLINE('o'), ITALIC('n'), STRIKETHROUGH('m'), OBFUSCATED('k'), RESET('r');

        private Enum(char extraLetter) {
            this.extraLetter = extraLetter;
        }

        private char extraLetter;

        public String getExtraCode() {
            return "§" + extraLetter;
        }
    }
}
