<%@page contentType="text/html" pageEncoding="UTF-8" %>

<ul class="nav navbar-nav navbar-right">
    <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">${pageContext.response.locale}<b class="caret"></b></a>
        <ul class="dropdown-menu" role="menu">
            <li><a onclick="show('en')">English</a></li>
            <li><a onclick="show('ru')">Русский</a></li>
        </ul>
    </li>
</ul>
<script type="text/javascript">
    function show(lang) {
        window.location.href = window.location.href.split('?')[0] + '?lang=' + lang;
    }
</script>