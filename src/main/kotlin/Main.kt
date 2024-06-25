import an.awesome.pipelinr.CommandHandlers
import an.awesome.pipelinr.Pipeline
import an.awesome.pipelinr.Pipelinr
import java.util.stream.Stream

fun main() {
    // Initialize handlers
    val commandHandlers = CommandHandlers{
        Stream.of(
            MyCommandHandler(),
            MyQueryHandler(),
            MyNotificationHandler()
        )
    }

    // Initialize pipeline
    val pipeline: Pipeline = Pipelinr()
        .with(commandHandlers)

    // Execute a command
    val commandResult = pipeline.send(MyCommand("Test Command"))
    println(commandResult.result)

    // Execute a query
    val queryResult = pipeline.send(MyQuery("Test Query"))
    println(queryResult.data)

    // Publish a notification
    pipeline.send(MyNotification("Test Notification"))
}
