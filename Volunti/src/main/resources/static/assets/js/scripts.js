/*jshint browser:true, devel:true, unused:false */
/*global google */
;(function($) {

"use strict";

var $body = $('body');
var body = $('body');
// var $head = $('head');
// var $mainWrapper = $('#main-wrapper');

var contact = $(body).find('.contact-button');
var contactWindow = $(contact).find('.contact-details');

$('.sponsors-slider').owlCarousel({
  items: 6
});

$(contact).on('click', function(e){
  $(this).find(contactWindow).toggle();
  e.preventDefault();
});

var share = $(body).find('.share-button');
var shareWindow = $(share).find('.contact-share');

$(share).on('click', function(e){
  $(this).find(shareWindow).toggle();
  e.preventDefault();
});


$('.slider-range-container').each(function(){
    if ( $.fn.slider ) {

      var self = $(this),
      slider = self.find( '.slider-range' ),
      min = slider.data( 'min' ) ? slider.data( 'min' ) : 100,
      max = slider.data( 'max' ) ? slider.data( 'max' ) : 2000,
      step = slider.data( 'step' ) ? slider.data( 'step' ) : 100,
      default_min = slider.data( 'default-min' ) ? slider.data( 'default-min' ) : 100,
      default_max = slider.data( 'default-max' ) ? slider.data( 'default-max' ) : 500,
      currency = slider.data( 'currency' ) ? slider.data( 'currency' ) : '$',
      input_from = self.find( '.range-from' ),
      input_to = self.find( '.range-to' );

      input_from.val( currency + ' ' + default_min );
      input_to.val( currency + ' ' + default_max );

      slider.slider({
        range: true,
        min: min,
        max: max,
        step: step,
        values: [ default_min, default_max ],
        slide: function( event, ui ) {
          input_from.val( currency + ' ' + ui.values[0] );
          input_to.val( currency + ' ' + ui.values[1] );
        }
      });
    }
});

$('.custom-select').select2();

// Mediaqueries
// ---------------------------------------------------------
// var XS = window.matchMedia('(max-width:767px)');
// var SM = window.matchMedia('(min-width:768px) and (max-width:991px)');
// var MD = window.matchMedia('(min-width:992px) and (max-width:1199px)');
// var LG = window.matchMedia('(min-width:1200px)');
// var XXS = window.matchMedia('(max-width:480px)');
// var SM_XS = window.matchMedia('(max-width:991px)');
// var LG_MD = window.matchMedia('(min-width:992px)');




// Touch
// ---------------------------------------------------------
var dragging = false;

$body.on('touchmove', function() {
	dragging = true;
});

$body.on('touchstart', function() {
	dragging = false;
});



// Set Background Image
// ---------------------------------------------------------
$('.has-bg-image').each(function () {
  var $this = $(this),

      image = $this.data('bg-image'),
      color = $this.data('bg-color'),
      opacity = $this.data('bg-opacity'),

      $content = $('<div/>', { 'class': 'content' }),
      $background = $('<div/>', { 'class': 'background' });

  if (opacity) {
    $this.children().wrapAll($content);
    $this.append($background);

    $this.css({
      'background-image': 'url(' + image + ')'
    });

    $background.css({
      'background-color': '#' + color,
      'opacity': opacity
    });
  } else {
    $this.css({
      'background-image': 'url(' + image + ')',
      'background-color': '#' + color
    });
  }
});



// Superfish Menus
// ---------------------------------------------------------
if ($.fn.superfish) {
  $('.sf-menu').superfish();
} else {
  console.warn('not loaded -> superfish.min.js and hoverIntent.js');
}



// Mobile Sidebar
// ---------------------------------------------------------
$('.mobile-sidebar-toggle').on('click', function () {
  $body.toggleClass('mobile-sidebar-active');
  return false;
});

$('.mobile-sidebar-open').on('click', function () {
  $body.addClass('mobile-sidebar-active');
  return false;
});

$('.mobile-sidebar-close').on('click', function () {
  $body.removeClass('mobile-sidebar-active');
  return false;
});



// UOU Tabs
// ---------------------------------------------------------
if ($.fn.uouTabs) {
  $('.uou-tabs').uouTabs();
} else {
  console.warn('not loaded -> uou-tabs.js');
}



// UOU Accordions
// ---------------------------------------------------------
if ($.fn.uouAccordions) {
  $('.uou-accordions').uouAccordions();
} else {
  console.warn('not loaded -> uou-accordions.js');
}



// UOU Alers
// ---------------------------------------------------------
$('.alert').each(function () {
  var $this = $(this);

  if ($this.hasClass('alert-dismissible')) {
    $this.children('.close').on('click', function (event) {
      event.preventDefault();

      $this.remove();
    });
  }
});



// Default Slider
// ---------------------------------------------------------
if ($.fn.flexslider) {
  $('.default-slider').flexslider({
    slideshowSpeed: 10000,
    animationSpeed: 1000,
    prevText: '',
    nextText: ''
  });
} else {
  console.warn('not loaded -> jquery.flexslider-min.js');
}



// Range Slider (forms)
// ---------------------------------------------------------
if ($.fn.rangeslider) {
  $('input[type="range"]').rangeslider({
    polyfill: false,
    onInit: function () {
      this.$range.wrap('<div class="uou-rangeslider"></div>').parent().append('<div class="tooltip">' + this.$element.data('unit-before') + '<span></span>' + this.$element.data('unit-after') + '</div>');
    },
    onSlide: function(value, position) {
      var $span = this.$range.parent().find('.tooltip span');
      $span.html(position);
    }
  });
} else {
  console.warn('not loaded -> rangeslider.min.js');
}



// Placeholder functionality for selects (forms)
// ---------------------------------------------------------
function selectPlaceholder(el) {
  var $el = $(el);

  if ($el.val() === 'placeholder') {
    $el.addClass('placeholder');
  } else {
    $el.removeClass('placeholder');
  }
}

$('select').each(function () {
  selectPlaceholder(this);
}).change(function () {
  selectPlaceholder(this);
});





// ---------------------------------------------------------
// BLOCKS
// BLOCKS
// BLOCKS
// BLOCKS
// BLOCKS
// ---------------------------------------------------------





// .uou-block-1a
// ---------------------------------------------------------
$('.uou-block-1a').each(function () {
  var $block = $(this);

  // search
  $block.find('.search').each(function () {
    var $this = $(this);

    $this.find('.toggle').on('click', function (event) {
      event.preventDefault();
      $this.addClass('active');
      setTimeout(function () {
        $this.find('.search-input').focus();
      }, 100);
    });

    $this.find('input[type="text"]').on('blur', function () {
      $this.removeClass('active');
    });
  });

  // language
  $block.find('.language').each(function () {
    var $this = $(this);

    $this.find('.toggle').on('click', function (event) {
      event.preventDefault();

      if (!$this.hasClass('active')) {
        $this.addClass('active');
      } else {
        $this.removeClass('active');
      }
    });
  });
});



// .uou-block-1b
// ---------------------------------------------------------
$('.uou-block-1b').each(function () {
  var $block = $(this);

  // language
  $block.find('.language').each(function () {
    var $this = $(this);

    $this.find('.toggle').on('click', function (event) {
      event.preventDefault();

      if (!$this.hasClass('active')) {
        $this.addClass('active');
      } else {
        $this.removeClass('active');
      }
    });
  });
});



// .uou-block-1e
// ---------------------------------------------------------
$('.uou-block-1e').each(function () {
  var $block = $(this);

  // language
  $block.find('.language').each(function () {
    var $this = $(this);

    $this.find('.toggle').on('click', function (event) {
      event.preventDefault();

      if (!$this.hasClass('active')) {
        $this.addClass('active');
      } else {
        $this.removeClass('active');
      }
    });
  });
});



// .uou-block-5b
// ---------------------------------------------------------
$('.uou-block-5b').each(function () {
  var $block = $(this),
      $tabs = $block.find('.tabs > li');

  $tabs.on('click', function () {
    var $this = $(this),
        target = $this.data('target');

    if (!$this.hasClass('active')) {
      $block.find('.' + target).addClass('active').siblings('blockquote').removeClass('active');

      $tabs.removeClass('active');
      $this.addClass('active');

      return false;
    }
  });
});



// .uou-block-5c
// ---------------------------------------------------------
$('.uou-block-5c').each(function () {
  var $block = $(this);

  if ($.fn.flexslider) {
    $block.find('.flexslider').flexslider({
      slideshowSpeed: 10000,
      animationSpeed: 1000,
      prevText: '',
      nextText: '',
      controlNav: false,
      smoothHeight: true
    });
  } else {
    console.warn('not loaded -> jquery.flexslider-min.js');
  }
});



// .uou-block-7g
// ---------------------------------------------------------
$('.uou-block-7g').each(function () {
  var $block = $(this),
      $badge = $block.find('.badge'),
      badgeColor = $block.data('badge-color');

  if (badgeColor) {
    $badge.css('background-color', '#' + badgeColor);
  }
});



// .uou-block-7h
// ---------------------------------------------------------
$('.uou-block-7h').each(function () {
  var $block = $(this);

  if ($.fn.flexslider) {
    $block.find('.flexslider').flexslider({
      slideshowSpeed: 10000,
      animationSpeed: 1000,
      prevText: '',
      nextText: '',
      directionNav: false,
      smoothHeight: true
    });
  } else {
    console.warn('not loaded -> jquery.flexslider-min.js');
  }
});



// .uou-block-11a
// ---------------------------------------------------------
$('.uou-block-11a').each(function () {
  var $block = $(this);

  // nav
  $block.find('.main-nav').each(function () {
    var $this = $(this).children('ul');

    $this.find('li').each(function () {
      var $this = $(this);

      if ($this.children('ul').length > 0) {
        $this.addClass('has-submenu');
        $this.append('<span class="arrow"></span>');
      }
    });

    var $submenus = $this.find('.has-submenu');

    $submenus.children('.arrow').on('click', function (event) {
      var $this = $(this),
          $li = $this.parent('li');

      if (!$li.hasClass('active')) {
        $li.addClass('active');
        $li.children('ul').slideDown();
      } else {
        $li.removeClass('active');
        $li.children('ul').slideUp();
      }
    });
  });
});





}(jQuery));





/*-----------------------------------------------------------------------------------*/
/* 	GALLERY SLIDER
/*-----------------------------------------------------------------------------------*/
$('.texti-slide').owlCarousel({
    loop:true,
    nav:true,
	items: 2,
	navText: ["<i class='fa fa-angle-left'></i>","<i class='fa fa-angle-right'></i>"],
    responsive:{
        0:{
            items:1
        },
        800:{
            items:2
        },
		
		1200:{
            items:2
        },
}});


// uou-toggle-content
$('.content-main h6 a').on('click', function(e){
    e.preventDefault();
    $(this).toggleClass('active');
    $(this).parent().next(".content-hidden").toggleClass('active');
  });








