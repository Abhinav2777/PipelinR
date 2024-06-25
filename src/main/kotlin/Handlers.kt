import an.awesome.pipelinr.Command.Handler

class MyCommandHandler : Handler<MyCommand, MyCommandResult> {
    override fun handle(command: MyCommand): MyCommandResult {
        return MyCommandResult("Handled: ${command.data}")
    }
}

class MyQueryHandler : Handler<MyQuery, MyQueryResult> {
    override fun handle(command: MyQuery): MyQueryResult {
        return MyQueryResult("Data for: ${command.parameter}")
    }
}

class MyNotificationHandler : Handler<MyNotification, Void> {
    override fun handle(command: MyNotification): Void? {
        println("Notification: ${command.message}")
        return null
    }
}
