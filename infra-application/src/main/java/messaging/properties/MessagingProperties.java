package messaging.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "messaging")
public class MessagingProperties {
    private Topics topics = new Topics();
    private String consumerGroup;

    public void setConsumerGroup(String consumerGroup) {this.consumerGroup = consumerGroup;}
    public String getConsumerGroup() {return consumerGroup;}

    public static class Topics {
        private String registrationRequested;
        private String registrationSubmitted;
        private String registrationAdministered;

        public void setRegistrationRequested(String registrationRequested) {this.registrationRequested=registrationRequested;}
        public String getRegistrationRequested() {return registrationRequested;}

        public void setRegistrationSubmitted(String registrationSubmitted) {this.registrationSubmitted=registrationSubmitted;}
        public String getRegistrationSubmitted() {return registrationSubmitted;}

        public void setRegistrationAdministered(String registrationAdministered) {this.registrationAdministered=registrationAdministered;}
        public String getRegistrationAdministered() {return registrationAdministered;}
    }

    public Topics getTopics() {
        return topics;
    }

    public void setTopics(Topics topics) {
        this.topics = topics;
    }
}
