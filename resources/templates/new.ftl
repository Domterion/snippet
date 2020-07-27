<!doctype html>

<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/static/styles.css">

    <link href="https://fonts.googleapis.com/css2?family=Roboto&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous"/>

    <title>Snippet</title>

</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark fixed-bottom">
    <a class="navbar-brand h1" href="/">Snippet</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav"
            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav ml-auto">
            <li class="nav-item">
                <#if snippet??>
                    <button class="btn" onclick="location.href='/'" type="button">
                        <i class="material-icons md-light">add_box</i>
                    </button>
                    <button class="btn">
                        <i class="material-icons md-light">not_interested</i>
                    </button>
                <#else>
                    <form method="post" action="action" id="createForm">
                        <input type="hidden" name="action" value="create"/>
                        <button type="submit" title="Save" class="btn">
                            <i class="material-icons md-light">save</i>
                        </button>
                    </form>
                </#if>
            </li>
        </ul>
    </div>
</nav>

<#if snippet??>
    <textarea class="textbox bg-dark" spellcheck="false" form="createForm" name="text"
              readonly>${snippet.text}</textarea>
<#else>
    <textarea class="textbox bg-dark" spellcheck="false" form="createForm" name="text"></textarea>
</#if>

<script src="https://code.jquery.com/jquery-3.4.1.min.js"
        integrity="sha256-CSXorXvZcTkaix6Yvo6HppcZGetbYMGWSFlBw8HfCJo=" crossorigin="anonymous"
        type="text/javascript"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"
        type="text/javascript"></script>

</body>
</html>