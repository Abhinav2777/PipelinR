import an.awesome.pipelinr.Command

data class MyCommand(val name: String, val passwd: String, ) : Command<String>
