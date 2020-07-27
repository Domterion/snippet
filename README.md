## Snippet
Based off of [ktor snippet](https://ktor.io/quickstart/guides/api.html) but database backed with web interface.

### Routes
`/` - Shows new snippet text box
`/?id=id` - Shows the snippet with id

`/action` Creates or deletes a snippet: - POST
+ create: `/action?action=create&text=text` - where text is the desired text. text cant be more than 5000 characters
+ delete: `/action?action=delete&id=id` - where id is the id to be deleted

