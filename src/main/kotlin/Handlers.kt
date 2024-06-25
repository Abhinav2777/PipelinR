import an.awesome.pipelinr.Command
import an.awesome.pipelinr.Command.Handler
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.config.Configurator

class MyCommandHandler : Handler<MyCommand, String> {
    override fun handle(command: MyCommand): String {
        return (mainBody(command))
    }
}

class Authentication : Command.Middleware {
    override fun <R : Any?, C : Command<R>?> invoke(command: C, next: Command.Middleware.Next<R>?): R? {
        println("Authorization is processing")
        val uname = (command as MyCommand).name
        val upassword = (command as MyCommand).passwd
        val isAuthentic = usersList
            .find{
                    it.name == uname && it.password==upassword
            }
        if (isAuthentic != null) {
            println("Authorization successful, moving to authorization")
            return next?.invoke()
        } else {
            logger.error("Exiting..Authorization failed")
            System.exit(0)
        }
        return null
    }
}

//
//class Logging : Command.Middleware {
//    override fun <R : Any?, C : Command<R>?> invoke(command: C, next: Command.Middleware.Next<R>?): R? {
//        println("Logging")
//        val loggerLevel = Level.ERROR
//        Configurator.setLevel(logger, loggerLevel)
//        logger.info("The login level is set to $loggerLevel")
//        return next?.invoke()
//    }
//}


class Authorization : Command.Middleware {
    override fun <R : Any?, C : Command<R>?> invoke(command: C, next: Command.Middleware.Next<R>?): R? {
        println("Authorizing....")
        val uname = (command as MyCommand).name
        val upassword = (command as MyCommand).passwd
        val urole = usersList
            .find{
                it.name == uname && it.password == upassword
            }?.role
        when(urole) {
            "Dev" -> Configurator.setLevel(logger, Level.INFO)
            "Admin" -> Configurator.setLevel(logger, Level.TRACE)
            "Tester" -> Configurator.setLevel(logger, Level.WARN)
            "Debugger" -> Configurator.setLevel(logger, Level.DEBUG)
        }
        println("Roles have been matched, and corresponding permissions have been allocated, your role is $urole")
        return next?.invoke()
    }
}