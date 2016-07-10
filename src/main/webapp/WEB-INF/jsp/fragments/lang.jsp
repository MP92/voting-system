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
    function updateQueryStringParameter(uri, key, value) {
        var re = new RegExp("([?|&])" + key + "=.*?(&|#|$)", "i");
        if (uri.match(re)) {
            return uri.replace(re, '$1' + key + "=" + value + '$2');
        } else {
            var hash =  '';
            if( uri.indexOf('#') !== -1 ){
                hash = uri.replace(/.*#/, '#');
                uri = uri.replace(/#.*/, '');
            }
            var separator = uri.indexOf('?') !== -1 ? "&" : "?";
            return uri + separator + key + "=" + value + hash;
        }
    }

    function show(lang) {
        window.location.href = updateQueryStringParameter(window.location.href, "lang", lang);
    }
</script>