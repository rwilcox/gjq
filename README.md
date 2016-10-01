# Introduction

`gjq` is a tool inspired by [jq](https://stedolan.github.io/jq/).

Instead of taking its inspiration from ack/sed, like `jq`, `gjq` exposes your JSON as an object you can run [Groovy gpath](http://www.groovy-lang.org/semantics.html#gpath_expressions) expressions on.

## What are GPath expressions?

GPath is an easy way to navigate deep nested structures in code. In short, it's an object graph navigation language. 

GPath is both natural to read and very powerful, let's see an example

You have an object like this:

`a = [ {name: 'thing'}, {name: 'thang'} ]`

We see `a` is an array of records each with a key name.

In GPath we can easy answer the question, "Give me a list of names of the people in this array", with one easy line of code:

`a.name`

This is it. The equivalent code even in Ruby would be `a.map(&:name)`, assuming a relatively advanced Rubiest and an array of objects that responds to `name`.

GPath expressions work all the way down too: for arrays in those records in the `a` array, or arrays in records in records in arrays... as far down as your structure goes.

GPath expressions also return pure Groovy objects - there's nothing special!

# gjq in Action: Examples

## Get all the names of every repository in my Github

    $ curl --silent https://api.github.com/users/rwilcox/repos | gjq 'name'
    [3taps-Python-Client, aasm, c.tmbundle, chatchat, CoffeeScript.bbpackage ...
    
## Get all the BBEdit Packages in my Github: 

    $ curl --silent https://api.github.com/users/rwilcox/repos | gjq 'name.findAll( { currentName -> currentName.contains ".bbpackage"} )'
    [CoffeeScript.bbpackage, dash.bbpackage, django.bbpackage, email.bbpackage]
    

# gjq in Action: Interactive Mode

Sometimes you don't know the structure of your json first hand. Interactive mode can help here: it exposes your json to a REPL!

    $ curl --silent https://api.github.com/users/rwilcox/repos | gjq --interactive
    
In interactive mode the parsed json object lives in a variable named `json`.

    groovy> json.name
    Result: [....]
