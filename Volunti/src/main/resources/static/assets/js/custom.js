
    $('#select-state').selectize({
        maxItems: 5
    });

    $('#select-language').selectize({
        maxItems: 5
    });

    $('#select-country').selectize({
        maxItems: 1
    });


    $('textarea').each(function () {
        this.setAttribute('style', 'height:' + (this.scrollHeight) + 'px;overflow-y:hidden;');
    }).on('input', function () {
        this.style.height = 'auto';
        this.style.height = (this.scrollHeight) + 'px';
    });


    function loadScript(url, callback)
    {
        // Adding the script tag to the head as suggested before
        var head = document.getElementsByTagName('head')[0];
        var script = document.createElement('script');
        script.type = 'text/javascript';
        script.src = url;

        // Then bind the event to the callback function.
        // There are several events for cross browser compatibility.
        script.onreadystatechange = callback;
        script.onload = callback;

        // Fire the loading
        head.appendChild(script);
    }

    var errors = [],

// Validation configuration
        conf = {
            onElementValidate : function(valid, $el, $form, errorMess) {
                if( !valid ) {
                    // gather up the failed validations
                    errors.push({el: $el, error: errorMess});
                }
            }
        },

// Optional language object
        lang = {
            lang:'en'
        };

    // Manually load the modules used in this form
    $.formUtils.loadModules('security, date');

    $('#check-form').on('click', function() {
        // reset error array
        errors = [];
        if( !$(this).isValid(lang, conf, false) ) {
            displayErrors( errors );
        } else {
            // The form is valid
        }
    });