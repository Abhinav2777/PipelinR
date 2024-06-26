import an.awesome.pipelinr.Command

data class MyCommand(val userId: String,val passwd : String ) : Command<String>
