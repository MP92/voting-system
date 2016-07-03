Handlebars.registerHelper('toPercentage', function(rational) {
    return Math.round(rational * 100) + '%';
});

Handlebars.registerHelper('contextPath', function() {
    return getContextPath();
});

Handlebars.getTemplate = function(name) {
    if (Handlebars.templates === undefined || Handlebars.templates[name] === undefined) {
        $.ajax({
            url : getContextPath() + '/resources/templates/' + name + '.handlebars',
            success : function(data) {
                if (Handlebars.templates === undefined) {
                    Handlebars.templates = {};
                }
                Handlebars.templates[name] = Handlebars.compile(data);
            },
            async : false
        });
    }
    return Handlebars.templates[name];
};
