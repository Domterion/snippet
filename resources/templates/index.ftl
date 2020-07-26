<!doctype html>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/static/styles.css">

    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous" />

    <title>Snippet</title>

</head>
<body>
<nav class="navbar navbar-dark bg-dark">
    <span class="navbar-brand mb-0 h1">Snippet</span>
</nav>
<div class="container snippets">
    <div class="create">
        <form method="post" action="action">
            <input type="hidden" name="action" value="create"/>
            <label for="textInput">Text</label>
            <input type="text" id="textInput" name="text" class="form-control" aria-describedby="textHelp">
            <small id="textHelp" class="form-text text-muted">Add a snippet, note that the character length cant be above 2000.</small>
            <button type="submit" class="btn btn-dark">Submit</button>
        </form>
    </div>
    <div class="list">
        <ul class="list-group">
            <#list snippets as s>
                <form method="post" action="action">
                    <input type="hidden" name="id" value="${s.id}"/>
                    <input type="hidden" name="text" value="${s.text}"/>
                    <input type="hidden" name="action" value="delete"/>
                    <li class="list-group-item d-flex justify-content-between align-items-center">${s.id}. ${s.text}
                        <small>Created at ${s.created} UTC</small>
                        <button type="submit" title="Delete" class="btn">
                            <i class="material-icons">delete</i>
                        </button>
                    </li>
                </form>
            </#list>
        </ul>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.4.1.min.js" integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous" type="text/javascript"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous" type="text/javascript"></script>

</body>
</html>