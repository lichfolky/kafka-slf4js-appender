import mjson.Json;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.AbstractStringLayout;

import java.nio.charset.Charset;

@Plugin(name = "WaspJsonLayout", category = "Core", elementType = "layout", printObject = true)
public class WaspJsonLayout extends AbstractStringLayout {

    protected WaspJsonLayout(Charset charset) {
        super(charset);
    }

    /**
     * Formats the event as an Object that can be serialized.
     *
     * @param event The Logging Event.
     * @return The formatted event.
     */
    @Override
    public String toSerializable(LogEvent event) {
        return this.toJson(event).toString() + "\n";
    }

    private Json toJson(LogEvent event) {
        return Json.object()
                .set("log_source", event.getLoggerName())
                .set("log_level", event.getLevel().toString())
                .set("message", event.getMessage().toString())
                .set("timestamp", event.getInstant().toString())
                // TO CHECK
                .set("thread", event.getThreadId())
                // TO CHECK

                .set("cause", "TO CHECK")
                // TO CHECK
                .set("stack_trace", "TO CHECK")
                .set("all", "");
    }

    @PluginFactory
    public static WaspJsonLayout createLayout(
            @PluginAttribute(value = "charset", defaultString = "UTF-8") Charset charset) {
        return new WaspJsonLayout(charset);
    }
}