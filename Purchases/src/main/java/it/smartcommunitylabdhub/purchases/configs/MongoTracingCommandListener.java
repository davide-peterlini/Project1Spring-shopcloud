package it.smartcommunitylabdhub.purchases.configs;


import com.mongodb.event.CommandFailedEvent;
import com.mongodb.event.CommandListener;
import com.mongodb.event.CommandStartedEvent;
import com.mongodb.event.CommandSucceededEvent;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import org.bson.BsonDocument;
import org.bson.BsonValue;

import java.util.concurrent.ConcurrentHashMap;


public class MongoTracingCommandListener implements CommandListener {

    private final Tracer tracer;
    private final ConcurrentHashMap<Integer, Span> spanMap = new ConcurrentHashMap<>();

    public MongoTracingCommandListener(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public void commandStarted(CommandStartedEvent event) {
        String commandName = event.getCommandName();
        BsonDocument command = event.getCommand();

        // Start new span
        Span span = tracer.nextSpan().name("mongo." + commandName).start();
        span.tag("mongodb.command", commandName);
        span.tag("mongodb.database", event.getDatabaseName());

        // Try to get the collection name dynamically (e.g. "find", "insert", "aggregate", etc.)
        command.getFirstKey(); // often the collection name is the first key
        String collectionName = extractCollectionName(commandName, command);
        if (collectionName != null) {
            span.tag("mongodb.collection", collectionName);
        }

        // Log the full command (can be verbose — use with caution)
        span.event("command.started");
        span.tag("mongodb.command.raw", command.toString());

        spanMap.put(event.getRequestId(), span);
    }

    @Override
    public void commandSucceeded(CommandSucceededEvent event) {
        Span span = spanMap.remove(event.getRequestId());
        if (span != null) {
            span.tag("status", "success");
            span.event("command.succeeded");
            span.end();
        }
    }

    @Override
    public void commandFailed(CommandFailedEvent event) {
        Span span = spanMap.remove(event.getRequestId());
        if (span != null) {
            span.tag("status", "failed");
            span.error(event.getThrowable());
            span.event("command.failed");
            span.end();
        }
    }

    private String extractCollectionName(String commandName, BsonDocument command) {
        // Heuristic: try to find collection name for known commands
        switch (commandName) {
            case "find":
            case "insert":
            case "update":
            case "delete":
            case "aggregate":
            case "count":
            case "distinct":
                BsonValue val = command.get(commandName);
                return val != null ? val.asString().getValue() : null;
            default:
                // Some commands don't operate on a collection
                return null;
        }
    }
}

