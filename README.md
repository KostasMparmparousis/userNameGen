# Introduction
A sparkJava API responsible for creating suggested userNames based on given firstName and lastName.

# How to use
You can call the userNameGenerator API by sending a request file at the endpoint ***/generator***. The request shall be structured as a JSON schema and should be consequently included
in a `.json` file. Details about it's structure below:

- [JSON Request schema-validator](https://github.com/KostasMparmparousis/userNameGen/wiki/JSON-request-schema&validator)

The API follows a simple principal: ***by adding properties*** to the schema you will essentially enable it to ***construct userNames*** based on a plethora of ***different techniques*** (i.e. prefixedIDs, fullNames, prefixedNames, partOfNames, etc.). Then you will be able to also define how the userNames are ***ordered and prioritized***.

A closer look at each ***property***:

- [JSON Request Properties Explained](https://github.com/KostasMparmparousis/userNameGen/wiki/JSON-Request-Properties-Explained)

# How the userNames are generated
- [Username generation methods](https://github.com/KostasMparmparousis/userNameGen/wiki/Username-generation-methods)

# What is the response
- [Response format & Test calls](https://github.com/KostasMparmparousis/userNameGen/wiki/Response-format-&-Test-calls)







