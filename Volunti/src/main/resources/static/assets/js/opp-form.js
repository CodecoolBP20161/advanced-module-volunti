
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
