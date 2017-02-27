
function sleep(milliseconds) {
    var start = new Date().getTime();
    for (var i = 0; i < 1e7; i++) {
        if ((new Date().getTime() - start) > milliseconds){
            break;
        }
    }
}

jQuery(document).ready(function() {
	
    /*
        Form
    */
    $('.opportunity-form').on('keyup keypress', function(e) {
        var keyCode = e.keyCode || e.which;
        if (keyCode === 13) {
            e.preventDefault();
            return false;
        }
    });

    $('.opportunity-form fieldset:first-child').fadeIn('slow');
    
    $('.opportunity-form input[type="text"], .opportunity-form input[type="password"], .opportunity-form textarea').on('focus', function() {
    	$(this).removeClass('input-error');
    });
    
    // next step
    $('.opportunity-form .btn-next').on('click', function() {
    	var parent_fieldset = $(this).parents('fieldset');
    	var next_step = true;

        if ($('span.form-error').length != 0 ){
            next_step = false;
        } else{
            next_step = true;
        }

        var multiSelect = [];
        $('#select-state :selected').each(function(i, selected){
            multiSelect[i] = $(selected).text();
        });

        if (multiSelect.length === 0){
            if ($('.help-block.form-error').length === 0 ) {
                $('.custom-select-box').append('<span class="help-block form-error">Please choose skill! </span>');
            }
                next_step = false;
        } else{
            next_step = true;
        }


    	if( next_step ) {
    		parent_fieldset.fadeOut(400, function() {
	    		$(this).next().fadeIn();
	    	});
			$( "li.active" ).removeClass( "active" ).next().addClass("active");//.addClass( "yourClass" )
			$("html, body").animate({ scrollTop: 0 }, "slow");

    	};
    	
    });
    
    // previous step
    $('.opportunity-form .btn-previous').on('click', function() {
    	$(this).parents('fieldset').fadeOut(400, function() {
    		$(this).prev().fadeIn();
    	});
        $( "li.active" ).removeClass( "active" ).prev().addClass("active");//.addClass( "yourClass" )
        $("html, body").animate({ scrollTop: 0 }, "slow");
    });

});
