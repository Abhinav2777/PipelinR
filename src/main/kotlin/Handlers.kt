import an.awesome.pipelinr.Command
import an.awesome.pipelinr.Command.Handler
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.config.Configurator

class MyCommandHandler : Handler<MyCommand, String> {
    override fun handle(command: MyCommand): String {
        println("Executing the main logic.....")

        logger.debug("This is an Debug Message!")
        logger.info("This is an Info Message!")
        logger.warn("This is an Warn Message!")
        logger.error("This is an Error Message!")
        logger.fatal("This is an Fatal Message!")
        logger.trace("This is an Trace Message!")
        return "${command.data} is authorized to this code, finished main block"
    }
}

class Auth : Command.Middleware {
    override fun <R : Any?, C : Command<R>?> invoke(command: C, next: Command.Middleware.Next<R>?): R? {
        println("Authorization is processing")
        val id = (command as MyCommand).data
        if (id == "Jack") {
            println("Authorization successful, moving to main code")
            return next?.invoke()
        } else {
            logger.error("Exiting..Authorization failed")
            System.exit(0)
        }
        return null
    }
}


class Logging : Command.Middleware {
    override fun <R : Any?, C : Command<R>?> invoke(command: C, next: Command.Middleware.Next<R>?): R? {
        println("Logging")
        val loggerLevel = Level.DEBUG
        Configurator.setLevel(logger, loggerLevel)
        logger.info("The login level is set to $loggerLevel")
        return next?.invoke()
    }
}
