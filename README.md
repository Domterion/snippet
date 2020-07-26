## Snippet
Based off of [ktor snippet](https://ktor.io/quickstart/guides/api.html) but database backed with web interface.

### Routes
`/` - Shows all snippets and field to add more - GET

`/action` Creates or deletes a snippet: - POST
+ create: `/action?action=create&text=text` - where text is the desired text. text cant be more than 2000 characters
+ delete: `/action?action=delete&id=id` - where id is the id to be deleted

