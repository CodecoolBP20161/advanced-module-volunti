
jQuery(document).ready(function() {
	
    /*
        Fullscreen background
    */
    $.backstretch("/assets/img/csiga.jpg");

    $('#top-navbar-1').on('shown.bs.collapse', function(){
    	$.backstretch("resize");
    });
    $('#top-navbar-1').on('hidden.bs.collapse', function(){
    	$.backstretch("resize");
    });
    
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
    	
    	// parent_fieldset.find('input[type="text"], option:selected, input[type="number"]').each(function() {
    	// 	if( $(this).val() == "" ) {
    	// 		$(this).addClass('input-error');
    	// 		next_step = false;
			// 	console.log("next Step1");
    	// 	}
    	// 	else {
    	// 		$(this).removeClass('input-error');
    	// 	}
    	// });
    	
    	if( next_step ) {
    		parent_fieldset.fadeOut(400, function() {
	    		$(this).next().fadeIn();
	    	});
    	}
    	
    });
    
    // previous step
    $('.opportunity-form .btn-previous').on('click', function() {
    	$(this).parents('fieldset').fadeOut(400, function() {
    		$(this).prev().fadeIn();
    	});
    });
    
    // submit
    // $('.opportunity-form').on('submit', function(e) {
    //
    // 	$(this).find('input[type="text"], input[type="password"], textarea').each(function() {
    // 		if( $(this).val() == "" ) {
    // 			e.preventDefault();
    // 			$(this).addClass('input-error');
    // 		}
    // 		else {
    // 			$(this).removeClass('input-error');
    // 		}
    // 	});
    //
    // });

	$('#select-state').selectize({
		maxItems: 5
	});
    
    
});
