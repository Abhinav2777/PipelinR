import an.awesome.pipelinr.Command
import an.awesome.pipelinr.Command.Handler
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.core.config.Configurator
import kotlin.system.exitProcess

class MyCommandHandler : Handler<MyCommand, String> {
    override fun handle(command: MyCommand): String {
        return (mainBody(command))
    }
}

class Authentication : Command.Middleware {
    override fun <R : Any?, C : Command<R>?> invoke(command: C, next: Command.Middleware.Next<R>?): R? {
        println("Authorization is processing")
        val userid=(command as MyCommand).userId
        val upassword = (command as MyCommand).passwd
        if(userid in userList.keys && userList[userid]?.password==upassword ){
            println("Authorization successful for the user ${userList[userid]?.name}, moving to authorization")
        } else {
            logger.error("Exiting..Authorization failed")
            exitProcess(0)
        }
        return next?.invoke()
    }
}

class Authorization : Command.Middleware {
    override fun <R : Any?, C : Command<R>?> invoke(command: C, next: Command.Middleware.Next<R>?): R? {
        println("Authorizing....")
        //Accessing directly through UserId Key
        val userId = (command as MyCommand).userId
        val urole = userList[userId]?.role
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