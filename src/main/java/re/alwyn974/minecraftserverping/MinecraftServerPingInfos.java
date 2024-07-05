
package re.alwyn974.minecraftserverping;

import java.util.List;

public class MinecraftServerPingInfos {
    protected String description;
    private Players players;
    private Version version;
    private String favicon;
    private long latency;

    public String getDescription() {
        return description;
    }

    public String getStrippedDescription() {
        return MinecraftServerPingUtil.removeExtraCode(description);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Players getPlayers() {
        return players;
    }

    public Version getVersion() {
        return version;
    }

    public String getFavicon() {
        return favicon;
    }

    public long getLatency() {
        return latency;
    }

    public void setLatency(long latency) {
        this.latency = latency;
    }

    public class Players {
        private int max;
        private int online;
        private List<Player> sample;

        public int getMax() {
            return max;
        }

        public int getOnline() {
            return online;
        }

        public List<Player> getSample() {
            return sample;
        }
    }

    public class Player {
        private String name;
        private String id;

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }
    }

    public class Version {
        private String name;
        private String protocol;

        public String getName() {
            return name;
        }

        public String getProtocol() {
            return protocol;
        }
    }
}
