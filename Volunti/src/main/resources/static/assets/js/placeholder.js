
jQuery(document).ready(function(){
	
	$('.opportunity-form input[type="text"], .opportunity-form input[type="password"], .opportunity-form textarea').each(function() {
		$(this).val( $(this).attr('placeholder') );
    });
	
});