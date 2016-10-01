#!/opt/local/bin/groovy

import groovy.json.JsonSlurper
import groovy.lang.GroovyShell
import org.codehaus.groovy.control.CompilerConfiguration

import groovy.ui.Console;

void applyCommandToJson( cmd, output ) {
	

	CompilerConfiguration cc = new CompilerConfiguration();
	cc.setScriptBaseClass(DelegatingScript.class.getName())

	def cl = ClassLoader.systemClassLoader

	GroovyShell sh = new GroovyShell(cc);
	DelegatingScript script = sh.parse(cmd)

	// setting the delegate object here!
	script.setDelegate( output );

	println script.run();
}

// ============================= main program follows below ==========

def jsonStr = System.in.text //'[{"hello": "world", "obj": [{"a": "bobby"}, {"a": "ryan"}]}]'

def cli = new CliBuilder(usage: 'gjq.groovy -[h] -[i] [json_filter]')
cli.with {
	h longOpt: 'help', 'Show usage information'
	
	i longOpt: 'interactive', 'Interactively explore incoming JSON (does not require json_filter to be specified)'
}

def options = cli.parse( args )

// Show usage text when -h or --help option is used.
if (options.h) {
	cli.usage()
	return
}

def sr = new StringReader( jsonStr )
def output = new JsonSlurper().parse( sr )

if (options.i) {
	
	Console console = new Console();
	console.setVariable("json", output);	
	console.run()
}

else {
	applyCommandToJson( options.arguments()[0], output )
}
