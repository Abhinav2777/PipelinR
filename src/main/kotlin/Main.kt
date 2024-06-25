import an.awesome.pipelinr.Command
import an.awesome.pipelinr.CommandHandlers
import an.awesome.pipelinr.Pipeline
import an.awesome.pipelinr.Pipelinr
import com.sun.tools.javac.Main
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.stream.Stream


val logger: Logger = LogManager.getLogger(Main::class.java)

fun main() {
    // Initialize handlers
    val commandHandlers = CommandHandlers{
        Stream.of(
            MyCommandHandler(),
        )
    }

    // Initialize pipeline
    val pipeline: Pipeline = Pipelinr()
        .with(
            Command.Middlewares{
                Stream.of(Logging(), Auth())
            }
        )
        .with(commandHandlers)


//     Execute a command
    println("Enter name for Authorisation: ")
    val input: String= readLine()!!.toString()
    val commandResult = pipeline.send(MyCommand(input))
    println(commandResult)

}
