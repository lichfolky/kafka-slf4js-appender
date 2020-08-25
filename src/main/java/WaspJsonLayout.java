import mjson.Json;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.layout.ByteBufferDestination;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

@Plugin(name = "WaspJsonLayout", category = "Core", elementType = "layout", printObject = false)
public class WaspJsonLayout implements Layout<Json> {

    protected WaspJsonLayout() {
    }

    /**
     * Returns the format for the layout format.
     *
     * @return The footer.
     */
    @Override
    public byte[] getFooter() {
        return new byte[0];
    }

    /**
     * Returns the header for the layout format.
     *
     * @return The header.
     */
    @Override
    public byte[] getHeader() {
        return new byte[0];
    }

    /**
     * Formats the event suitable for display.
     *
     * @param event The Logging Event.
     * @return The formatted event.
     */
    @Override
    public byte[] toByteArray(LogEvent event) {
        Json jsonEvent = toSerializable(event);

        return jsonEvent.toString().getBytes();

    }

    /**
     * Formats the event as an Object that can be serialized.
     * {
     * "log_source" -> event.loggerName,
     * "log_level" -> event.level.toString,
     * "message" -> event.message,
     * "timestamp" -> DateTimeFormatter.ISO_INSTANT.format(event.timestamp),
     * "thread" -> event.thread,
     * "cause" -> event.maybeCause.getOrElse(""),
     * "stack_trace" -> event.maybeStackTrace.getOrElse(""),
     * "all" -> all
     * }
     *
     * @param event The Logging Event.
     * @return The formatted event.
     */
    @Override
    public Json toSerializable(LogEvent event) {

        return Json.object()
                .set("log_source", event.getLoggerName())
                .set("log_level", event.getLevel())
                .set("message", event.getMessage())
                .set("timestamp", event.getInstant())
                // TO CHECK
                .set("thread", event.getThreadId())
                // TO CHECK

                .set("cause", "TO CHECK")
                // TO CHECK

                .set("stack_trace", event.getContextStack().asList())
                .set("all", event.toString());

    }

    /**
     * Returns the content type output by this layout. The base class returns "text/plain".
     *
     * @return the content type.
     */
    @Override
    public String getContentType() {
        return "application/json";
    }

    /**
     * Returns a description of the content format.
     *
     * @return a Map of key/value pairs describing the Layout-specific content format, or an empty Map if no content
     * format descriptors are specified.
     */
    @Override
    public Map<String, String> getContentFormat() {

        Map<String, String> contentFormat = new HashMap<>();
        contentFormat.put("log_source", "log_source");
        contentFormat.put("log_level", "log_level");
        contentFormat.put("message", "message");
        contentFormat.put("timestamp", "timestamp");
        contentFormat.put("thread", "thread");
        contentFormat.put("cause", "cause");
        contentFormat.put("stack_trace", "stack_trace");
        contentFormat.put("all", "all");

        return contentFormat;
    }

    /**
     * Encodes the specified source object to some binary representation and writes the result to the specified
     * destination.
     *
     * @param source      the object to encode.
     * @param destination holds the ByteBuffer to write into.
     */
    @Override
    public void encode(LogEvent source, ByteBufferDestination destination) {
        destination.writeBytes(ByteBuffer.wrap(this.toByteArray(source)));
    }

    //PluginFactory and with each of the methods parameters annotated with PluginAttr or
    // PluginElement as appropriate.
    @PluginFactory
    public static WaspJsonLayout createLayout() {
        return new WaspJsonLayout();
    }
}