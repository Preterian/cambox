$( document ).ready(function() {
 
	
// drag and drop	
  $(function() {
    $( ".linkToVideo" ).draggable({
    	 revert : function(event, ui) {
             // on older version of jQuery use "draggable"
             // $(this).data("draggable")
             // on 2.x versions of jQuery use "ui-draggable"
             // $(this).data("ui-draggable")
             $(this).data("uiDraggable").originalPosition = {
                 top : 0,
                 left : 0
             };
             // return boolean
             return !event;
             // that evaluate like this:
             // return event !== false ? false : true;
         }
    });
    
    $( ".header-box" ).droppable({
      drop: function( event, ui ) {
    	  console.log("droped!!");
    	  var linkToParse = $( ui.draggable ).attr( "href" );
    	  console.log(linkToParse);
    	  
    	  $.ajax({
  	        type : 'GET',
  	        url : '/addvideotocollection',
  	        data : {
  	           link:linkToParse
  	        },
  	        success : function(data) {
  	        	alert("Video added to collection!!!");
  	        },
  	        error: function (xhr, ajaxOptions, thrownError) {
  	        	alert("some error occured!");
  	        }
  	    });
    	  
    	
    	  
    	  ui.draggable.draggable('option','revert',true);
      }
    });
  });
  
  
 
  
 
  
  



 

 

    $(function() {         
        // Expose the form 
        $('form').click(function() { 
            $('form').expose({api: true}).load(); 
        }); 
        
        // If there is an error, focus to form
        if($('form .error').size()) {
            $('form').expose({api: true, loadSpeed: 0}).load(); 
            $('form input[type=text]').get(0).focus();
        }
    });

  
  
}); 

