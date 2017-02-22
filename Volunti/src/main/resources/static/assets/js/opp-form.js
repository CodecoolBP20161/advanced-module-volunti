
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
    $('.opportunity-form fieldset:first-child').fadeIn('slow');
    
    $('.opportunity-form input[type="text"], .opportunity-form input[type="password"], .opportunity-form textarea').on('focus', function() {
    	$(this).removeClass('input-error');
    });
    
    // next step
    $('.opportunity-form .btn-next').on('click', function() {
    	var parent_fieldset = $(this).parents('fieldset');
    	var next_step = true;

		var $title = $('#title');
        var $multiSkills = $('#custom-select-box');
        var $numberOfVolunteers = $('#numberOfVolunteers');
		if (!$title[0].checkValidity() ) {
			// If the form is invalid, submit it. The form won't actually submit;
			// this will just cause the browser to display the native HTML5 error messages.
			next_step = false;
            $('form').find(':submit').click();
            sleep(2000);
            // $("html, body").animate({ scrollTop: 30 }, "slow").append(alert);
		}


        if (!$numberOfVolunteers[0].checkValidity() ) {
            next_step = false;
            $('form').find(':submit').click();
            sleep(2000);
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
