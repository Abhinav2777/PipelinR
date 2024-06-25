import an.awesome.pipelinr.Command

data class MyCommand(val data: String) : Command<MyCommandResult>
data class MyCommandResult(val result: String)

data class MyQuery(val parameter: String) : Command<MyQueryResult>
data class MyQueryResult(val data: String)

data class MyNotification(val message: String) : Command<Void>

